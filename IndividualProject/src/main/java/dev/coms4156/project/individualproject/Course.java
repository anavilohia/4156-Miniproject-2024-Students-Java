package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a course within an educational institution.
 * This class stores information about the course, including its instructor's name,
 * location, time slot, and capacity.
 */
public class Course implements Serializable {

  /**
   * Constructs a new Course object with the given parameters. Initial count starts at 0.
   *
   * @param instructorName The name of the instructor teaching the course.
   * @param courseLocation The location where the course is held.
   * @param timeSlot       The time slot of the course.
   * @param capacity       The maximum number of students that can enroll in the course.
   */
  public Course(String instructorName, String courseLocation, String timeSlot, int capacity) {
    this.courseLocation = (courseLocation != null) ? courseLocation : UNKNOWN;
    this.instructorName = (instructorName != null) ? instructorName : UNKNOWN;
    this.courseTimeSlot = (timeSlot != null) ? timeSlot : UNKNOWN;
    this.enrollmentCapacity = Math.max(capacity, 0);
    this.enrolledStudentCount = 0;
  }

  /**
   * Enrolls a student in the course if there is space available.
   *
   * @return true if the student is successfully enrolled, false otherwise.
   */
  public boolean enrollStudent() {
    if (isCourseFull()) {
      return false;
    }
    enrolledStudentCount++;
    return true;
  }

  /**
   * Drops a student from the course if a student is enrolled.
   *
   * @return true if the student is successfully dropped, false otherwise.
   */
  public boolean dropStudent() {
    if (enrolledStudentCount > 0) {
      enrolledStudentCount--;
      return true;
    }
    return false;
  }

  /**
   * Gets the location of the course.
   *
   * @return A {@code String} courseLocation.
   */
  public String getCourseLocation() {
    return this.courseLocation;
  }

  /**
   * Gets the instructor's name for the course.
   *
   * @return A {@code String} instructorName.
   */
  public String getInstructorName() {
    return this.instructorName;
  }

  /**
   * Gets the time slot for the course.
   *
   * @return A {@code String} timeSlot.
   */
  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }

  /**
   * Gets the enrolled student count for the course.
   *
   * @return A {@code int} enrolledStudentCount.
   */
  public int getEnrolledStudentCount() {
    return this.enrolledStudentCount;
  }

  /**
   * Gets the capacity for the course.
   *
   * @return A {@code int} enrollmentCapacity.
   */
  public int getEnrollmentCapacity() {
    return this.enrollmentCapacity;
  }

  /**
   * Forms a string containing course information, including its instructor's name,
   * * location, and time slot.
   *
   * @return A {@code String} of the format
   *     "Instructor: instructorName; Location: courseLocation; Time: courseTimeSlot"
   */
  @Override
  public String toString() {
    return "\nInstructor: "
        + instructorName
        + "; Location: "
        + courseLocation
        + "; Time: "
        + courseTimeSlot;
  }

  /**
   * Reassigns the course instructor.
   *
   * @param newInstructorName the new name to be set for the instructorName
   */
  public void reassignInstructor(String newInstructorName) {
    this.instructorName = (newInstructorName != null) ? newInstructorName : UNKNOWN;
  }

  /**
   * Reassigns the course location.
   *
   * @param newLocation the new location to be set for the courseLocation
   */
  public void reassignLocation(String newLocation) {
    this.courseLocation = (newLocation != null) ? newLocation : UNKNOWN;
  }

  /**
   * Reassigns the course time slot.
   *
   * @param newTime the new time slot to be set for the courseTimeSlot
   */
  public void reassignTime(String newTime) {
    this.courseTimeSlot = (newTime != null) ? newTime : "Unknown";
  }

  /**
   * Sets the course enrollment count if count is a valid value from 0 to capacity.
   *
   * @param count the count to be set for the enrolledStudentCount
   */
  public void setEnrolledStudentCount(int count) {
    if (count > this.enrollmentCapacity) {
      return;
    }
    if (count < 0) {
      return;
    }
    this.enrolledStudentCount = count;
  }

  /**
   * Check's if the course enrollment is full.
   *
   * @return true if enrollment capacity is equal to enrolled student count.
   */
  public boolean isCourseFull() {
    return enrollmentCapacity == enrolledStudentCount;
  }

  // const and variables
  @Serial
  private static final long serialVersionUID = 123456L;
  private static final String UNKNOWN = "Unknown";
  private final int enrollmentCapacity;
  private int enrolledStudentCount;
  private String courseLocation;
  private String instructorName;
  private String courseTimeSlot;
}
