package hmim.eteam.rest.backend.repository.course;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.course.CourseTheme;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CourseThemeRepositoryTests {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseThemeRepository courseThemeRepository;

    public void clearDatabase(){
        courseRepository.deleteAll();
        courseThemeRepository.deleteAll();
    }

    @Test
    public void findByCourseExists(){
        clearDatabase();

        Course firstCourse = new Course(0, "CourseName");
        courseRepository.save(firstCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1, firstCourse, "CourseTheme");
        courseThemeRepository.save(firstCourseTheme);

        List<CourseTheme> courseThemes = courseThemeRepository.findByCourseOrderByPriorityAsc(firstCourse);
        Assert.assertNotNull(courseThemes);
        Assert.assertEquals(courseThemes.size(),1);
        Assert.assertEquals(courseThemes.get(0),firstCourseTheme);
    }

    @Test
    public void findByCourseNotExists(){
        clearDatabase();

        Course firstCourse = new Course(0, "CourseName");
        courseRepository.save(firstCourse);

        Course secondCourse = new Course(0, "CourseName");
        courseRepository.save(secondCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1, firstCourse, "CourseTheme");
        courseThemeRepository.save(firstCourseTheme);

        List<CourseTheme> courseThemes = courseThemeRepository.findByCourseOrderByPriorityAsc(secondCourse);
        Assert.assertNotNull(courseThemes);
        Assert.assertTrue(courseThemes.isEmpty());
    }

    @Test
    public void findSeveralByCourse(){
        clearDatabase();

        Course firstCourse = new Course(0, "CourseName");
        courseRepository.save(firstCourse);

        List<CourseTheme> courseThemes = new ArrayList<>();
        for(int i = 0; i<10; i++) {
            courseThemes.add(new CourseTheme(9-i, firstCourse, "CourseTheme"));
            courseThemeRepository.save(courseThemes.get(courseThemes.size() - 1));
        }

        List<CourseTheme> foundedCourseThemes = courseThemeRepository.findByCourseOrderByPriorityAsc(firstCourse);
        Assert.assertNotNull(foundedCourseThemes);
        Assert.assertEquals(foundedCourseThemes.size(),courseThemes.size());
        Assert.assertTrue(courseThemes.containsAll(foundedCourseThemes));
        Assert.assertTrue(foundedCourseThemes.containsAll(courseThemes));

        for(int i =1; i<foundedCourseThemes.size(); i++) {
            CourseTheme previous = foundedCourseThemes.get(i-1);
            CourseTheme last = foundedCourseThemes.get(i);
            Assert.assertTrue(previous.getPriority() <= last.getPriority());
        }
    }
}
