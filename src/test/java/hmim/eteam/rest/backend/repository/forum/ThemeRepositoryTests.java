package hmim.eteam.rest.backend.repository.forum;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.forum.ForumTheme;
import hmim.eteam.rest.backend.repository.course.CourseRepository;
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
public class ThemeRepositoryTests {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ThemeRepository themeRepository;

    private void clearDatabase()
    {
        courseRepository.deleteAll();
        themeRepository.deleteAll();
    }

    @Test
    public void findByCourseExists() {
        clearDatabase();
        Course course = new Course("HelloWorld");
        courseRepository.save(course);

        ForumTheme theme = new ForumTheme(course, "HelloWorld", new Date());
        themeRepository.save(theme);

        List<ForumTheme> themes = themeRepository.findByCourse(course);
        Assert.assertNotNull(themes);
        Assert.assertEquals(themes.size(), 1);
        Assert.assertEquals(themes.get(0), theme);
    }

    @Test
    public void findByCourseNotExists() {
        clearDatabase();
        Course firstCourse = new Course("HelloWorld");
        courseRepository.save(firstCourse);

        Course secondCourse = new Course("HelloWorld");
        courseRepository.save(secondCourse);

        ForumTheme theme = new ForumTheme(firstCourse, "HelloWorld", new Date());
        themeRepository.save(theme);

        List<ForumTheme> themes = themeRepository.findByCourse(secondCourse);
        Assert.assertNotNull(themes);
        Assert.assertTrue(themes.isEmpty());
    }

    @Test
    public void findSeveralByCourseExists() {
        clearDatabase();
        Course course = new Course("HelloWorld");
        courseRepository.save(course);

        List<ForumTheme> themes = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            themes.add(new ForumTheme(course, String.valueOf(index), new Date()));
            themeRepository.save(themes.get(themes.size() - 1));
        }

        List<ForumTheme> foundThemes = themeRepository.findByCourse(course);
        Assert.assertNotNull(themes);
        Assert.assertEquals(themes.size(), foundThemes.size());

        for (int index = 0; index < themes.size(); index++) {
            Assert.assertEquals(themes.get(index), foundThemes.get(index));
        }
    }
}