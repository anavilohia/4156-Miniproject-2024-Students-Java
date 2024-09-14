package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests to be used for MyFileDatabase class.
 */
@SpringBootTest
@ContextConfiguration
class MyFileDatabaseUnitTests {

  /**
   * Set up to be run before all tests.
   */
  @BeforeAll
  public static void setupDataForTesting() {
    testCourse1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourse1.setEnrolledStudentCount(249);
    testCourse2 = new Course("Prof. K", "Mudd 343", "1 to 4 pm", 34);
    testCourse2.setEnrolledStudentCount(38);
    coursesMap = new HashMap<>();
    coursesMap.put("1004", testCourse1);
    coursesMap.put("3134", testCourse2);

    comsDept = new Department(DEPARTMENT_CODES[1], coursesMap, "Luca Carloni", 2700);
    econDept = new Department(DEPARTMENT_CODES[2], coursesMap, null, 22);
    expectedDepartmentsMap = new HashMap<>();
    expectedDepartmentsMap.put(DEPARTMENT_CODES[1], comsDept);
    expectedDepartmentsMap.put(DEPARTMENT_CODES[2], econDept);
  }

  /**
   * Set up to be run before each test.
   */
  @BeforeEach
  void setupMyFileDatabaseForTesting() {
    myFileDatabase = new MyFileDatabase(1, "./data.txt");
  }

  /**
   * Test for MyFileDatabase class setMapping method.
   */
  @Test
  void setMappingTest() {
    assertNotEquals(expectedDepartmentsMap, myFileDatabase.getDepartmentMapping());

    myFileDatabase.setMapping(expectedDepartmentsMap);
    assertEquals(expectedDepartmentsMap, myFileDatabase.getDepartmentMapping());

    myFileDatabase.setMapping(null);
    assertNotNull(myFileDatabase.getDepartmentMapping());
    assert (myFileDatabase.getDepartmentMapping().isEmpty());
  }

  /**
   * Test for MyFileDatabase class deSerializeObjectFromFile method.
   */
  @Test
  void deSerializeObjectFromFileTest() {
    actualDepartmentsMap = myFileDatabase.deSerializeObjectFromFile();
    assertNotNull(actualDepartmentsMap);
    assertFalse(actualDepartmentsMap.isEmpty());
    assertTrue(actualDepartmentsMap.containsKey(DEPARTMENT_CODES[1]));

    Set<String> expectedKeySet =
        new HashSet<>(Arrays.asList(DEPARTMENT_CODES));
    Set<String> actualKeySet = actualDepartmentsMap.keySet();
    assertEquals(expectedKeySet, actualKeySet);

    String expectedValue = "Trenton Jerde";
    String actualValue =
        actualDepartmentsMap.get("PSYC").getCourseSelection().get("4236").getInstructorName();
    assertEquals(expectedValue, actualValue);

    int expectedCount = 0;
    int actualCount =
        actualDepartmentsMap.get("CHEM").getCourseSelection().get("1500").getEnrolledStudentCount();
    assertEquals(expectedCount, actualCount);
  }

  /**
   * Test for MyFileDatabase class saveContentsToFile method.
   */
  @Test
  void saveContentsToFileTest() {
    MyFileDatabase testDatabase = new MyFileDatabase(1, "./testDataFile.txt");

    testDatabase.saveContentsToFile();
    actualDepartmentsMap = testDatabase.deSerializeObjectFromFile();
    assertNotNull(actualDepartmentsMap);
    assert (actualDepartmentsMap.isEmpty());

    testDatabase.setMapping(expectedDepartmentsMap);
    testDatabase.saveContentsToFile();
    actualDepartmentsMap = testDatabase.deSerializeObjectFromFile();
    assertNotNull(actualDepartmentsMap);
    assert (!actualDepartmentsMap.isEmpty());

    assert (actualDepartmentsMap.containsKey(DEPARTMENT_CODES[2]));

    try {
      Files.delete(Paths.get("./testDataFile.txt"));
    } catch (IOException e) {
      fail("Failed to delete the test file");
    }
  }

  /**
   * Test for MyFileDatabase class getDepartmentMapping method.
   */
  @Test
  void getDepartmentMappingTest() {
    myFileDatabase.setMapping(expectedDepartmentsMap);
    actualDepartmentsMap = myFileDatabase.getDepartmentMapping();
    assertEquals(expectedDepartmentsMap, actualDepartmentsMap);

    MyFileDatabase testDatabase = new MyFileDatabase(1, "./testDataFile.txt");
    actualDepartmentsMap = testDatabase.getDepartmentMapping();
    assertNotNull(actualDepartmentsMap);
    assertTrue(actualDepartmentsMap.isEmpty());
  }

  /**
   * Test for MyFileDatabase class toString method.
   */
  @Test
  void toStringTest() {
    assertEquals("", myFileDatabase.toString());

    myFileDatabase.setMapping(expectedDepartmentsMap);
    assertEquals("""
           For the COMS department:\s
           COMS 1004:\s
           Instructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55
           COMS 3134:\s
           Instructor: Prof. K; Location: Mudd 343; Time: 1 to 4 pm
           For the ECON department:\s
           ECON 1004:\s
           Instructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55
           ECON 3134:\s
           Instructor: Prof. K; Location: Mudd 343; Time: 1 to 4 pm
           """,
        myFileDatabase.toString());

    myFileDatabase.setMapping(null);
    assertEquals("", myFileDatabase.toString());
  }

  /**
   * This myFileDatabase instance is used for testing.
   */
  public static MyFileDatabase myFileDatabase;

  /**
   * These instances are used for testing.
   */
  public static Course testCourse1;
  public static Course testCourse2;
  public static Department comsDept;
  public static Department econDept;
  public static HashMap<String, Course> coursesMap;
  public static HashMap<String, Department> expectedDepartmentsMap;
  public static HashMap<String, Department> actualDepartmentsMap;
  static final String[] DEPARTMENT_CODES = {"CHEM", "COMS", "ECON", "ELEN", "IEOR", "PHYS", "PSYC"};
}