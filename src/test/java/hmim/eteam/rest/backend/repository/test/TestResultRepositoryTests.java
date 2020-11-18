package hmim.eteam.rest.backend.repository.test;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.task.CreativeTask;
import hmim.eteam.rest.backend.entity.task.CreativeTaskAnswer;
import hmim.eteam.rest.backend.entity.test.Test;
import hmim.eteam.rest.backend.entity.test.TestAnswer;
import hmim.eteam.rest.backend.entity.test.TestQuestion;
import hmim.eteam.rest.backend.entity.test.TestResult;
import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.repository.course.CourseRepository;
import hmim.eteam.rest.backend.repository.course.CourseThemeRepository;
import hmim.eteam.rest.backend.repository.test.*;
import hmim.eteam.rest.backend.repository.user.SiteUserRepository;
import org.junit.Assert;
//import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestResultRepositoryTests {

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

    public void clearDatabase() {
        testRepository.deleteAll();
        testResultRepository.deleteAll();
        siteUserRepository.deleteAll();
        courseThemeRepository.deleteAll();
        courseRepository.deleteAll();
        testAnswerRepository.deleteAll();
        testQuestionRepository.deleteAll();
    }

    @org.junit.Test
    public void findByStudentAndTaskExists() {
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

        TestResult testResult = new TestResult(firstTest,firstUser,10,new Date());
        testResultRepository.save(testResult);

        List<TestResult> testResults = testResultRepository.findBySiteUserAndTestOrderByFinishDateAsc(firstUser,firstTest);

        Assert.assertNotNull(testResults);
        Assert.assertEquals(testResults.size(),1);
        Assert.assertEquals(testResult,testResults.get(0));
    }

    @org.junit.Test
    public void findByStudentAndTaskNotExists() {
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

        TestResult testResult = new TestResult(firstTest,firstUser,10,new Date());
        testResultRepository.save(testResult);

        List<TestResult> testResults = testResultRepository.findBySiteUserAndTestOrderByFinishDateAsc(secondUser,firstTest);

        Assert.assertNotNull(testResults);
        Assert.assertTrue(testResults.isEmpty());
    }

    @org.junit.Test
    public void findSeveralByStudentAndTask() {
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

        List<TestResult> testResults = new ArrayList<TestResult>();
        for(int i=0;i<10;i++) {
            testResults.add(new TestResult(firstTest,firstUser,10,new Date(100-i)));
            testResultRepository.save(testResults.get(testResults.size()-1));
        }

        List<TestResult> foundedTestResults = testResultRepository.findBySiteUserAndTestOrderByFinishDateAsc(firstUser,firstTest);

        Assert.assertNotNull(foundedTestResults);
        Assert.assertEquals(testResults.size(), foundedTestResults.size());

        Assert.assertTrue(foundedTestResults.containsAll(testResults));
        Assert.assertTrue(testResults.containsAll(foundedTestResults));

        for (int index = 1; index < foundedTestResults.size(); ++index) {
            Date previous = foundedTestResults.get(index - 1).getFinishDate();
            Date current = foundedTestResults.get(index).getFinishDate();
            Assert.assertTrue(previous.before(current));
        }
    }
}