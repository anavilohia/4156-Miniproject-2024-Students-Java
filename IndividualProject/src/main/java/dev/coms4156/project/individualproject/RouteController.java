package dev.coms4156.project.individualproject;

import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains all the API routes for the system.
 */
@RestController
public class RouteController {

  /**
   * Redirects to the homepage.
   *
   * @return A String containing the name of the html file to be loaded.
   */
  @GetMapping({"/", "/index", "/home"})
  @ResponseBody
  public String index() {
    return """
        Welcome, in order to make an API call direct your browser or Postman to an endpoint
        
        This can be done using the following format:
        
        http://127.0.0.1:8080/endpoint?arg=value
        """;
  }

  /**
   * Returns the details of the specified department.
   *
   * @param deptCode A {@code String} representing the department the user wishes
   *                 to retrieve.
   * @return A {@code ResponseEntity} object containing either the details of the Department and
   *     an HTTP 200 response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/retrieveDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> retrieveDepartment(
      @RequestParam(value = deptCodeLiteral) String deptCode) {
    try {
      Map<String, Department> departmentMapping;
      departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

      if (departmentMapping.containsKey(deptCode.toUpperCase(Locale.US))) {
        return new ResponseEntity<>(
            departmentMapping.get(deptCode.toUpperCase(Locale.US)).toString(),
            HttpStatus.OK);
      }
      return new ResponseEntity<>(
          departmentNotFoundLiteral,
          HttpStatus.NOT_FOUND);

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the details of the requested course to the user or displays the proper error
   * message in response to the request.
   *
   * @param deptCode   A {@code String} representing the department the user wishes
   *                   to find the course in.
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to retrieve.
   * @return A {@code ResponseEntity} object containing either the details of the
   *     course and an HTTP 200 response or, an appropriate message indicating the
   *     proper response.
   */
  @GetMapping(value = "/retrieveCourse", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> retrieveCourse(
      @RequestParam(value = deptCodeLiteral) String deptCode,
      @RequestParam(value = courseCodeLiteral) int courseCode) {
    try {
      boolean doesDepartmentExists =
          retrieveDepartment(deptCode.toUpperCase(Locale.US)).getStatusCode() == HttpStatus.OK;

      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping =
            departmentMapping.get(deptCode.toUpperCase(Locale.US)).getCourseSelection();

        if (coursesMapping.containsKey(Integer.toString(courseCode))) {
          return new ResponseEntity<>(
              coursesMapping.get(Integer.toString(courseCode)).toString(),
              HttpStatus.OK);
        }
        return new ResponseEntity<>(
            "Course Not Found",
            HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(
          departmentNotFoundLiteral,
          HttpStatus.NOT_FOUND);

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays whether the course has at minimum reached its enrollmentCapacity.
   *
   * @param deptCode   A {@code String} representing the department the user wishes
   *                   to find the course in.
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to retrieve.
   * @return A {@code ResponseEntity} object containing either the requested information
   *     and an HTTP 200 response or, an appropriate message indicating the proper
   *     response.
   */
  @GetMapping(value = "/isCourseFull", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> isCourseFull(
      @RequestParam(value = deptCodeLiteral) String deptCode,
      @RequestParam(value = courseCodeLiteral) int courseCode) {
    try {
      ResponseEntity<String> retrieveCourseResponse
          = retrieveCourse(deptCode.toUpperCase(Locale.US), courseCode);
      boolean doesCourseExists = retrieveCourseResponse.getStatusCode() == HttpStatus.OK;
      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping =
            departmentMapping.get(deptCode.toUpperCase(Locale.US)).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>(
            String.valueOf(requestedCourse.isCourseFull()),
            HttpStatus.OK);
      }
      return new ResponseEntity<>(
          retrieveCourseResponse.getBody(),
          retrieveCourseResponse.getStatusCode());
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the number of majors in the specified department.
   *
   * @param deptCode A {@code String} representing the department the user wishes
   *                 to find number of majors for.
   * @return A {@code ResponseEntity} object containing either number of majors for the
   *     specified department and an HTTP 200 response or, an appropriate message
   *     indicating the proper response.
   */
  @GetMapping(value = "/getMajorCountFromDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> getMajorCtFromDept(
      @RequestParam(value = deptCodeLiteral) String deptCode) {
    try {
      boolean doesDepartmentExists =
          retrieveDepartment(deptCode.toUpperCase(Locale.US)).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        return new ResponseEntity<>(
            "There are: "
                + departmentMapping.get(deptCode.toUpperCase(Locale.US)).getNumberOfMajors()
                + " majors in the department", HttpStatus.OK);
      }
      return new ResponseEntity<>(
          departmentNotFoundLiteral,
          HttpStatus.NOT_FOUND);

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the department chair for the specified department.
   *
   * @param deptCode A {@code String} representing the department the user wishes
   *                 to find the department chair of.
   * @return A {@code ResponseEntity} object containing either department chair of the
   *     specified department and an HTTP 200 response or, an appropriate message
   *     indicating the proper response.
   */
  @GetMapping(value = "/idDeptChair", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> identifyDeptChair(
      @RequestParam(value = deptCodeLiteral) String deptCode) {
    try {
      boolean doesDepartmentExists =
          retrieveDepartment(deptCode.toUpperCase(Locale.US)).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        return new ResponseEntity<>(
            departmentMapping.get(deptCode.toUpperCase(Locale.US)).getDepartmentChair()
                + " is "
                + "the department chair.",
            HttpStatus.OK);
      }
      return new ResponseEntity<>(
          departmentNotFoundLiteral,
          HttpStatus.NOT_FOUND);

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the location for the specified course.
   *
   * @param deptCode   A {@code String} representing the department the user wishes
   *                   to find the course in.
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to find information about.
   * @return A {@code ResponseEntity} object containing either the location of the
   *     course and an HTTP 200 response or, an appropriate message indicating the
   *     proper response.
   */
  @GetMapping(value = "/findCourseLocation", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> findCourseLocation(
      @RequestParam(value = deptCodeLiteral) String deptCode,
      @RequestParam(value = courseCodeLiteral) int courseCode) {
    try {
      ResponseEntity<String> retrieveCourseResponse =
          retrieveCourse(deptCode.toUpperCase(Locale.US), courseCode);
      boolean doesCourseExists = retrieveCourseResponse.getStatusCode() == HttpStatus.OK;
      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping =
            departmentMapping.get(deptCode.toUpperCase(Locale.US)).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>(
            requestedCourse.getCourseLocation()
                + " is where the course "
                + "is located.", HttpStatus.OK);
      }
      return new ResponseEntity<>(
          retrieveCourseResponse.getBody(),
          retrieveCourseResponse.getStatusCode());

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the instructor for the specified course.
   *
   * @param deptCode   A {@code String} representing the department the user wishes
   *                   to find the course in.
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to find information about.
   * @return A {@code ResponseEntity} object containing either the course instructor and
   *     an HTTP 200 response or, an appropriate message indicating the proper
   *     response.
   */
  @GetMapping(value = "/findCourseInstructor", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> findCourseInstructor(
      @RequestParam(value = deptCodeLiteral) String deptCode,
      @RequestParam(value = courseCodeLiteral) int courseCode) {
    try {
      ResponseEntity<String> retrieveCourseResponse =
          retrieveCourse(deptCode.toUpperCase(Locale.US), courseCode);
      boolean doesCourseExists = retrieveCourseResponse.getStatusCode() == HttpStatus.OK;
      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping =
            departmentMapping.get(deptCode.toUpperCase(Locale.US)).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>(
            requestedCourse.getInstructorName()
                + " is the instructor for"
                + " the course.", HttpStatus.OK);
      }
      return new ResponseEntity<>(
          retrieveCourseResponse.getBody(),
          retrieveCourseResponse.getStatusCode());

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the time the course meets at for the specified course.
   *
   * @param deptCode   A {@code String} representing the department the user wishes
   *                   to find the course in.
   * @param courseCode A {@code int} representing the course the user wishes
   *                   to find information about.
   * @return A {@code ResponseEntity} object containing either the details of the
   *     course timeslot and an HTTP 200 response or, an appropriate message
   *     indicating the proper response.
   */
  @GetMapping(value = "/findCourseTime", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> findCourseTime(
      @RequestParam(value = deptCodeLiteral) String deptCode,
      @RequestParam(value = courseCodeLiteral) int courseCode) {
    try {
      ResponseEntity<String> retrieveCourseResponse =
          retrieveCourse(deptCode.toUpperCase(Locale.US), courseCode);
      boolean doesCourseExists = retrieveCourseResponse.getStatusCode() == HttpStatus.OK;
      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping =
            departmentMapping.get(deptCode.toUpperCase(Locale.US)).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        return new ResponseEntity<>(
            "The course meets at: "
                + requestedCourse.getCourseTimeSlot(),
            HttpStatus.OK);
      }
      return new ResponseEntity<>(
          retrieveCourseResponse.getBody(),
          retrieveCourseResponse.getStatusCode());

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Attempts to add a student to the specified department.
   *
   * @param deptCode A {@code String} representing the department.
   * @return A {@code ResponseEntity} object containing an HTTP 200
   *     response with an appropriate message or the proper status
   *     code in tune with what has happened.
   */
  @PatchMapping(value = "/addMajorToDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> addMajorToDept(
      @RequestParam(value = deptCodeLiteral) String deptCode) {
    try {
      boolean doesDepartmentExists =
          retrieveDepartment(deptCode.toUpperCase(Locale.US)).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

        Department specifiedDept = departmentMapping.get(deptCode.toUpperCase(Locale.US));
        specifiedDept.addPersonToMajor();
        return new ResponseEntity<>(
            "Attribute was updated or is at maximum",
            HttpStatus.OK);
      }
      return new ResponseEntity<>(
          departmentNotFoundLiteral,
          HttpStatus.NOT_FOUND);

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Attempts to remove a student from the specified department.
   *
   * @param deptCode A {@code String} representing the department.
   * @return A {@code ResponseEntity} object containing an HTTP 200
   *     response with an appropriate message or the proper status
   *     code in tune with what has happened.
   */
  @PatchMapping(value = "/removeMajorFromDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> removeMajorFromDept(
      @RequestParam(value = deptCodeLiteral) String deptCode) {
    try {
      boolean doesDepartmentExists =
          retrieveDepartment(deptCode.toUpperCase(Locale.US)).getStatusCode() == HttpStatus.OK;
      if (doesDepartmentExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

        Department specifiedDept = departmentMapping.get(deptCode.toUpperCase(Locale.US));
        specifiedDept.dropPersonFromMajor();
        return new ResponseEntity<>(
            "Attribute was updated or is at minimum",
            HttpStatus.OK);
      }
      return new ResponseEntity<>(
          departmentNotFoundLiteral,
          HttpStatus.NOT_FOUND);

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Attempts to drop a student from the specified course.
   *
   * @param deptCode   A {@code String} representing the department.
   * @param courseCode A {@code int} representing the course within the department.
   * @return A {@code ResponseEntity} object containing an HTTP 200
   *     response with an appropriate message or the proper status
   *     code in tune with what has happened.
   */
  @PatchMapping(value = "/dropStudentFromCourse", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> dropStudent(
      @RequestParam(value = deptCodeLiteral) String deptCode,
      @RequestParam(value = courseCodeLiteral) int courseCode) {
    try {
      ResponseEntity<String> retrieveCourseResponse =
          retrieveCourse(deptCode.toUpperCase(Locale.US), courseCode);
      boolean doesCourseExists = retrieveCourseResponse.getStatusCode() == HttpStatus.OK;
      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping =
            departmentMapping.get(deptCode.toUpperCase(Locale.US)).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        boolean isStudentDropped = requestedCourse.dropStudent();

        if (isStudentDropped) {
          return new ResponseEntity<>(
              "Student has been dropped.",
              HttpStatus.OK);
        }
        return new ResponseEntity<>(
            "Student has not been dropped.",
            HttpStatus.BAD_REQUEST);
      }
      return new ResponseEntity<>(
          retrieveCourseResponse.getBody(),
          retrieveCourseResponse.getStatusCode());

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Attempts to set enrollment count for the specified course.
   *
   * @param deptCode   A {@code String} representing the department.
   * @param courseCode A {@code int} representing the course within the department.
   * @param count      A {@code int} representing the enrollment count to be set.
   * @return a ResponseEntity with a success message if the operation is
   *     successful, or an error message if the course is not found
   */
  @PatchMapping(value = "/setEnrollmentCount", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> setEnrollmentCount(
      @RequestParam(value = deptCodeLiteral) String deptCode,
      @RequestParam(value = courseCodeLiteral) int courseCode,
      @RequestParam(value = "count") int count) {
    try {
      ResponseEntity<String> retrieveCourseResponse =
          retrieveCourse(deptCode.toUpperCase(Locale.US), courseCode);
      boolean doesCourseExists = retrieveCourseResponse.getStatusCode() == HttpStatus.OK;
      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping =
            departmentMapping.get(deptCode.toUpperCase(Locale.US)).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        if (requestedCourse.getEnrollmentCapacity() > count) {
          return new ResponseEntity<>(
              "Attributed was not updated.",
              HttpStatus.BAD_REQUEST);
        }
        requestedCourse.setEnrolledStudentCount(count);
        return new ResponseEntity<>(
           succesfulUpdate,
            HttpStatus.OK);
      }
      return new ResponseEntity<>(
          retrieveCourseResponse.getBody(),
          retrieveCourseResponse.getStatusCode());

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Endpoint for changing the time of a course.
   * This method handles PATCH requests to change the time of a course identified by
   * department code and course code.If the course exists, its time is updated to the provided time.
   *
   * @param deptCode   the code of the department containing the course
   * @param courseCode the code of the course to change the time for
   * @param time       the new time for the course
   * @return a ResponseEntity with a success message if the operation is
   *     successful, or an error message if the course is not found
   */
  @PatchMapping(value = "/changeCourseTime", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> changeCourseTime(
      @RequestParam(value = deptCodeLiteral) String deptCode,
      @RequestParam(value = courseCodeLiteral) int courseCode,
      @RequestParam(value = "time") String time) {
    try {
      ResponseEntity<String> retrieveCourseResponse =
          retrieveCourse(deptCode.toUpperCase(Locale.US), courseCode);
      boolean doesCourseExists = retrieveCourseResponse.getStatusCode() == HttpStatus.OK;
      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping =
            departmentMapping.get(deptCode.toUpperCase(Locale.US)).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        requestedCourse.reassignTime(time);
        return new ResponseEntity<>(
           succesfulUpdate,
            HttpStatus.OK);
      }
      return new ResponseEntity<>(
          retrieveCourseResponse.getBody(),
          retrieveCourseResponse.getStatusCode());

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Endpoint for changing the instructor of a course.
   * This method handles PATCH requests to change the instructor of a course identified by
   * department code and course code. If the course exists, its instructor is updated to the
   * provided instructor.
   *
   * @param deptCode   the code of the department containing the course
   * @param courseCode the code of the course to change the instructor for
   * @param teacher    the new instructor for the course
   * @return a ResponseEntity with a success message if the operation is
   *     successful, or an error message if the course is not found
   */
  @PatchMapping(value = "/changeCourseTeacher", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> changeCourseTeacher(
      @RequestParam(value = deptCodeLiteral) String deptCode,
      @RequestParam(value = courseCodeLiteral) int courseCode,
      @RequestParam(value = "teacher") String teacher) {
    try {
      ResponseEntity<String> retrieveCourseResponse =
          retrieveCourse(deptCode.toUpperCase(Locale.US), courseCode);
      boolean doesCourseExists = retrieveCourseResponse.getStatusCode() == HttpStatus.OK;
      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping =
            departmentMapping.get(deptCode.toUpperCase(Locale.US)).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        requestedCourse.reassignInstructor(teacher);
        return new ResponseEntity<>(
           succesfulUpdate,
            HttpStatus.OK);
      }
      return new ResponseEntity<>(
          retrieveCourseResponse.getBody(),
          retrieveCourseResponse.getStatusCode());

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Endpoint for changing the location of a course.
   * This method handles PATCH requests to change the location of a course identified by
   * department code and course code.If the course exists, its location is updated to the
   * provided location.
   *
   * @param deptCode   the code of the department containing the course
   * @param courseCode the code of the course to change the time for
   * @param location   the new location for the course
   * @return a ResponseEntity with a success message if the operation is
   *     successful, or an error message if the course is not found
   */
  @PatchMapping(value = "/changeCourseLocation", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> changeCourseLocation(
      @RequestParam(value = deptCodeLiteral) String deptCode,
      @RequestParam(value = courseCodeLiteral) int courseCode,
      @RequestParam(value = "location") String location) {
    try {
      ResponseEntity<String> retrieveCourseResponse =
          retrieveCourse(deptCode.toUpperCase(Locale.US), courseCode);
      boolean doesCourseExists = retrieveCourseResponse.getStatusCode() == HttpStatus.OK;
      if (doesCourseExists) {
        Map<String, Department> departmentMapping;
        departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
        Map<String, Course> coursesMapping;
        coursesMapping =
            departmentMapping.get(deptCode.toUpperCase(Locale.US)).getCourseSelection();

        Course requestedCourse = coursesMapping.get(Integer.toString(courseCode));
        requestedCourse.reassignLocation(location);
        return new ResponseEntity<>(
           succesfulUpdate,
            HttpStatus.OK);
      }
      return new ResponseEntity<>(
          retrieveCourseResponse.getBody(),
          retrieveCourseResponse.getStatusCode());

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Handles exceptions and prints the error.
   *
   * @param e the exception to be handles and printed as string
   * @return a ResponseEntity with a success message
   */
  private ResponseEntity<String> handleException(Exception e) {
    if (LOGGER.isLoggable(Level.SEVERE)) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }
    return new ResponseEntity<>("An Error has occurred", HttpStatus.OK);
  }

  /**
   * String literals.
   */
  final String deptCodeLiteral = "deptCode";
  final String courseCodeLiteral = "courseCode";
  final String departmentNotFoundLiteral = "Department Not Found";
  final String succesfulUpdate = "Attributed was updated successfully.";

  /**
   * Logger to print information and exceptions.
   */
  private static final Logger LOGGER = Logger.getLogger(MyFileDatabase.class.getName());
}