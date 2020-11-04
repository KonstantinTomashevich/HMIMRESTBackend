package hmim.eteam.rest.backend.repository.forum;

import hmim.eteam.rest.backend.entity.core.Course;
import hmim.eteam.rest.backend.entity.forum.ForumMessage;
import hmim.eteam.rest.backend.entity.forum.ForumTheme;
import hmim.eteam.rest.backend.repository.core.CourseRepository;
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

    private void clearDatabase() {
        courseRepository.deleteAll();
        themeRepository.deleteAll();
        messageRepository.deleteAll();
    }

    @Test
    public void findByThemeExists() {
        clearDatabase();
        Course course = new Course("HelloWorld");
        courseRepository.save(course);

        ForumTheme theme = new ForumTheme(course, "HelloWorld");
        themeRepository.save(theme);

        ForumMessage message = new ForumMessage(theme, null, 1, "HelloWorld");
        messageRepository.save(message);

        List<ForumMessage> messages = messageRepository.findByThemeOrderByIndexAsc(theme);
        Assert.assertNotNull(messages);
        Assert.assertEquals(messages.size(), 1);
        Assert.assertEquals(messages.get(0), message);
    }

    @Test
    public void findByThemeNotExists() {
        clearDatabase();
        Course course = new Course("HelloWorld");
        courseRepository.save(course);

        ForumTheme theme = new ForumTheme(course, "HelloWorld");
        themeRepository.save(theme);

        ForumTheme otherTheme = new ForumTheme(course, "HelloWorld");
        themeRepository.save(otherTheme);

        ForumMessage message = new ForumMessage(theme, null, 1, "HelloWorld");
        messageRepository.save(message);

        List<ForumMessage> messages = messageRepository.findByThemeOrderByIndexAsc(otherTheme);
        Assert.assertNotNull(messages);
        Assert.assertTrue(messages.isEmpty());
    }

    List<ForumMessage> generateTestMessages(ForumTheme theme) {
        List<ForumMessage> messages = new ArrayList<>();
        for (int index = 0; index < 100; ++index) {
            ForumMessage message = new ForumMessage(theme, null,
                    -index, "Something");

            messageRepository.save(message);
            messages.add(message);
        }

        return messages;
    }

    @Test
    public void findSeveralByTheme() {
        clearDatabase();
        Course course = new Course("HelloWorld");
        courseRepository.save(course);

        ForumTheme theme = new ForumTheme(course, "HelloWorld");
        themeRepository.save(theme);
        List<ForumMessage> messages = generateTestMessages(theme);

        List<ForumMessage> foundMessages = messageRepository.findByThemeOrderByIndexAsc(theme);
        Assert.assertNotNull(foundMessages);
        Assert.assertEquals(messages.size(), foundMessages.size());

        Assert.assertTrue(foundMessages.containsAll(messages));
        Assert.assertTrue(messages.containsAll(foundMessages));

        for (int index = 1; index < foundMessages.size(); ++index) {
            long previous = foundMessages.get(index - 1).getIndex();
            long current = foundMessages.get(index).getIndex();
            Assert.assertTrue(previous < current);
        }
    }

    @Test
    public void findTopByTheme() {
        clearDatabase();
        Course course = new Course("HelloWorld");
        courseRepository.save(course);

        ForumTheme theme = new ForumTheme(course, "HelloWorld");
        themeRepository.save(theme);
        List<ForumMessage> messages = generateTestMessages(theme);

        Optional<ForumMessage> top = messages.stream().reduce(
                (first, second) -> first.getIndex() > second.getIndex() ? first : second);
        Assert.assertTrue(top.isPresent());

        ForumMessage foundTop = messageRepository.findTopByThemeOrderByIndexDesc(theme);
        Assert.assertEquals(top.get(), foundTop);
    }
}
