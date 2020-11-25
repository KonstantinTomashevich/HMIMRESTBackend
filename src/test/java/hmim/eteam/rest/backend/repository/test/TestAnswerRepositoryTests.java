package hmim.eteam.rest.backend.repository.test;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.test.Test;
import hmim.eteam.rest.backend.entity.test.TestAnswer;
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
public class TestAnswerRepositoryTests {
    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestQuestionRepository testQuestionRepository;

    @Autowired
    private CourseThemeRepository courseThemeRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TestAnswerRepository testAnswerRepository;

    public void clearDatabase(){
        testQuestionRepository.deleteAll();
        testRepository.deleteAll();
        courseRepository.deleteAll();
        courseThemeRepository.deleteAll();
        testAnswerRepository.deleteAll();
    }

    @org.junit.Test
    public void findByQuestionExists(){
        clearDatabase();

        Course firstCourse = new Course(0, "CourseName");
        courseRepository.save(firstCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1, firstCourse, "Name");
        courseThemeRepository.save(firstCourseTheme);

        Test firstTest = new Test(1,firstCourseTheme,"Name");
        testRepository.save(firstTest);

        TestQuestion firstQuestion = new TestQuestion(1,firstTest,"Text");
        testQuestionRepository.save(firstQuestion);

        TestAnswer firstAnswer = new TestAnswer(1,firstQuestion,"Answer",10);
        testAnswerRepository.save(firstAnswer);

        List<TestAnswer> testAnswers = testAnswerRepository.findByQuestionOrderByPriority(firstQuestion);

        Assert.assertNotNull(testAnswers);
        Assert.assertEquals(testAnswers.size(),1);
        Assert.assertEquals(testAnswers.get(0),firstAnswer);
    }

    @org.junit.Test
    public void findByQuestionNotExists(){
        clearDatabase();

        Course firstCourse = new Course(0, "CourseName");
        courseRepository.save(firstCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1, firstCourse, "Name");
        courseThemeRepository.save(firstCourseTheme);

        Test firstTest = new Test(1,firstCourseTheme,"Name");
        testRepository.save(firstTest);

        TestQuestion firstQuestion = new TestQuestion(1,firstTest,"Text");
        testQuestionRepository.save(firstQuestion);

        TestQuestion secondQuestion = new TestQuestion(1,firstTest,"Text");
        testQuestionRepository.save(secondQuestion);

        TestAnswer firstAnswer = new TestAnswer(1,firstQuestion,"Answer",10);
        testAnswerRepository.save(firstAnswer);

        List<TestAnswer> testAnswers = testAnswerRepository.findByQuestionOrderByPriority(secondQuestion);

        Assert.assertNotNull(testAnswers);
        Assert.assertTrue(testAnswers.isEmpty());
    }

    @org.junit.Test
    public void findSeveralByQuestion(){
        clearDatabase();

        Course firstCourse = new Course(0, "CourseName");
        courseRepository.save(firstCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1, firstCourse, "Name");
        courseThemeRepository.save(firstCourseTheme);

        Test firstTest = new Test(1,firstCourseTheme,"Name");
        testRepository.save(firstTest);

        TestQuestion firstQuestion = new TestQuestion(1,firstTest,"Text");
        testQuestionRepository.save(firstQuestion);

        List<TestAnswer> testAnswers = new ArrayList<>();
        for(int i=0;i<10;i++) {
            testAnswers.add(new TestAnswer(1,firstQuestion,"Answer",10));
            testAnswerRepository.save(testAnswers.get(testAnswers.size()-1));
        }

        List<TestAnswer> foundedTestAnswers = testAnswerRepository.findByQuestionOrderByPriority(firstQuestion);
        Assert.assertNotNull(foundedTestAnswers);
        Assert.assertEquals(testAnswers.size(), foundedTestAnswers.size());

        Assert.assertTrue(foundedTestAnswers.containsAll(testAnswers));
        Assert.assertTrue(testAnswers.containsAll(foundedTestAnswers));

        for (int index = 1; index < foundedTestAnswers.size(); ++index) {
            long previous = foundedTestAnswers.get(index - 1).getPriority();
            long current = foundedTestAnswers.get(index).getPriority();
            Assert.assertTrue(previous <= current);
        }

    }
}
