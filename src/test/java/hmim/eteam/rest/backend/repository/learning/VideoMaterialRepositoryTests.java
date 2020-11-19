package hmim.eteam.rest.backend.repository.learning;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.learning.VideoMaterial;
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
public class VideoMaterialRepositoryTests {

    @Autowired
    private CourseThemeRepository courseThemeRepository;

    @Autowired
    private VideoMaterialRepository videoMaterialRepository;

    @Autowired
    private CourseRepository courseRepository;

    public void clearDatabase(){
        courseThemeRepository.deleteAll();
        videoMaterialRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Test
    public void findByThemeExists(){
        clearDatabase();

        Course firstCourse = new Course("CourseName");
        courseRepository.save(firstCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1, firstCourse, "CourseTheme");
        courseThemeRepository.save(firstCourseTheme);

        VideoMaterial firstVideoMaterial = new VideoMaterial(1,firstCourseTheme,"Name","Url");
        videoMaterialRepository.save(firstVideoMaterial);

        List<VideoMaterial> videoMaterials = videoMaterialRepository.findByThemeOrderByPriorityAsc(firstCourseTheme);

        Assert.assertNotNull(videoMaterials);
        Assert.assertEquals(videoMaterials.size(),1);
        Assert.assertEquals(videoMaterials.get(0),firstVideoMaterial);
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

        VideoMaterial firstVideoMaterial = new VideoMaterial(1,firstCourseTheme,"Name","Url");
        videoMaterialRepository.save(firstVideoMaterial);

        List<VideoMaterial> videoMaterials = videoMaterialRepository.findByThemeOrderByPriorityAsc(secondCourseTheme);

        Assert.assertNotNull(videoMaterials);
        Assert.assertTrue(videoMaterials.isEmpty());
    }

    @Test
    public void findSeveralByTheme(){
        clearDatabase();

        Course firstCourse = new Course("CourseName");
        courseRepository.save(firstCourse);

        CourseTheme firstCourseTheme = new CourseTheme(1, firstCourse, "CourseTheme");
        courseThemeRepository.save(firstCourseTheme);

        List<VideoMaterial> videoMaterials = new ArrayList<VideoMaterial>();
        for(int i = 0; i < 10; i++) {
            videoMaterials.add(new VideoMaterial(9-i, firstCourseTheme, "Name", "Url"));
            videoMaterialRepository.save(videoMaterials.get(videoMaterials.size()-1));
        }

        List<VideoMaterial> foundedVideoMaterials = videoMaterialRepository.findByThemeOrderByPriorityAsc(firstCourseTheme);

        Assert.assertNotNull(foundedVideoMaterials);
        Assert.assertEquals(foundedVideoMaterials.size(),videoMaterials.size());
        Assert.assertTrue(videoMaterials.containsAll(foundedVideoMaterials));
        Assert.assertTrue(foundedVideoMaterials.containsAll(videoMaterials));

        for(int i =1; i<foundedVideoMaterials.size(); i++) {
            VideoMaterial previous = foundedVideoMaterials.get(i-1);
            VideoMaterial last = foundedVideoMaterials.get(i);
            Assert.assertTrue(previous.getPriority() <= last.getPriority());
        }
    }

}
