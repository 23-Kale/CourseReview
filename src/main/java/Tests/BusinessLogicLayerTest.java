package Tests;

import BusinessLogicLayer.Course;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BusinessLogicLayerTest {

    @Test
    @DisplayName("CourseFormatTest if course name is not upper case")
    public void CourseFormatTestUpper(){
        Course test1 = new Course("TEST", 3240);
        AtomicReference<Course> test2 = null;
        assertThrows(IllegalArgumentException.class, () ->
                test2.set(new Course("test", 3100)));
    }

    @Test
    @DisplayName("CourseFormatTest if course name has space")
    public void CourseFormatTestSpace(){
        AtomicReference<Course> test = null;
        assertThrows(IllegalArgumentException.class, () ->
                test.set(new Course("TE T", 3100)));
    }

    @Test
    @DisplayName("CourseFormatTest if course number < 4")
    public void CourseFormatTestLess4(){
        AtomicReference<Course> test = null;
        assertThrows(IllegalArgumentException.class, () ->
                test.set(new Course("TEST", 31)));
    }

    @Test
    @DisplayName("CourseFormatTest if course number > 4")
    public void CourseFormatTestGreater4(){
        AtomicReference<Course> test = null;
        assertThrows(IllegalArgumentException.class, () ->
                test.set(new Course("TEST", 31000)));
    }
}
