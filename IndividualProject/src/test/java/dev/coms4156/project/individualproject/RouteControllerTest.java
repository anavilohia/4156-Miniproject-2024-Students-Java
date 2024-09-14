package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
class RouteControllerTest {
  /**
   * Set up to be run before each test.
   */
  @BeforeEach
  public void setUpRouteControllerForTesting() {
    routeController = new RouteController();

    MyFileDatabase mockFileDatabase = mock(MyFileDatabase.class);
    IndividualProjectApplication.overrideDatabase(mockFileDatabase);

    mockDepartment = mock(Department.class);
    departmentsMap = new HashMap<>();
    when(mockFileDatabase.getDepartmentMapping()).thenReturn(departmentsMap);

    mockCourse = mock(Course.class);
    coursesMap = new HashMap<>();
    when(mockDepartment.getCourseSelection()).thenReturn(coursesMap);
  }

  /**
   * Test for RouteController class retrieveDepartment method.
   */
  @Test
  void retrieveDepartmentTest() {
    departmentsMap.put(comsUppercase, mockDepartment);
    departmentsMap.put(englUppercase, mockDepartment);

    response = routeController.retrieveDepartment(comsUppercase);
    assertEquals(HttpStatus.OK, response.getStatusCode(),
        okTestMessage);
    assertEquals(mockDepartment.toString(), response.getBody(),
        bodyMatchMessage);

    response = routeController.retrieveDepartment(comsLowerrcase);
    assertEquals(HttpStatus.OK, response.getStatusCode(),
        okTestMessage);
    assertEquals(mockDepartment.toString(), response.getBody(),
        bodyMatchMessage);

    response = routeController.retrieveDepartment("eNGL");
    assertEquals(HttpStatus.OK, response.getStatusCode(),
        okTestMessage);
    assertEquals(mockDepartment.toString(), response.getBody(),
        bodyMatchMessage);

    response = routeController.retrieveDepartment("Engl");
    assertEquals(HttpStatus.OK, response.getStatusCode(),
        okTestMessage);
    assertEquals(mockDepartment.toString(), response.getBody(),
        bodyMatchMessage);

    response = routeController.retrieveDepartment("MATH");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
        "Response code should be 404");
    assertEquals(departmentNotFound, response.getBody(),
        bodyMatchMessage);
  }

  /**
   * Test for RouteController class retrieveCourse method.
   */
  @Test
  void retrieveCourseTest() {
    coursesMap.put("3214", mockCourse);
    departmentsMap.put(comsUppercase, mockDepartment);

    response = routeController.retrieveCourse(comsUppercase, 3214);
    assertEquals(HttpStatus.OK, response.getStatusCode(),
        "Response status code should be OK");
    assertEquals(mockCourse.toString(), response.getBody(),
        bodyMatchMessage);

    response = routeController.retrieveCourse(comsLowerrcase, 3212);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
        notFoundTestMessage);
    assertEquals(courseNotFound, response.getBody(),
        bodyMatchMessage);

    response = routeController.retrieveCourse(englUppercase, 3214);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
        notFoundTestMessage);
    assertEquals(departmentNotFound, response.getBody(),
        bodyMatchMessage);
  }

  /**
   * Test for RouteController class isCourseFull method.
   */
  @Test
  void isCourseFullTest() {
    coursesMap.put(courseId, mockCourse);
    departmentsMap.put(comsUppercase, mockDepartment);

    when(mockCourse.isCourseFull()).thenReturn(true);
    response = routeController.isCourseFull(comsUppercase, 1234);
    assertEquals(HttpStatus.OK, response.getStatusCode(),
        okTestMessage);
    assertEquals("true", response.getBody(),
        bodyMatchMessage);

    response = routeController.isCourseFull(comsUppercase, 123);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
        notFoundTestMessage);
    assertEquals(courseNotFound, response.getBody(),
        bodyMatchMessage);

    response = routeController.isCourseFull(englUppercase, 1234);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
        notFoundTestMessage);
    assertEquals(departmentNotFound, response.getBody(),
        bodyMatchMessage);

    when(mockCourse.isCourseFull()).thenReturn(false);
    response = routeController.isCourseFull(comsUppercase, 1234);
    assertEquals(HttpStatus.OK, response.getStatusCode(),
        okTestMessage);
    assertEquals("false", response.getBody(),
        bodyMatchMessage);
  }

  /**
   * Test for RouteController class getMajorCtFromDept method.
   */
  @Test
  void getMajorCtFromDeptTest() {
    departmentsMap.put("PHYS", mockDepartment);
    when(mockDepartment.getNumberOfMajors()).thenReturn(760);

    response = routeController.getMajorCtFromDept("PHYS");
    assertEquals(HttpStatus.OK, response.getStatusCode(),
        okTestMessage);
    assertEquals("There are: 760 majors in the department", response.getBody(),
        bodyMatchMessage);

    response = routeController.getMajorCtFromDept(comsUppercase);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
        notFoundTestMessage);
    assertEquals(departmentNotFound, response.getBody(),
        bodyMatchMessage);
  }

  /**
   * Test for RouteController class identifyDeptChair method.
   */
  @Test
  void identifyDeptChairTest() {
    departmentsMap.put(comsUppercase, mockDepartment);
    when(mockDepartment.getDepartmentChair()).thenReturn("Jae Woo Lee");

    response = routeController.identifyDeptChair(comsUppercase);
    assertEquals(HttpStatus.OK, response.getStatusCode(),
        okTestMessage);
    assertEquals("Jae Woo Lee is the department chair.", response.getBody(),
        bodyMatchMessage);

    verify(mockDepartment).getDepartmentChair();

    response = routeController.identifyDeptChair(englUppercase);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
        notFoundTestMessage);
    assertEquals(departmentNotFound, response.getBody(),
        bodyMatchMessage);

    verify(mockDepartment).getDepartmentChair();
  }

  /**
   * Test for RouteController class findCourseTime method.
   */
  @Test
  void findCourseTimeTest() {
    coursesMap.put(courseId, mockCourse);
    departmentsMap.put(comsUppercase, mockDepartment);

    when(mockCourse.getCourseTimeSlot()).thenReturn("10:00 AM - 11:30 AM");

    response = routeController.findCourseTime(comsUppercase, 1234);
    assertEquals(HttpStatus.OK, response.getStatusCode(),
        okTestMessage);
    assertEquals("The course meets at: 10:00 AM - 11:30 AM", response.getBody(),
        bodyMatchMessage);

    verify(mockCourse).getCourseTimeSlot();

    response = routeController.findCourseTime(comsUppercase, 1111);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
        notFoundTestMessage);
    assertEquals(courseNotFound, response.getBody(),
        bodyMatchMessage);

    verify(mockCourse).getCourseTimeSlot();

  }

  /**
   * Test for RouteController class dropStudent method.
   */
  @Test
  void dropStudentTest() {
    coursesMap.put(courseId, mockCourse);
    departmentsMap.put(comsUppercase, mockDepartment);

    when(mockCourse.dropStudent()).thenReturn(true);
    response = routeController.dropStudent(comsUppercase, 1234);
    assertEquals(HttpStatus.OK, response.getStatusCode(),
        okTestMessage);
    assertEquals("Student has been dropped.", response.getBody(),
        bodyMatchMessage);

    response = routeController.dropStudent(comsUppercase, 123);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
        notFoundTestMessage);
    assertEquals(courseNotFound, response.getBody(),
        bodyMatchMessage);

    response = routeController.dropStudent(englUppercase, 1234);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
        notFoundTestMessage);
    assertEquals(departmentNotFound, response.getBody(),
        bodyMatchMessage);

    when(mockCourse.dropStudent()).thenReturn(false);
    response = routeController.dropStudent(comsUppercase, 1234);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(),
        "Response status code should be 400");
    assertEquals("Student has not been dropped.", response.getBody(),
        bodyMatchMessage);
  }

  /**
   * Test for RouteController class changeCourseTime method.
   */
  @Test
  void changeCourseTimeTest() {
    coursesMap.put(courseId, mockCourse);
    departmentsMap.put(comsUppercase, mockDepartment);

    doNothing().when(mockCourse).reassignTime(anyString());

    response = routeController.changeCourseTime(comsLowerrcase, 1234, "1-5");
    assertEquals(HttpStatus.OK, response.getStatusCode(),
        okTestMessage);
    assertEquals("Attributed was updated successfully.", response.getBody(),
        bodyMatchMessage);

    response = routeController.changeCourseTime(comsLowerrcase, 1111, "1:40");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
        notFoundTestMessage);
    assertEquals(courseNotFound, response.getBody(),
        bodyMatchMessage);

    response = routeController.changeCourseTime(englUppercase, 1234, "2:30-3:30");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
        notFoundTestMessage);
    assertEquals(departmentNotFound, response.getBody(),
        bodyMatchMessage);
  }

  /**
   * Test for RouteController class changeCourseTeacher method.
   */
  @Test
  void changeCourseTeacherTest() {
    coursesMap.put(courseId, mockCourse);
    departmentsMap.put(comsUppercase, mockDepartment);

    doNothing().when(mockCourse).reassignInstructor(anyString());

    response =
        routeController.changeCourseTeacher(comsLowerrcase, 1234, "BLAH");
    assertEquals(HttpStatus.OK, response.getStatusCode(),
        okTestMessage);
    assertEquals("Attributed was updated successfully.", response.getBody(),
        bodyMatchMessage);

    response =
        routeController.changeCourseTeacher(comsLowerrcase, 1111, "pineapple");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
        notFoundTestMessage);
    assertEquals(courseNotFound, response.getBody(),
        bodyMatchMessage);

    response =
        routeController.changeCourseTeacher(englUppercase, 1234, "my teacher");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
        notFoundTestMessage);
    assertEquals(departmentNotFound, response.getBody(),
        bodyMatchMessage);
  }

  /**
   * Test for RouteController class changeCourseLocation method.
   */
  @Test
  void changeCourseLocationTest() {
    coursesMap.put(courseId, mockCourse);
    departmentsMap.put(comsUppercase, mockDepartment);

    doNothing().when(mockCourse).reassignLocation(anyString());

    response = routeController.changeCourseLocation(comsLowerrcase, 1234, "SCH 304");
    assertEquals(HttpStatus.OK, response.getStatusCode(),
        okTestMessage);
    assertEquals("Attributed was updated successfully.", response.getBody(),
        bodyMatchMessage);

    response = routeController.changeCourseLocation(comsLowerrcase, 1111, "SCH 304");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
        notFoundTestMessage);
    assertEquals(courseNotFound, response.getBody(),
        bodyMatchMessage);

    response = routeController.changeCourseLocation(englUppercase, 1234, "SCH 304");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
        notFoundTestMessage);
    assertEquals(departmentNotFound, response.getBody(),
        bodyMatchMessage);
  }

  /**
   * These instances are used for testing.
   */
  private static RouteController routeController;
  private static Map<String, Department> departmentsMap;
  private static Department mockDepartment;
  private static Map<String, Course> coursesMap;
  private static Course mockCourse;
  private ResponseEntity<String> response;
  final String courseId = "1234";
  final String courseNotFound = "Course Not Found";
  final String departmentNotFound = "Department Not Found";
  final String comsUppercase = "COMS";
  final String comsLowerrcase = "coms";
  final String englUppercase = "ENGL";
  final String okTestMessage = "Response code should be OK";
  final String notFoundTestMessage = "Response status code should be 404";
  final String bodyMatchMessage = "Response body should match expected string";
}