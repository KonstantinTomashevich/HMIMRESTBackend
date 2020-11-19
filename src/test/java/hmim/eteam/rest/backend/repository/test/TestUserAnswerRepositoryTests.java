package hmim.eteam.rest.backend.repository.test;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.test.*;
import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.repository.course.CourseRepository;
import hmim.eteam.rest.backend.repository.course.CourseThemeRepository;
import hmim.eteam.rest.backend.repository.user.SiteUserRepository;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
public class TestUserAnswerRepositoryTests {

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private SiteUserRepository siteUserRepository;

    @Autowired
    private CourseThemeRepository courseThemeRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TestQuestionRepository testQuestionRepository;

    @Autowired
    private TestAnswerRepository testAnswerRepository;

    @Autowired
    private TestUserAnswerRepository testUserAnswerRepository;

    public void clearDatabase() {
        testRepository.deleteAll();
        testResultRepository.deleteAll();
        siteUserRepository.deleteAll();
        courseThemeRepository.deleteAll();
        courseRepository.deleteAll();
        testAnswerRepository.deleteAll();
        testQuestionRepository.deleteAll();
        testUserAnswerRepository.deleteAll();
    }

    @org.junit.Test
    public void findBySiteUserAndResultExists() {
        clearDatabase();

        Course firstCourse = new Course("CourseName");
        courseRepository.save(firstCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1, firstCourse, "Name");
        courseThemeRepository.save(firstCourseTheme);

        Test firstTest = new Test(1,firstCourseTheme,"Name");
        testRepository.save(firstTest);

        TestQuestion firstQuestion = new TestQuestion(1,firstTest,"Text");
        testQuestionRepository.save(firstQuestion);

        TestAnswer firstAnswer = new TestAnswer(1,firstQuestion,"Answer",10);
        testAnswerRepository.save(firstAnswer);

        SiteUser firstUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(firstUser);

        TestResult firstResult = new TestResult(firstTest,firstUser,10,new Date());
        testResultRepository.save(firstResult);

        TestUserAnswer testUserAnswer = new TestUserAnswer(firstAnswer,firstUser,firstResult);
        testUserAnswerRepository.save(testUserAnswer);

        List<TestUserAnswer> testUserAnswers = testUserAnswerRepository.findBySiteUserAndResult(firstUser,firstResult);

        Assert.assertNotNull(testUserAnswers);
        Assert.assertEquals(testUserAnswers.size(),1);
        Assert.assertEquals(testUserAnswers.get(0),testUserAnswer);

    }


    @org.junit.Test
    public void findBySiteUserAndResultNotExists() {
        clearDatabase();

        Course firstCourse = new Course("CourseName");
        courseRepository.save(firstCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1, firstCourse, "Name");
        courseThemeRepository.save(firstCourseTheme);

        Test firstTest = new Test(1,firstCourseTheme,"Name");
        testRepository.save(firstTest);

        TestQuestion firstQuestion = new TestQuestion(1,firstTest,"Text");
        testQuestionRepository.save(firstQuestion);

        TestAnswer firstAnswer = new TestAnswer(1,firstQuestion,"Answer",10);
        testAnswerRepository.save(firstAnswer);

        SiteUser firstUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(firstUser);

        SiteUser secondUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(secondUser);

        TestResult firstResult = new TestResult(firstTest,firstUser,10,new Date());
        testResultRepository.save(firstResult);

        TestUserAnswer testUserAnswer = new TestUserAnswer(firstAnswer,firstUser,firstResult);
        testUserAnswerRepository.save(testUserAnswer);

        List<TestUserAnswer> testUserAnswers = testUserAnswerRepository.findBySiteUserAndResult(secondUser,firstResult);

        Assert.assertNotNull(testUserAnswers);
        Assert.assertTrue(testUserAnswers.isEmpty());

    }

    @org.junit.Test
    public void findSeveralBySiteUserAndResult() {
        clearDatabase();

        Course firstCourse = new Course("CourseName");
        courseRepository.save(firstCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1, firstCourse, "Name");
        courseThemeRepository.save(firstCourseTheme);

        Test firstTest = new Test(1,firstCourseTheme,"Name");
        testRepository.save(firstTest);

        TestQuestion firstQuestion = new TestQuestion(1,firstTest,"Text");
        testQuestionRepository.save(firstQuestion);

        TestAnswer firstAnswer = new TestAnswer(1,firstQuestion,"Answer",10);
        testAnswerRepository.save(firstAnswer);

        SiteUser firstUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(firstUser);

        TestResult firstResult = new TestResult(firstTest,firstUser,10,new Date());
        testResultRepository.save(firstResult);

        List<TestUserAnswer> testUserAnswers = new ArrayList<TestUserAnswer>();
        for(int i = 0;i<10;i++){
            testUserAnswers.add(new TestUserAnswer(firstAnswer,firstUser,firstResult));
            testUserAnswerRepository.save(testUserAnswers.get(testUserAnswers.size()-1));
        }

        List<TestUserAnswer> foundedTestUserAnswers = testUserAnswerRepository.findBySiteUserAndResult(firstUser,firstResult);
        Assert.assertNotNull(foundedTestUserAnswers);
        Assert.assertTrue(foundedTestUserAnswers.containsAll(testUserAnswers));
        Assert.assertTrue(testUserAnswers.containsAll(foundedTestUserAnswers));
    }
}