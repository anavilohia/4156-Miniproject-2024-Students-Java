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
  static void setupCourseForTesting() {
    testCourse = new Course(profName, testLocation, testTimeSlot, 250);
    testCourse2 = new Course(null, "", null, 0);
  }

  /**
   * Test for Course class enrollStudent method.
   */
  @Test
  void enrollStudentTest() {
    testCourse.setEnrolledStudentCount(249);
    assertTrue(testCourse.enrollStudent(),
        "Student should be able to enroll when course is not full");
    assertFalse(testCourse.enrollStudent(),
        "Student should not be able to enroll when course is full");
  }

  /**
   * Test for Course class dropStudent method.
   */
  @Test
  void dropStudentTest() {
    testCourse.setEnrolledStudentCount(1);
    assertTrue(testCourse.dropStudent(),
        "Student should be able to drop when enrolled student count is greater than 0");
    assertFalse(testCourse.dropStudent(),
        "Student should not be able to drop when enrolled student count is 0");
  }

  /**
   * Test for Course class getCourseLocation method.
   */
  @Test
  void getCourseLocationTest() {
    String expectedResult = testLocation;
    assertEquals(expectedResult, testCourse.getCourseLocation(),
        "Course location should be '417 IAB'");

    expectedResult = "417 IA";
    assertNotEquals(expectedResult, testCourse.getCourseLocation(),
        "Course location should not be '417 IA'");

    expectedResult = "417 ia";
    assertNotEquals(expectedResult, testCourse.getCourseLocation(),
        "Course location should not be '417 ia'");

    expectedResult = "";
    assertEquals(expectedResult, testCourse2.getCourseLocation(),
        "Course2 location should be an empty string");
  }

  /**
   * Test for Course class getInstructorName method.
   */
  @Test
  void getInstructorNameTest() {
    String expectedResult = profName;
    assertEquals(expectedResult, testCourse.getInstructorName(),
        "Instructor name should match 'Griffin Newbold'");

    expectedResult = "GriffinNewbold";
    assertNotEquals(expectedResult, testCourse.getInstructorName(),
        "Instructor name should not match");

    expectedResult = "griffin newbold";
    assertNotEquals(expectedResult, testCourse.getInstructorName(),
        "Instructor name should not match");

    expectedResult = unknown;
    assertEquals(expectedResult, testCourse2.getInstructorName(),
        "Instructor name should match 'UNKNOWN'");
  }

  /**
   * Test for Course class getCourseTimeSlot method.
   */
  @Test
  void getCourseTimeSlotTest() {
    String expectedResult = testTimeSlot;
    assertEquals(expectedResult, testCourse.getCourseTimeSlot(),
        "Time slot should match '11:40-12:55'");

    expectedResult = unknown;
    assertEquals(expectedResult, testCourse2.getCourseTimeSlot(),
        "Time slot should match 'UNKNOWN");
  }

  /**
   * Test for Course class getEnrolledStudentCount method.
   */
  @Test
  void getEnrolledStudentCountTest() {
    Course tempCourse = new Course(
        profName,
        testLocation,
        testTimeSlot,
        250);

    int expectedResult = 23;
    tempCourse.setEnrolledStudentCount(23);
    assertEquals(expectedResult, tempCourse.getEnrolledStudentCount(),
        "Enrolled student count should be 23");

    tempCourse.enrollStudent();
    tempCourse.enrollStudent();
    tempCourse.enrollStudent();
    tempCourse.dropStudent();
    expectedResult = 25;
    assertEquals(expectedResult, tempCourse.getEnrolledStudentCount(),
        "Enrolled student count should be 25");
  }

  /**
   * Test for Course class toString method.
   */
  @Test
  void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString(),
        "Expected and actual string should be the same");

    expectedResult = "\nInstructor: Unknown; Location: ; Time: Unknown";
    assertEquals(expectedResult, testCourse2.toString(),
        "Expected and actual string should be the same");
  }

  /**
   * Test for Course class reassignInstructor method.
   */
  @Test
  void reassignInstructorTest() {
    Course tempCourse = new Course(
        profName,
        testLocation,
        testTimeSlot,
        250);

    String expectedResult = "Prof. Kaiser";
    assertNotEquals(expectedResult, tempCourse.getInstructorName(),
        "Instructor name should not be the same for new Course");
    tempCourse.reassignInstructor(expectedResult);
    assertEquals(expectedResult, tempCourse.getInstructorName(),
        "Instructor name should be the same after reassign");

    expectedResult = unknown;
    tempCourse.reassignInstructor(null);
    assertEquals(expectedResult, tempCourse.getInstructorName(),
        "Instructor name should be 'UNKNOWN' after reassign to null");

    expectedResult = "Prof.\n K\\";
    tempCourse.reassignInstructor("Prof.\n K\\");
    assertEquals(expectedResult, tempCourse.getInstructorName(),
        "Instructor name should be the same after reassign");
  }

  /**
   * Test for Course class reassignLocation method.
   */
  @Test
  void reassignLocationTest() {
    Course tempCourse = new Course(
        profName,
        testLocation,
        testTimeSlot,
        250);

    String expectedResult = "Mudd 455";
    assertNotEquals(expectedResult, tempCourse.getCourseLocation(),
        "Course location should not be '417 IAB'");
    tempCourse.reassignLocation(expectedResult);
    assertEquals(expectedResult, tempCourse.getCourseLocation(),
        "Course location should be '417 IAB' after reassign");

    expectedResult = unknown;
    tempCourse.reassignLocation(null);
    assertEquals(expectedResult, tempCourse.getCourseLocation(),
        "Course location should be 'UNKNOWN' when reassigned null");

    expectedResult = "-+*7ejej&&";
    tempCourse.reassignLocation("-+*7ejej&&");
    assertEquals(expectedResult, tempCourse.getCourseLocation(),
        "Course location should be '-+*7ejej&&'");
  }

  /**
   * Test for Course class reassignTime method.
   */
  @Test
  void reassignTimeTest() {
    Course tempCourse = new Course(
        profName,
        testLocation,
        testTimeSlot,
        250);

    String expectedResult = "11:40-12";
    assertNotEquals(expectedResult, tempCourse.getCourseTimeSlot(),
        "Time slot should not be '11:40-12' for new course");
    tempCourse.reassignTime(expectedResult);
    assertEquals(expectedResult, tempCourse.getCourseTimeSlot(),
        "Time slot should be '11:40-12' after reassign");

    expectedResult = unknown;
    tempCourse.reassignTime(null);
    assertEquals(expectedResult, tempCourse.getCourseTimeSlot(),
        "Time slot should be 'UNKNOWN' when reassigned null");
  }

  /**
   * Test for Course class setEnrolledStudentCount method.
   */
  @Test
  void setEnrolledStudentCountTest() {
    Course tempCourse = new Course(
        profName,
        testLocation,
        testTimeSlot,
        53);

    int expectedResult = 39;
    tempCourse.setEnrolledStudentCount(39);
    assertEquals(expectedResult, tempCourse.getEnrolledStudentCount(),
        "EnrolledStudentCount should be 39 when set to 39 (below capacity)");

    expectedResult = 53;
    tempCourse.setEnrolledStudentCount(53);
    assertEquals(expectedResult, tempCourse.getEnrolledStudentCount(),
        "EnrolledStudentCount should be 53 when set at capacity");

    expectedResult = 0;
    tempCourse.setEnrolledStudentCount(0);
    assertEquals(expectedResult, tempCourse.getEnrolledStudentCount(),
        "EnrolledStudentCount should be 0 when set to 0");

    tempCourse.setEnrolledStudentCount(-30);
    assertEquals(expectedResult, tempCourse.getEnrolledStudentCount(),
        "EnrolledStudentCount should remain 0 when set to negative number -30");

    tempCourse.setEnrolledStudentCount(250);
    assertEquals(expectedResult, tempCourse.getEnrolledStudentCount(),
        "EnrolledStudentCount should remain 0 when set to above capacity number 250");
  }

  /**
   * Test for Course class isCourseFull method.
   */
  @Test
  void isCourseFullTest() {
    Course tempCourse = new Course(
        profName,
        testLocation,
        testTimeSlot,
        17);
    for (int i = 0; i < 15; i++) {
      tempCourse.enrollStudent();
    }

    assertFalse(tempCourse.isCourseFull(), "Course should not be full");

    tempCourse.enrollStudent();
    tempCourse.enrollStudent();
    assertTrue(tempCourse.isCourseFull(), "Course should be full");

    tempCourse.enrollStudent();
    assertTrue(tempCourse.isCourseFull(),
        "Course should remain full after attempting to enroll student");

    tempCourse = new Course(
        profName,
        testLocation,
        testTimeSlot,
        -4);

    assertTrue(tempCourse.isCourseFull(),
        "Course with negative capacity should be full");

    tempCourse = new Course(
        profName,
        testLocation,
        testTimeSlot,
        0);

    assertTrue(tempCourse.isCourseFull(),
        "Course with 0 capacity should be full");
  }

  /**
   * These instances are used for testing.
   */
  public static Course testCourse;
  public static Course testCourse2;
  final String unknown = "Unknown";
  static final String profName = "Griffin Newbold";
  static final String testLocation = "417 IAB";
  static final String testTimeSlot = "11:40-12:55";
}

