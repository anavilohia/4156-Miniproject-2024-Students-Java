package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests to be used for Course class.
 */
@SpringBootTest
@ContextConfiguration
class CourseUnitTests {

  /**
   * Set up to be run before all tests.
   */
  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourse2 = new Course(null, "", null, 0);
  }

  /**
   * Test for Course class enrollStudent method.
   */
  @Test
  public void enrollStudentTest() {
    testCourse.setEnrolledStudentCount(249);
    assertTrue(testCourse.enrollStudent());
    assertFalse(testCourse.enrollStudent());
  }

  /**
   * Test for Course class dropStudent method.
   */
  @Test
  public void dropStudentTest() {
    testCourse.setEnrolledStudentCount(1);
    assertTrue(testCourse.dropStudent());
    assertFalse(testCourse.dropStudent());
  }

  /**
   * Test for Course class getCourseLocation method.
   */
  @Test
  public void getCourseLocationTest() {
    String expectedResult = "417 IAB";
    assertEquals(expectedResult, testCourse.getCourseLocation());

    expectedResult = "417 IA";
    assertNotEquals(expectedResult, testCourse.getCourseLocation());

    expectedResult = "417 ia";
    assertNotEquals(expectedResult, testCourse.getCourseLocation());

    expectedResult = "";
    assertEquals(expectedResult, testCourse2.getCourseLocation());
  }

  /**
   * Test for Course class getInstructorName method.
   */
  @Test
  public void getInstructorNameTest() {
    String expectedResult = "Griffin Newbold";
    assertEquals(expectedResult, testCourse.getInstructorName());

    expectedResult = "GriffinNewbold";
    assertNotEquals(expectedResult, testCourse.getInstructorName());

    expectedResult = "griffin newbold";
    assertNotEquals(expectedResult, testCourse.getInstructorName());

    expectedResult = "Unknown";
    assertEquals(expectedResult, testCourse2.getInstructorName());
  }

  /**
   * Test for Course class getCourseTimeSlot method.
   */
  @Test
  public void getCourseTimeSlotTest() {
    String expectedResult = "11:40-12:55";
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());

    expectedResult = "Unknown";
    assertEquals(expectedResult, testCourse2.getCourseTimeSlot());
  }

  /**
   * Test for Course class getEnrolledStudentCount method.
   */
  @Test
  public void getEnrolledStudentCountTest() {
    Course tempCourse = new Course(
        "Griffin Newbold",
        "417 IAB",
        "11:40-12:55",
        250);

    int expectedResult = 23;
    tempCourse.setEnrolledStudentCount(23);
    assertEquals(expectedResult, tempCourse.getEnrolledStudentCount());

    tempCourse.enrollStudent();
    tempCourse.enrollStudent();
    tempCourse.enrollStudent();
    tempCourse.dropStudent();
    expectedResult = 25;
    assertEquals(expectedResult, tempCourse.getEnrolledStudentCount());
  }

  /**
   * Test for Course class toString method.
   */
  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());

    expectedResult = "\nInstructor: Unknown; Location: ; Time: Unknown";
    assertEquals(expectedResult, testCourse2.toString());
  }

  /**
   * Test for Course class reassignInstructor method.
   */
  @Test
  public void reassignInstructorTest() {
    Course tempCourse = new Course(
        "Griffin Newbold",
        "417 IAB",
        "11:40-12:55",
        250);

    String expectedResult = "Prof. Kaiser";
    assertNotEquals(expectedResult, tempCourse.getInstructorName());
    tempCourse.reassignInstructor(expectedResult);
    assertEquals(expectedResult, tempCourse.getInstructorName());

    expectedResult = "Unknown";
    tempCourse.reassignInstructor(null);
    assertEquals(expectedResult, tempCourse.getInstructorName());

    expectedResult = "Prof.\n K\\";
    tempCourse.reassignInstructor("Prof.\n K\\");
    assertEquals(expectedResult, tempCourse.getInstructorName());
  }

  /**
   * Test for Course class reassignLocation method.
   */
  @Test
  public void reassignLocationTest() {
    Course tempCourse = new Course(
        "Griffin Newbold",
        "417 IAB",
        "11:40-12:55",
        250);

    String expectedResult = "Mudd 455";
    assertNotEquals(expectedResult, tempCourse.getCourseLocation());
    tempCourse.reassignLocation(expectedResult);
    assertEquals(expectedResult, tempCourse.getCourseLocation());

    expectedResult = "Unknown";
    tempCourse.reassignLocation(null);
    assertEquals(expectedResult, tempCourse.getCourseLocation());

    expectedResult = "-+*7ejej&&";
    tempCourse.reassignLocation("-+*7ejej&&");
    assertEquals(expectedResult, tempCourse.getCourseLocation());
  }

  /**
   * Test for Course class reassignTime method.
   */
  @Test
  public void reassignTimeTest() {
    Course tempCourse = new Course(
        "Griffin Newbold",
        "417 IAB",
        "11:40-12:55",
        250);

    String expectedResult = "11:40-12";
    assertNotEquals(expectedResult, tempCourse.getCourseTimeSlot());
    tempCourse.reassignTime(expectedResult);
    assertEquals(expectedResult, tempCourse.getCourseTimeSlot());

    expectedResult = "Unknown";
    tempCourse.reassignTime(null);
    assertEquals(expectedResult, tempCourse.getCourseTimeSlot());
  }

  /**
   * Test for Course class setEnrolledStudentCount method.
   */
  @Test
  public void setEnrolledStudentCountTest() {
    Course tempCourse = new Course(
        "Griffin Newbold",
        "417 IAB",
        "11:40-12:55",
        53);

    int expectedResult = 39;
    tempCourse.setEnrolledStudentCount(39);
    assertEquals(expectedResult, tempCourse.getEnrolledStudentCount());

    expectedResult = 53;
    tempCourse.setEnrolledStudentCount(53);
    assertEquals(expectedResult, tempCourse.getEnrolledStudentCount());

    expectedResult = 0;
    tempCourse.setEnrolledStudentCount(0);
    assertEquals(expectedResult, tempCourse.getEnrolledStudentCount());

    tempCourse.setEnrolledStudentCount(-30);
    assertEquals(expectedResult, tempCourse.getEnrolledStudentCount());

    tempCourse.setEnrolledStudentCount(250);
    assertEquals(expectedResult, tempCourse.getEnrolledStudentCount());
  }

  /**
   * Test for Course class isCourseFull method.
   */
  @Test
  public void isCourseFullTest() {
    Course tempCourse = new Course(
        "Griffin Newbold",
        "417 IAB",
        "11:40-12:55",
        17);
    for (int i = 0; i < 15; i++) {
      tempCourse.enrollStudent();
    }

    assertFalse(tempCourse.isCourseFull());

    tempCourse.enrollStudent();
    tempCourse.enrollStudent();
    assertTrue(tempCourse.isCourseFull());

    tempCourse.enrollStudent();
    assertTrue(tempCourse.isCourseFull());

    tempCourse = new Course(
        "Griffin Newbold",
        "417 IAB",
        "11:40-12:55",
        -4);

    assertTrue(tempCourse.isCourseFull());

    tempCourse = new Course(
        "Griffin Newbold",
        "417 IAB",
        "11:40-12:55",
        0);

    assertTrue(tempCourse.isCourseFull());
  }

  /**
   * These test course instances are used for testing.
   */
  public static Course testCourse;
  public static Course testCourse2;
}

