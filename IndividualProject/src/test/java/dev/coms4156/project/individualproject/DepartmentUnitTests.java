package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests to be used for Department class.
 */
@SpringBootTest
@ContextConfiguration
class DepartmentUnitTests {

  /**
   * Set up to be run before each test.
   */
  @BeforeEach
  void setupDepartmentForTesting() {

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
  void getNumberOfMajorsTest() {
    int expectedResult = 2700;
    assertEquals(expectedResult, testDept.getNumberOfMajors(),
        "Number of Majors should be 2700");

    expectedResult = 0;
    assertEquals(expectedResult, testDept2.getNumberOfMajors(),
        "Number of Majors should be 0");
  }

  /**
   * Test for Department class getDepartmentChair method.
   */
  @Test
  void getDepartmentChairTest() {
    String expectedResult = "Luca Carloni";
    assertEquals(expectedResult, testDept.getDepartmentChair(),
        "Department Chair should be Luca Carloni");

    expectedResult = "";
    assertEquals(expectedResult, testDept2.getDepartmentChair(),
        "Department Chair should be empty string");
  }

  /**
   * Test for Department class getCourseSelection method.
   */
  @Test
  void getCourseSelectionTest() {
    assertEquals(coursesMap, testDept.getCourseSelection(),
        "Course selection should match courseMap");

    assertNotNull(testDept2.getCourseSelection(), "Course selection should not be null");
    assertEquals(new HashMap<>(), testDept2.getCourseSelection(),
        "Course selection should be empty HashMap");
  }

  /**
   * Test for Department class addPersonToMajor method.
   */
  @Test
  void addPersonToMajorTest() {
    int expectedResult = 2701;
    testDept.addPersonToMajor();
    assertEquals(expectedResult, testDept.getNumberOfMajors(),
        "Number of majors should be 2701 after adding one person to testDept");

    expectedResult = 1;
    testDept2.addPersonToMajor();
    assertEquals(expectedResult, testDept2.getNumberOfMajors(),
        "Number of majors should be 1 after adding one person to testDept2");

    assertEquals(Integer.MAX_VALUE, testDept3.getNumberOfMajors(),
        "Number of majors should be Integer.MAX_VALUE before adding more people to testDept3");
    testDept3.addPersonToMajor();
    testDept3.addPersonToMajor();
    assertEquals(Integer.MAX_VALUE, testDept3.getNumberOfMajors(),
        "Number of majors should remain Integer.MAX_VALUE after "
            + "attempting to add more people to testDept3");
  }

  /**
   * Test for Department class dropPersonFromMajor method.
   */
  @Test
  void dropPersonFromMajorTest() {
    int expectedResult = 2699;
    testDept.dropPersonFromMajor();
    assertEquals(expectedResult, testDept.getNumberOfMajors(),
        "Number of majors should be 2699 after dropping one person from testDept");

    expectedResult = 0;
    testDept2.dropPersonFromMajor();
    assertEquals(expectedResult, testDept2.getNumberOfMajors(),
        "Number of majors should remain 0 after dropping one person from testDept2");
    testDept3.dropPersonFromMajor();
    testDept3.dropPersonFromMajor();
    assertEquals(expectedResult, testDept2.getNumberOfMajors(),
        "Number of majors should remain 0 after dropping two person from testDept3");
  }

  /**
   * Test for Department class addCourse method.
   */
  @Test
  void addCourseTest() {

    String[] courseIds = {"1004", "3134", "3157", "2323"};

    Map<String, Course> expectedCoursesMap = new HashMap<>();
    expectedCoursesMap.put(courseIds[0], testCourse);
    expectedCoursesMap.put(courseIds[1], testCourse2);
    expectedCoursesMap.put(courseIds[2], testCourse3);
    expectedCoursesMap.put(courseIds[3], testCourse);

    testDept.addCourse(courseIds[3], testCourse);
    assertEquals(expectedCoursesMap, testDept.getCourseSelection(),
        "CourseSelection should match courseMap");

    testDept.addCourse("3535", null);
    assertEquals(expectedCoursesMap, testDept.getCourseSelection(),
        "CourseSelection should match courseMap when adding null course");

    testDept.addCourse(null, null);
    assertEquals(expectedCoursesMap, testDept.getCourseSelection(),
        "CourseSelection should match courseMap when adding null courseID and null course");

    expectedCoursesMap.replace(courseIds[3], testCourse3);
    testDept.addCourse(courseIds[3], testCourse3);
    assertEquals(expectedCoursesMap, testDept.getCourseSelection(),
        "CourseSelection should match courseMap after replace");
  }

  /**
   * Test for Department class createCourse method.
   */
  @Test
  void createCourseTest() {
    Map<String, Course> expectedCoursesMap = new HashMap<>();
    String newCourseId = "7777";
    expectedCoursesMap.put("1004", testCourse);
    expectedCoursesMap.put("3134", testCourse2);
    expectedCoursesMap.put("3157", testCourse3);
    Course newCourse = new Course("Cool", "LER 202", "11:40-12:55", 250);
    expectedCoursesMap.put(newCourseId, newCourse);

    testDept.createCourse(newCourseId, "Cool", "LER 202", "11:40-12:55", 250);

    Map<String, Course> actualCoursesMap = testDept.getCourseSelection();
    assertEquals(expectedCoursesMap.keySet(), actualCoursesMap.keySet(),
        "Expected and actual key sets should match");
    for (String courseId : expectedCoursesMap.keySet()) {
      Course expected = expectedCoursesMap.get(courseId);
      Course actual = actualCoursesMap.get(courseId);
      assertEquals(expected.getInstructorName(), actual.getInstructorName(),
          "Expected and actual instructor name should match");
      assertEquals(expected.getCourseLocation(), actual.getCourseLocation(),
          "Expected and actual course location should match");
      assertEquals(expected.getCourseTimeSlot(), actual.getCourseTimeSlot(),
          "Expected and actual course timeSlot should match");
      assertEquals(expected.getEnrollmentCapacity(), actual.getEnrollmentCapacity(),
          "Expected and actual enrollment capacity should match");
      assertEquals(expected.getEnrolledStudentCount(), actual.getEnrolledStudentCount(),
          "Expected and actual enrolledStudentCount should match");

    }

    testDept.createCourse(null, null, null, null, -555);
    actualCoursesMap = testDept.getCourseSelection();
    assertEquals(expectedCoursesMap.keySet(), actualCoursesMap.keySet(),
        "Expected and actual key sets should match");

    Course newCourse2 = new Course("Unknown", "Unknown", "Unknown", 0);
    expectedCoursesMap.replace(newCourseId, newCourse2);
    testDept.createCourse(newCourseId, null, null, null, -555);
    actualCoursesMap = testDept.getCourseSelection();
    assertEquals(expectedCoursesMap.keySet(), actualCoursesMap.keySet(),
        "Expected and actual key sets should match");
    for (String courseId : expectedCoursesMap.keySet()) {
      Course expected = expectedCoursesMap.get(courseId);
      Course actual = actualCoursesMap.get(courseId);
      assertEquals(expected.getInstructorName(), actual.getInstructorName(),
          "Expected and actual instructor name should match");
      assertEquals(expected.getCourseLocation(), actual.getCourseLocation(),
          "Expected and actual course location should match");
      assertEquals(expected.getCourseTimeSlot(), actual.getCourseTimeSlot(),
          "Expected and actual course timeSlot should match");
      assertEquals(expected.getEnrollmentCapacity(), actual.getEnrollmentCapacity(),
          "Expected and actual enrollment capacity should match");
      assertEquals(expected.getEnrolledStudentCount(), actual.getEnrolledStudentCount(),
          "Expected and actual enrolledStudentCount should match");
    }

    testDept.createCourse("77777", null, null, null, -555);
    actualCoursesMap = testDept.getCourseSelection();
    assertNotEquals(expectedCoursesMap.keySet(), actualCoursesMap.keySet(),
        "Expected and actual key sets should not match");
  }

  /**
   * Test for Department class toString method.
   */
  @Test
  void toStringTest() {
    String expectedResult = """
        COMS 1004:\s
        Instructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55
        COMS 3157:\s
        Instructor: Prof. K; Location: Mudd 343; Time: 1 to 4 pm
        COMS 3134:\s
        Instructor: Unknown; Location: ; Time: Unknown
        """;
    assertEquals(expectedResult, testDept.toString(),
        "Expected and actual string should be the same");

    expectedResult = "";
    assertEquals(expectedResult, testDept2.toString(),
        "Expected and actual string should be the same");

    testDept2 = new Department("0.\\n3/", coursesMap, "", -3);
    expectedResult = """
        0.\\n3/ 1004:\s
        Instructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55
        0.\\n3/ 3157:\s
        Instructor: Prof. K; Location: Mudd 343; Time: 1 to 4 pm
        0.\\n3/ 3134:\s
        Instructor: Unknown; Location: ; Time: Unknown
        """;
    assertEquals(expectedResult, testDept2.toString(),
        "Expected and actual string should be the same");
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
  public static Map<String, Course> coursesMap;
}