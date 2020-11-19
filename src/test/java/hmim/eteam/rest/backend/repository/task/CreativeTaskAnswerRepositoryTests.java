package hmim.eteam.rest.backend.repository.task;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.task.CreativeTask;
import hmim.eteam.rest.backend.entity.task.CreativeTaskAnswer;
import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.repository.course.CourseRepository;
import hmim.eteam.rest.backend.repository.course.CourseThemeRepository;
import hmim.eteam.rest.backend.repository.user.SiteUserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CreativeTaskAnswerRepositoryTests {

    @Autowired
    private CreativeTaskAnswerRepository creativeTaskAnswerRepository;

    @Autowired
    private CreativeTaskRepository creativeTaskRepository;

    @Autowired
    private SiteUserRepository siteUserRepository;

    @Autowired
    private CourseThemeRepository courseThemeRepository;

    @Autowired
    private CourseRepository courseRepository;

    public void clearDatabase(){
        creativeTaskAnswerRepository.deleteAll();
        creativeTaskRepository.deleteAll();
        siteUserRepository.deleteAll();
        courseThemeRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Test
    public void findByStudentAndTaskExists(){
        clearDatabase();

        Course firstCourse = new Course("CourseName");
        courseRepository.save(firstCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1,firstCourse,"Name");
        courseThemeRepository.save(firstCourseTheme);

        CreativeTask firstCreativeTask = new CreativeTask(1,firstCourseTheme,"Name","Text");
        creativeTaskRepository.save(firstCreativeTask);

        SiteUser firstUser = new SiteUser("Name","Login","Password",false);
        siteUserRepository.save(firstUser);

        CreativeTaskAnswer firstCreativeTaskAnswer = new CreativeTaskAnswer(firstCreativeTask,firstUser,"Answer",new Date());
        creativeTaskAnswerRepository.save(firstCreativeTaskAnswer);

        List<CreativeTaskAnswer> foundedCreativeTaskAnswer = creativeTaskAnswerRepository.findByStudentAndTaskOrderByDateAsc(firstUser,firstCreativeTask);

        Assert.assertNotNull(foundedCreativeTaskAnswer);
        Assert.assertEquals(foundedCreativeTaskAnswer.size(),1);
        Assert.assertEquals(firstCreativeTaskAnswer,firstCreativeTaskAnswer);
    }

    @Test
    public void findByStudentAndTaskNotExists(){
        clearDatabase();

        Course firstCourse = new Course("CourseName");
        courseRepository.save(firstCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1,firstCourse,"Name");
        courseThemeRepository.save(firstCourseTheme);

        CreativeTask firstCreativeTask = new CreativeTask(1,firstCourseTheme,"Name","Text");
        creativeTaskRepository.save(firstCreativeTask);

        CreativeTask secondCreativeTask = new CreativeTask(1,firstCourseTheme,"Name","Text");
        creativeTaskRepository.save(secondCreativeTask);

        SiteUser firstUser = new SiteUser("Name","Login","Password",false);
        siteUserRepository.save(firstUser);

        CreativeTaskAnswer firstCreativeTaskAnswer = new CreativeTaskAnswer(secondCreativeTask,firstUser,"Answer",new Date());
        creativeTaskAnswerRepository.save(firstCreativeTaskAnswer);

        List<CreativeTaskAnswer> foundedCreativeTaskAnswer = creativeTaskAnswerRepository.findByStudentAndTaskOrderByDateAsc(firstUser,firstCreativeTask);

        Assert.assertNotNull(foundedCreativeTaskAnswer);
        Assert.assertTrue(foundedCreativeTaskAnswer.isEmpty());
    }

    @Test
    public void findSeveralByStudentAndTask() {
        clearDatabase();

        Course firstCourse = new Course("CourseName");
        courseRepository.save(firstCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1, firstCourse, "Name");
        courseThemeRepository.save(firstCourseTheme);

        CreativeTask firstCreativeTask = new CreativeTask(1, firstCourseTheme, "Name", "Text");
        creativeTaskRepository.save(firstCreativeTask);

        SiteUser firstUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(firstUser);

        List<CreativeTaskAnswer> creativeTaskAnswers = new ArrayList<CreativeTaskAnswer>();
        for (int i = 0; i < 10; i++) {
            creativeTaskAnswers.add(new CreativeTaskAnswer(firstCreativeTask, firstUser, "Answer", new Date(100 - i)));
            creativeTaskAnswerRepository.save(creativeTaskAnswers.get(creativeTaskAnswers.size() - 1));
        }


        List<CreativeTaskAnswer> foundedCreativeTaskAnswers = creativeTaskAnswerRepository.findByStudentAndTaskOrderByDateAsc(firstUser, firstCreativeTask);
        Assert.assertNotNull(foundedCreativeTaskAnswers);
        Assert.assertEquals(creativeTaskAnswers.size(), foundedCreativeTaskAnswers.size());

        Assert.assertTrue(foundedCreativeTaskAnswers.containsAll(creativeTaskAnswers));
        Assert.assertTrue(creativeTaskAnswers.containsAll(foundedCreativeTaskAnswers));

        for (int index = 1; index < foundedCreativeTaskAnswers.size(); ++index) {
            Date previous = foundedCreativeTaskAnswers.get(index - 1).getDate();
            Date current = foundedCreativeTaskAnswers.get(index).getDate();
            Assert.assertTrue(previous.before(current));
        }
    }
}
