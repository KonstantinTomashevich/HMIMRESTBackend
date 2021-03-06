package hmim.eteam.rest.backend.repository.forum;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.entity.forum.ForumMessage;
import hmim.eteam.rest.backend.entity.forum.ForumTheme;
import hmim.eteam.rest.backend.repository.course.CourseRepository;
import hmim.eteam.rest.backend.repository.user.SiteUserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MessageRepositoryTests {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SiteUserRepository siteUserRepository;

    private void clearDatabase() {
        courseRepository.deleteAll();
        themeRepository.deleteAll();
        messageRepository.deleteAll();
        siteUserRepository.deleteAll();
    }

    @Test
    public void findByThemeExists() {
        clearDatabase();
        SiteUser user = new SiteUser("Name", "longHash", "passwordHash", false);
        siteUserRepository.save(user);

        Course course = new Course(0, "HelloWorld");
        courseRepository.save(course);

        ForumTheme theme = new ForumTheme(course, "HelloWorld", new Date());
        themeRepository.save(theme);

        ForumMessage message = new ForumMessage(theme, user, new Date(), "HelloWorld");
        messageRepository.save(message);

        List<ForumMessage> messages = messageRepository.findByThemeOrderByDateAsc(theme);
        Assert.assertNotNull(messages);
        Assert.assertEquals(messages.size(), 1);
        Assert.assertEquals(messages.get(0), message);
    }

    @Test
    public void findByThemeNotExists() {
        clearDatabase();
        SiteUser user = new SiteUser("Name", "longHash", "passwordHash", false);
        siteUserRepository.save(user);

        Course course = new Course(0, "HelloWorld");
        courseRepository.save(course);

        ForumTheme theme = new ForumTheme(course, "HelloWorld", new Date());
        themeRepository.save(theme);

        ForumTheme otherTheme = new ForumTheme(course, "HelloWorld", new Date());
        themeRepository.save(otherTheme);

        ForumMessage message = new ForumMessage(theme, user, new Date(), "HelloWorld");
        messageRepository.save(message);

        List<ForumMessage> messages = messageRepository.findByThemeOrderByDateAsc(otherTheme);
        Assert.assertNotNull(messages);
        Assert.assertTrue(messages.isEmpty());
    }

    List<ForumMessage> generateTestMessages(ForumTheme theme, SiteUser user) {
        List<ForumMessage> messages = new ArrayList<>();
        final int count = 100;

        for (int index = 0; index < count; ++index) {
            ForumMessage message = new ForumMessage(theme, user, new Date(count - index), "Something");
            messageRepository.save(message);
            messages.add(message);
        }

        return messages;
    }

    @Test
    public void findSeveralByTheme() {
        clearDatabase();
        SiteUser user = new SiteUser("Name", "longHash", "passwordHash", false);
        siteUserRepository.save(user);

        Course course = new Course(0, "HelloWorld");
        courseRepository.save(course);

        ForumTheme theme = new ForumTheme(course, "HelloWorld", new Date());
        themeRepository.save(theme);
        List<ForumMessage> messages = generateTestMessages(theme, user);

        List<ForumMessage> foundMessages = messageRepository.findByThemeOrderByDateAsc(theme);
        Assert.assertNotNull(foundMessages);
        Assert.assertEquals(messages.size(), foundMessages.size());

        Assert.assertTrue(foundMessages.containsAll(messages));
        Assert.assertTrue(messages.containsAll(foundMessages));

        for (int index = 1; index < foundMessages.size(); ++index) {
            Date previous = foundMessages.get(index - 1).getDate();
            Date current = foundMessages.get(index).getDate();
            Assert.assertTrue(previous.before(current));
        }
    }

    @Test
    public void findTopByTheme() {
        clearDatabase();
        SiteUser user = new SiteUser("Name", "longHash", "passwordHash", false);
        siteUserRepository.save(user);

        Course course = new Course(0, "HelloWorld");
        courseRepository.save(course);

        ForumTheme theme = new ForumTheme(course, "HelloWorld", new Date());
        themeRepository.save(theme);
        List<ForumMessage> messages = generateTestMessages(theme, user);

        Optional<ForumMessage> top = messages.stream().reduce(
                (first, second) -> first.getDate().after(second.getDate()) ? first : second);
        Assert.assertTrue(top.isPresent());

        ForumMessage foundTop = messageRepository.findTopByThemeOrderByDateDesc(theme);
        Assert.assertEquals(top.get(), foundTop);
    }
}
