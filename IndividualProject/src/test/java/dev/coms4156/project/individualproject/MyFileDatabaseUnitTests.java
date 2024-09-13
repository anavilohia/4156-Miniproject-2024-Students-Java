package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
  static void setupDataForTesting() {
    testCourse1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourse1.setEnrolledStudentCount(249);
    testCourse2 = new Course("Prof. K", "Mudd 343", "1 to 4 pm", 34);
    testCourse2.setEnrolledStudentCount(38);
    coursesMap = new HashMap<>();
    coursesMap.put("1004", testCourse1);
    coursesMap.put("3134", testCourse2);

    comsDept = new Department("COMS", coursesMap, "Luca Carloni", 2700);
    biolDept = new Department("BIOL", coursesMap, null, 22);
    expectedDepartmentsMap = new HashMap<>();
    expectedDepartmentsMap.put("COMS", comsDept);
    expectedDepartmentsMap.put("BIOL", biolDept);
  }

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
    assert(myFileDatabase.getDepartmentMapping().isEmpty());
  }

  /**
   * Test for MyFileDatabase class deSerializeObjectFromFile method.
   */
  @Test
  void deSerializeObjectFromFileTest() {
    actualDepartmentsMap =  myFileDatabase.deSerializeObjectFromFile();
    assertNotNull(actualDepartmentsMap);
    assertFalse(actualDepartmentsMap.isEmpty());
    assertTrue(actualDepartmentsMap.containsKey("COMS"));

    Set<String> expectedKeySet = new HashSet<>(Arrays.asList("COMS", "ECON", "IEOR", "CHEM", "PHYS", "ELEN", "PSYC"));
    Set<String> actualKeySet = actualDepartmentsMap.keySet();
    assertEquals(expectedKeySet, actualKeySet);

    String expectedValue = "Trenton Jerde";
    String actualValue = actualDepartmentsMap.get("PSYC").getCourseSelection().get("4236").getInstructorName();
    assertEquals(expectedValue, actualValue);

    int expectedCount = 0;
    int actualCount = actualDepartmentsMap.get("CHEM").getCourseSelection().get("1500").getEnrolledStudentCount();
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
    assert(actualDepartmentsMap.isEmpty());

    testDatabase.setMapping(expectedDepartmentsMap);
    testDatabase.saveContentsToFile();
    actualDepartmentsMap =  testDatabase.deSerializeObjectFromFile();
    assertNotNull(actualDepartmentsMap);
    assert(!actualDepartmentsMap.isEmpty());

    assert(actualDepartmentsMap.containsKey("BIOL"));

    try {
      Files.delete(Paths.get("./testDataFile.txt"));
    } catch (IOException e) {
      e.printStackTrace();
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
    assertEquals("For the BIOL department: \n"
        + "BIOL 1004: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n"
        + "BIOL 3134: \nInstructor: Prof. K; Location: Mudd 343; Time: 1 to 4 pm\n"
        + "For the COMS department: \n"
        + "COMS 1004: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n"
        + "COMS 3134: \nInstructor: Prof. K; Location: Mudd 343; Time: 1 to 4 pm\n"
        , myFileDatabase.toString());

    myFileDatabase.setMapping(null);
    assertEquals("", myFileDatabase.toString());
  }

  /**
   * This myFileDatabase instance is used for testing.
   */
  public static MyFileDatabase myFileDatabase;

  /**
   * These test course and department instances are used for testing.
   */
  public static Course testCourse1;
  public static Course testCourse2;
  public static Department comsDept;
  public static Department biolDept;
  public static HashMap<String, Course> coursesMap;
  public static HashMap<String, Department> expectedDepartmentsMap;
  public static HashMap<String, Department> actualDepartmentsMap;
}