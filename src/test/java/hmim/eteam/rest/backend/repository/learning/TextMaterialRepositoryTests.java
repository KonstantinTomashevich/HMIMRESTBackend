package hmim.eteam.rest.backend.repository.learning;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.learning.TextMaterial;
import hmim.eteam.rest.backend.repository.course.CourseRepository;
import hmim.eteam.rest.backend.repository.course.CourseThemeRepository;
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
public class TextMaterialRepositoryTests {
    @Autowired
    private CourseThemeRepository courseThemeRepository;

    @Autowired
    private TextMaterialRepository textMaterialRepository;

    @Autowired
    private CourseRepository courseRepository;

    public void clearDatabase(){
        courseThemeRepository.deleteAll();
        textMaterialRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Test
    public void findByThemeExists(){
        clearDatabase();

        Course firstCourse = new Course("CourseName");
        courseRepository.save(firstCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1, firstCourse, "CourseTheme");
        courseThemeRepository.save(firstCourseTheme);

        TextMaterial firstTextMaterial = new TextMaterial(1,firstCourseTheme,"Name","Text");
        textMaterialRepository.save(firstTextMaterial);

        List<TextMaterial> textMaterials = textMaterialRepository.findByThemeOrderByPriorityAsc(firstCourseTheme);

        Assert.assertNotNull(textMaterials);
        Assert.assertEquals(textMaterials.size(),1);
        Assert.assertEquals(textMaterials.get(0),firstTextMaterial);
    }

    @Test
    public void findByThemeNotExists(){
        clearDatabase();

        Course firstCourse = new Course("CourseName");
        courseRepository.save(firstCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1, firstCourse, "CourseTheme");
        courseThemeRepository.save(firstCourseTheme);

        CourseTheme secondCourseTheme = new CourseTheme(1, firstCourse, "CourseTheme");
        courseThemeRepository.save(secondCourseTheme);

        TextMaterial firstTextMaterial = new TextMaterial(1,firstCourseTheme,"Name","Text");
        textMaterialRepository.save(firstTextMaterial);

        List<TextMaterial> textMaterials = textMaterialRepository.findByThemeOrderByPriorityAsc(secondCourseTheme);

        Assert.assertNotNull(textMaterials);
        Assert.assertTrue(textMaterials.isEmpty());
    }

    @Test
    public void findSeveralByTheme(){
        clearDatabase();

        Course firstCourse = new Course("CourseName");
        courseRepository.save(firstCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1, firstCourse, "CourseTheme");
        courseThemeRepository.save(firstCourseTheme);

        List<TextMaterial> textMaterials = new ArrayList<TextMaterial>();
        for(int i = 0; i < 10; i++) {
            textMaterials.add(new TextMaterial(9-i, firstCourseTheme, "Name", "Text"));
            textMaterialRepository.save(textMaterials.get(textMaterials.size()-1));
        }

        List<TextMaterial> foundedTextMaterials = textMaterialRepository.findByThemeOrderByPriorityAsc(firstCourseTheme);

        Assert.assertNotNull(foundedTextMaterials);
        Assert.assertEquals(foundedTextMaterials.size(),textMaterials.size());
        Assert.assertTrue(textMaterials.containsAll(foundedTextMaterials));
        Assert.assertTrue(foundedTextMaterials.containsAll(textMaterials));

        for(int i =1; i<foundedTextMaterials.size(); i++) {
            TextMaterial previous = foundedTextMaterials.get(i-1);
            TextMaterial last = foundedTextMaterials.get(i);
            Assert.assertTrue(previous.getPriority() <= last.getPriority());
        }
    }

}

