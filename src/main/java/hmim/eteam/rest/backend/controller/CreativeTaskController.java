package hmim.eteam.rest.backend.controller;

import hmim.eteam.rest.backend.api.IRoleResolver;
import hmim.eteam.rest.backend.entity.task.CreativeTask;
import hmim.eteam.rest.backend.entity.user.AuthToken;
import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.entity.user.UserRole;
import hmim.eteam.rest.backend.model.AnswerSave;
import hmim.eteam.rest.backend.model.CreativeTaskAnswer;
import hmim.eteam.rest.backend.repository.task.CreativeTaskAnswerRepository;
import hmim.eteam.rest.backend.repository.task.CreativeTaskRepository;
import hmim.eteam.rest.backend.repository.user.AuthTokenRepository;
import hmim.eteam.rest.backend.repository.user.SiteUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CreativeTaskController {
    private final IRoleResolver roleResolver;
    private final CreativeTaskRepository creativeTaskRepository;
    private final CreativeTaskAnswerRepository creativeTaskAnswerRepository;
    private final AuthTokenRepository authTokenRepository;
    private final SiteUserRepository siteUserRepository;

    public CreativeTaskController(IRoleResolver roleResolver, CreativeTaskRepository creativeTaskRepository,
                                  CreativeTaskAnswerRepository creativeTaskAnswerRepository,
                                  AuthTokenRepository authTokenRepository, SiteUserRepository siteUserRepository) {
        this.roleResolver = roleResolver;
        this.creativeTaskRepository = creativeTaskRepository;
        this.creativeTaskAnswerRepository = creativeTaskAnswerRepository;
        this.authTokenRepository = authTokenRepository;
        this.siteUserRepository = siteUserRepository;
    }

    public ResponseEntity<List<CreativeTaskAnswer>> taskIdAnswersGet(String token, Long id, Long participant) {
        if (token == null || id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<CreativeTask> creativeTask = creativeTaskRepository.findById(id);
        if (!creativeTask.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserRole role = roleResolver.resolve(token, creativeTask.get().getTheme().getCourse().getId());
        if (role == UserRole.Guest || (participant == null && role != UserRole.Admin)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (role != UserRole.Admin) {
            Optional<AuthToken> authToken = authTokenRepository.resolveToken(token);
            if (!authToken.isPresent() || authToken.get().getUser().getId() != (long) participant) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        List<hmim.eteam.rest.backend.entity.task.CreativeTaskAnswer> answers;
        if (participant == null) {
            answers = creativeTaskAnswerRepository.findByTaskOrderByDateAsc(creativeTask.get());
        } else {
            Optional<SiteUser> user = siteUserRepository.findById(participant);
            if (!user.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            answers = creativeTaskAnswerRepository.findByStudentAndTaskOrderByDateAsc(user.get(), creativeTask.get());
        }

        List<CreativeTaskAnswer> convertedAnswers = new ArrayList<>();
        answers.forEach(answer -> convertedAnswers.add(answer.toApiRepresentation()));
        return new ResponseEntity<>(convertedAnswers, HttpStatus.OK);
    }

    public ResponseEntity<Void> answerPost(String token, AnswerSave answerSave) {
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Optional<CreativeTask> task = creativeTaskRepository.findById(answerSave.getId());
        if (!task.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<AuthToken> authToken = authTokenRepository.resolveToken(token);
        if (!authToken.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UserRole role = roleResolver.resolve(authToken, task.get().getTheme().getCourse().getId());
        if (role == UserRole.Guest ||
                (!Objects.equals(authToken.get().getUser().getId(), answerSave.getAnswer().getParticipant()) &&
                        role != UserRole.Admin)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        } else {
            Optional<SiteUser> student = siteUserRepository.findById(answerSave.getAnswer().getParticipant());
            if (!student.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            if (roleResolver.resolve(student.get(), task.get().getTheme().getCourse().getId()) != UserRole.Student) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            creativeTaskAnswerRepository.save(new hmim.eteam.rest.backend.entity.task.CreativeTaskAnswer(
                    task.get(),
                    student.get(),
                    answerSave.getAnswer().getText(),
                    Date.from(answerSave.getAnswer().getDate().toInstant())));

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
