package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;

@SpringBootTest
@ContextConfiguration
class RouteControllerTest {
  /**
   * Set up to be run before each test.
   */
  @BeforeEach
  public void setUpRouteControllerForTesting() {
    routeController = new RouteController();

    mockFileDatabase = mock(MyFileDatabase.class);
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
    departmentsMap.put("COMS", mockDepartment);
    departmentsMap.put("ENGL", mockDepartment);

    response = routeController.retrieveDepartment("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockDepartment.toString(), response.getBody());

    response = routeController.retrieveDepartment("coms");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockDepartment.toString(), response.getBody());

    response = routeController.retrieveDepartment("eNGL");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockDepartment.toString(), response.getBody());

    response = routeController.retrieveDepartment("Engl");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockDepartment.toString(), response.getBody());

    response = routeController.retrieveDepartment("MATH");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  /**
   * Test for RouteController class retrieveCourse method.
   */
  @Test
  void retrieveCourseTest() {
    coursesMap.put("3214", mockCourse);
    departmentsMap.put("COMS", mockDepartment);

    response = routeController.retrieveCourse("COMS", 3214);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockCourse.toString(), response.getBody());

    response = routeController.retrieveCourse("coms", 3212);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());

    response = routeController.retrieveCourse("ENGL", 3214);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  /**
   * Test for RouteController class isCourseFull method.
   */
  @Test
  void isCourseFullTest() {
    coursesMap.put("1234", mockCourse);
    departmentsMap.put("COMS", mockDepartment);

    when(mockCourse.isCourseFull()).thenReturn(true);
    response = routeController.isCourseFull("COMS", 1234);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("true", response.getBody());

    response = routeController.isCourseFull("COMS", 123);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());

    response = routeController.isCourseFull("ENGL", 1234);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());

    when(mockCourse.isCourseFull()).thenReturn(false);
    response = routeController.isCourseFull("COMS", 1234);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("false", response.getBody());
  }

  /**
   * Test for RouteController class getMajorCtFromDept method.
   */
  @Test
  void getMajorCtFromDeptTest() {
    departmentsMap.put("PHYS", mockDepartment);
    when(mockDepartment.getNumberOfMajors()).thenReturn(760);

    response = routeController.getMajorCtFromDept("PHYS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("There are: 760 majors in the department", response.getBody());

    response = routeController.getMajorCtFromDept("COMS");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  /**
   * Test for RouteController class identifyDeptChair method.
   */
  @Test
  void identifyDeptChairTest() {
    departmentsMap.put("COMS", mockDepartment);
    when(mockDepartment.getDepartmentChair()).thenReturn("Jae Woo Lee");

    response = routeController.identifyDeptChair("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Jae Woo Lee is the department chair.", response.getBody());

    verify(mockDepartment).getDepartmentChair();

    response = routeController.identifyDeptChair("ENGL");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());

    verify(mockDepartment).getDepartmentChair();
  }

  /**
   * Test for RouteController class findCourseTime method.
   */
  @Test
  void findCourseTimeTest() {
    coursesMap.put("1234", mockCourse);
    departmentsMap.put("COMS", mockDepartment);

    when(mockCourse.getCourseTimeSlot()).thenReturn("10:00 AM - 11:30 AM");

    response = routeController.findCourseTime("COMS", 1234);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("The course meets at: 10:00 AM - 11:30 AM", response.getBody());

    verify(mockCourse).getCourseTimeSlot();

    response = routeController.findCourseTime("COMS", 1111);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());

    verify(mockCourse).getCourseTimeSlot();

  }

  /**
   * Test for RouteController class dropStudent method.
   */
  @Test
  void dropStudentTest() {
    coursesMap.put("1234", mockCourse);
    departmentsMap.put("COMS", mockDepartment);

    when(mockCourse.dropStudent()).thenReturn(true);
    response = routeController.dropStudent("COMS", 1234);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Student has been dropped.", response.getBody());

    response = routeController.dropStudent("COMS", 123);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());

    response = routeController.dropStudent("ENGL", 1234);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());

    when(mockCourse.dropStudent()).thenReturn(false);
    response = routeController.dropStudent("COMS", 1234);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Student has not been dropped.", response.getBody());
  }

  /**
   * Test for RouteController class changeCourseTime method.
   */
  @Test
  void changeCourseTimeTest() {
    coursesMap.put("1234", mockCourse);
    departmentsMap.put("COMS", mockDepartment);

    doNothing().when(mockCourse).reassignTime(anyString());

    response = routeController.changeCourseTime("coms", 1234, "1-5");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());

    response = routeController.changeCourseTime("coms", 1111, "1:40");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());

    response = routeController.changeCourseTime("ENGL", 1234, "2:30-3:30");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  /**
   * Test for RouteController class changeCourseTeacher method.
   */
  @Test
  void changeCourseTeacherTest() {
    coursesMap.put("1234", mockCourse);
    departmentsMap.put("COMS", mockDepartment);

    doNothing().when(mockCourse).reassignInstructor(anyString());

    response = routeController.changeCourseTeacher("coms", 1234, "BLAH");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());

    response = routeController.changeCourseTeacher("coms", 1111, "pineapple");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());

    response = routeController.changeCourseTeacher("ENGL", 1234, "my teacher");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  /**
   * Test for RouteController class changeCourseLocation method.
   */
  @Test
  void changeCourseLocationTest() {
    coursesMap.put("1234", mockCourse);
    departmentsMap.put("COMS", mockDepartment);

    doNothing().when(mockCourse).reassignLocation(anyString());

    response = routeController.changeCourseLocation("coms", 1234, "SCH 304");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());

    response = routeController.changeCourseLocation("coms", 1111, "SCH 304");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());

    response = routeController.changeCourseLocation("ENGL", 1234, "SCH 304");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  /**
   * These instances are used for testing.
   */
  private static RouteController routeController;
  private static MyFileDatabase mockFileDatabase;
  private static HashMap<String, Department> departmentsMap;
  private static Department mockDepartment;
  private static HashMap<String, Course> coursesMap;
  private static Course mockCourse;
  private ResponseEntity<String> response;
}