package hmim.eteam.rest.backend.repository.test;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.test.Test;
import hmim.eteam.rest.backend.entity.test.TestQuestion;
import hmim.eteam.rest.backend.repository.course.CourseRepository;
import hmim.eteam.rest.backend.repository.course.CourseThemeRepository;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
public class TestQuestionRepositoryTests {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestQuestionRepository testQuestionRepository;

    @Autowired
    private CourseThemeRepository courseThemeRepository;

    @Autowired
    private CourseRepository courseRepository;

    public void clearDatabase(){
        testQuestionRepository.deleteAll();
        testRepository.deleteAll();
        courseRepository.deleteAll();
        courseThemeRepository.deleteAll();
    }

    @org.junit.Test
    public void findByTestExists(){
        clearDatabase();

        Course firstCourse = new Course("CourseName");
        courseRepository.save(firstCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1, firstCourse, "Name");
        courseThemeRepository.save(firstCourseTheme);

        Test firstTest = new Test(1,firstCourseTheme,"Name");
        testRepository.save(firstTest);

        TestQuestion firstQuestion = new TestQuestion(1,firstTest,"Text");
        testQuestionRepository.save(firstQuestion);

        List<TestQuestion> testQuestions = testQuestionRepository.findByTestOrderByPriorityAsc(firstTest);

        Assert.assertNotNull(testQuestions);
        Assert.assertEquals(testQuestions.size(),1);
        Assert.assertEquals(firstQuestion,testQuestions.get(0));
    }

    @org.junit.Test
    public void findByTestNotExists(){
        clearDatabase();

        Course firstCourse = new Course("CourseName");
        courseRepository.save(firstCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1, firstCourse, "Name");
        courseThemeRepository.save(firstCourseTheme);

        Test firstTest = new Test(1,firstCourseTheme,"Name");
        testRepository.save(firstTest);

        Test secondTest = new Test(1,firstCourseTheme,"Name");
        testRepository.save(secondTest);

        TestQuestion firstQuestion = new TestQuestion(1,secondTest,"Text");
        testQuestionRepository.save(firstQuestion);


        List<TestQuestion> testQuestions = testQuestionRepository.findByTestOrderByPriorityAsc(firstTest);

        Assert.assertNotNull(testQuestions);
        Assert.assertTrue(testQuestions.isEmpty());
    }

    @org.junit.Test
    public void findSeveralByTest(){
        clearDatabase();

        Course firstCourse = new Course("CourseName");
        courseRepository.save(firstCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1, firstCourse, "Name");
        courseThemeRepository.save(firstCourseTheme);

        Test firstTest = new Test(1,firstCourseTheme,"Name");
        testRepository.save(firstTest);


        List<TestQuestion> testQuestions = new ArrayList<TestQuestion>();
        for(int i=0;i<10;i++) {
            testQuestions.add(new TestQuestion(9-i,firstTest,"Text"));
            testQuestionRepository.save(testQuestions.get(testQuestions.size()-1));
        }

        List<TestQuestion> foundedTestQuestions = testQuestionRepository.findByTestOrderByPriorityAsc(firstTest);
        Assert.assertNotNull(foundedTestQuestions);
        Assert.assertEquals(testQuestions.size(), foundedTestQuestions.size());

        Assert.assertTrue(foundedTestQuestions.containsAll(testQuestions));
        Assert.assertTrue(testQuestions.containsAll(foundedTestQuestions));

        for (int index = 1; index < foundedTestQuestions.size(); ++index) {
            long previous = foundedTestQuestions.get(index - 1).getPriority();
            long current = foundedTestQuestions.get(index).getPriority();
            Assert.assertTrue(previous <= current);
        }

    }
}
