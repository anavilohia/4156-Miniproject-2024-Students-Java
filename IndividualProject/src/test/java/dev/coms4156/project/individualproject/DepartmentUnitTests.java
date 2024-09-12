package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests to be used for Department class.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  /**
   * Set up to be run before all tests.
   */
  @BeforeEach
  public void setupDepartmentForTesting() {

    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourse.setEnrolledStudentCount(249);
    testCourse2 = new Course(null, "", null, 0);
    testCourse2.setEnrolledStudentCount(0);
    testCourse3 = new Course("Prof. K", "Mudd 343", "1 to 4 pm", 34);
    testCourse3.setEnrolledStudentCount(12);
    coursesMap = new HashMap<>();
    coursesMap.put("1004", testCourse);
    coursesMap.put("3134", testCourse2);
    coursesMap.put("3157", testCourse3);

    testDept = new Department("COMS", coursesMap, "Luca Carloni", 2700);
    testDept2 = new Department("0.\\n3/", null, "", -3);
    testDept3 = new Department(null, coursesMap, null, Integer.MAX_VALUE);
  }

  /**
   * Test for Department class getNumberOfMajors method.
   */
  @Test
  public void getNumberOfMajorsTest() {
    int expectedResult = 2700;
    assertEquals(expectedResult, testDept.getNumberOfMajors());

    expectedResult = 0;
    assertEquals(expectedResult, testDept2.getNumberOfMajors());
  }

  /**
   * Test for Department class getDepartmentChair method.
   */
  @Test
  public void getDepartmentChairTest() {
    String expectedResult = "Luca Carloni";
    assertEquals(expectedResult, testDept.getDepartmentChair());

    expectedResult = "";
    assertEquals(expectedResult, testDept2.getDepartmentChair());
  }

  /**
   * Test for Department class getCourseSelection method.
   */
  @Test
  public void getCourseSelectionTest() {
    assertEquals(coursesMap, testDept.getCourseSelection());

    assertNotNull(testDept2.getCourseSelection());
    assertEquals(new HashMap<>(), testDept2.getCourseSelection());
  }

  /**
   * Test for Department class addPersonToMajor method.
   */
  @Test
  public void addPersonToMajorTest() {
    int expectedResult = 2701;
    testDept.addPersonToMajor();
    assertEquals(expectedResult, testDept.getNumberOfMajors());

    expectedResult = 1;
    testDept2.addPersonToMajor();
    assertEquals(expectedResult, testDept2.getNumberOfMajors());

    assertEquals(Integer.MAX_VALUE, testDept3.getNumberOfMajors());
    testDept3.addPersonToMajor();
    testDept3.addPersonToMajor();
    assertEquals(Integer.MAX_VALUE, testDept3.getNumberOfMajors());
  }

  /**
   * Test for Department class dropPersonFromMajor method.
   */
  @Test
  public void dropPersonFromMajorTest() {
    int expectedResult = 2699;
    testDept.dropPersonFromMajor();
    assertEquals(expectedResult, testDept.getNumberOfMajors());

    expectedResult = 0;
    testDept2.dropPersonFromMajor();
    assertEquals(expectedResult, testDept2.getNumberOfMajors());
    testDept3.dropPersonFromMajor();
    testDept3.dropPersonFromMajor();
    assertEquals(expectedResult, testDept2.getNumberOfMajors());
  }

  /**
   * Test for Department class addCourse method.
   */
  @Test
  public void addCourseTest() {

    HashMap<String, Course> expectedCoursesMap = new HashMap<>();
    expectedCoursesMap.put("1004", testCourse);
    expectedCoursesMap.put("3134", testCourse2);
    expectedCoursesMap.put("3157", testCourse3);
    expectedCoursesMap.put("2323", testCourse);

    testDept.addCourse("2323", testCourse);
    assertEquals(expectedCoursesMap, testDept.getCourseSelection());

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      testDept.addCourse("3535", null);
    });
    assertEquals(
        "Course cannot be null",
        exception.getMessage());
    assertEquals(expectedCoursesMap, testDept.getCourseSelection());

    exception = assertThrows(IllegalArgumentException.class, () -> {
      testDept.addCourse(null, null);
    });
    assertEquals(
        "Course ID cannot be null",
        exception.getMessage());
    assertEquals(expectedCoursesMap, testDept.getCourseSelection());

    expectedCoursesMap.replace("2323", testCourse3);
    testDept.addCourse("2323", testCourse3);
    assertEquals(expectedCoursesMap, testDept.getCourseSelection());
  }

  /**
   * Test for Department class createCourse method.
   */
  @Test
  public void createCourseTest() {
    HashMap<String, Course> expectedCoursesMap = new HashMap<>();
    expectedCoursesMap.put("1004", testCourse);
    expectedCoursesMap.put("3134", testCourse2);
    expectedCoursesMap.put("3157", testCourse3);
    Course newCourse = new Course("Cool", "LER 202", "11:40-12:55", 250);
    expectedCoursesMap.put("7777", newCourse);

    testDept.createCourse("7777", "Cool", "LER 202", "11:40-12:55", 250);
    HashMap<String, Course> actualCoursesMap = testDept.getCourseSelection();

    assertEquals(expectedCoursesMap.keySet(), actualCoursesMap.keySet());
    for (String courseId : expectedCoursesMap.keySet()) {
      Course expected = expectedCoursesMap.get(courseId);
      Course actual = actualCoursesMap.get(courseId);
      assertTrue(expected.equals(actual));
    }

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      testDept.createCourse(null, null, null, null, -555);
    });
    assertEquals(
        "Course ID cannot be null",
        exception.getMessage());

    Course newCourse2 = new Course("Unknown", "Unknown", "Unknown", 0);
    expectedCoursesMap.replace("7777", newCourse2);
    testDept.createCourse("7777", null, null, null, -555);
    actualCoursesMap = testDept.getCourseSelection();

    assertEquals(expectedCoursesMap.keySet(), actualCoursesMap.keySet());
    for (String courseId : expectedCoursesMap.keySet()) {
      Course expected = expectedCoursesMap.get(courseId);
      Course actual = actualCoursesMap.get(courseId);
      assertTrue(expected.equals(actual));
    }

    testDept.createCourse("77777", null, null, null, -555);
    actualCoursesMap = testDept.getCourseSelection();
    assertNotEquals(expectedCoursesMap.keySet(), actualCoursesMap.keySet());
  }

  /**
   * Test for Department class toString method.
   */
  @Test
  public void toStringTest() {
    String expectedResult =
        "COMS 1004: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n"
            + "COMS 3157: \nInstructor: Prof. K; Location: Mudd 343; Time: 1 to 4 pm\n"
            + "COMS 3134: \nInstructor: Unknown; Location: ; Time: Unknown\n";
    assertEquals(expectedResult, testDept.toString());

    expectedResult = "";
    assertEquals(expectedResult, testDept2.toString());

    testDept2 = new Department("0.\\n3/", coursesMap, "", -3);
    expectedResult =
        "0.\\n3/ 1004: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n"
            + "0.\\n3/ 3157: \nInstructor: Prof. K; Location: Mudd 343; Time: 1 to 4 pm\n"
            + "0.\\n3/ 3134: \nInstructor: Unknown; Location: ; Time: Unknown\n";
    assertEquals(expectedResult, testDept2.toString());
  }

  /**
   * These test course and department instances are used for testing.
   */
  public static Course testCourse;
  public static Course testCourse2;
  public static Course testCourse3;
  public static Department testDept;
  public static Department testDept2;
  public static Department testDept3;
  public static HashMap<String, Course> coursesMap;
}