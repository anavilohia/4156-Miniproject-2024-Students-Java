package dev.coms4156.project.individualproject;

import jakarta.annotation.PreDestroy;
import java.util.HashMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class contains all the startup logic for the application.
 * <p>
 * DO NOT MODIFY ANYTHING BELOW THIS POINT WITH REGARD TO FUNCTIONALITY
 * YOU MAY MAKE STYLE/REFACTOR MODIFICATIONS AS NEEDED
 * </p>
 */
@SpringBootApplication
public class IndividualProjectApplication implements CommandLineRunner {

  /**
   * The main launcher for the service all it does
   * is make a call to the overridden run method.
   *
   * @param args A {@code String[]} of any potential
   *             runtime arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(IndividualProjectApplication.class, args);
  }

  /**
   * This contains all the setup logic, it will mainly be focused
   * on loading up and creating an instance of the database based
   * off a saved file or will create a fresh database if the file
   * is not present.
   *
   * @param args A {@code String[]} of any potential runtime args
   */
  public void run(String[] args) {
    for (String arg : args) {
      if (arg.equals("setup")) {
        myFileDatabase = new MyFileDatabase(1, "./data.txt");
        resetDataFile();
        System.out.println("System Setup");
        return;
      }
    }
    myFileDatabase = new MyFileDatabase(0, "./data.txt");
    System.out.println("Start up");
  }

  /**
   * Overrides the database reference, used when testing.
   *
   * @param testData A {@code MyFileDatabase} object referencing test data.
   */
  public static void overrideDatabase(MyFileDatabase testData) {
    myFileDatabase = testData;
    saveData = false;
  }

  /**
   * Allows for data to be reset in event of errors.
   */
  public void resetDataFile() {
    final String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    final String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    // Data for CHEM department courses.
    HashMap<String, Course> chemCourses = new HashMap<>();

    Course chem1403 = new Course("Ruben M Savizky", locations[1], "6:10-7:25", 120);
    chem1403.setEnrolledStudentCount(100);
    chemCourses.put("1403", chem1403);

    Course chem1500 = new Course("Joseph C Ulichny", "302 HAV", "6:10-9:50", 46);
    chem1500.setEnrolledStudentCount(50);
    chemCourses.put("1500", chem1500);

    Course chem2045 = new Course("Luis M Campos", "209 HAV", "1:10-2:25", 50);
    chem2045.setEnrolledStudentCount(29);
    chemCourses.put("2045", chem2045);

    Course chem2444 = new Course("Christopher Eckdahl", locations[1], times[0], 150);
    chem2444.setEnrolledStudentCount(150);
    chemCourses.put("2444", chem2444);

    Course chem2494 = new Course("Talha Siddiqui", "202 HAV", "1:10-5:00", 24);
    chem2494.setEnrolledStudentCount(18);
    chemCourses.put("2494", chem2494);

    Course chem3080 = new Course("Milan Delor", "209 HAV", times[2], 60);
    chem3080.setEnrolledStudentCount(18);
    chemCourses.put("3080", chem3080);

    Course chem4071 = new Course("Jonathan S Owen", "320 HAV", "8:40-9:55", 42);
    chem4071.setEnrolledStudentCount(29);
    chemCourses.put("4071", chem4071);

    Course chem4102 = new Course("Dalibor Sames", "320 HAV", times[2], 28);
    chem4102.setEnrolledStudentCount(27);
    chemCourses.put("4102", chem4102);

    // Data for COMS department courses.
    HashMap<String, Course> comsCourses = new HashMap<>();

    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);
    comsCourses.put("1004", coms1004);

    Course coms3134 = new Course("Brian Borowski", locations[2], times[1], 250);
    coms3134.setEnrolledStudentCount(242);
    comsCourses.put("3134", coms3134);

    Course coms3157 = new Course("Jae Lee", locations[0], times[1], 400);
    coms3157.setEnrolledStudentCount(311);
    comsCourses.put("3157", coms3157);

    Course coms3203 = new Course("Ansaf Salleb-Aouissi", locations[2], times[2], 250);
    coms3203.setEnrolledStudentCount(215);
    comsCourses.put("3203", coms3203);

    Course coms3251 = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
    coms3251.setEnrolledStudentCount(99);
    comsCourses.put("3251", coms3251);

    Course coms3261 = new Course("Josh Alman", locations[0], times[3], 150);
    coms3261.setEnrolledStudentCount(140);
    comsCourses.put("3261", coms3261);

    Course coms3827 = new Course("Daniel Rubenstein", "207 Math", times[2], 300);
    coms3827.setEnrolledStudentCount(283);
    comsCourses.put("3827", coms3827);

    Course coms4156 = new Course("Gail Kaiser", "501 NWC", times[2], 120);
    coms4156.setEnrolledStudentCount(109);
    comsCourses.put("4156", coms4156);

    // Data for ECON department courses.
    HashMap<String, Course> econCourses = new HashMap<>();

    Course econ1105 = new Course("Waseem Noor", locations[1], times[3], 210);
    econ1105.setEnrolledStudentCount(187);
    econCourses.put("1105", econ1105);

    Course econ2257 = new Course("Tamrat Gashaw", "428 PUP", times[2], 125);
    econ2257.setEnrolledStudentCount(63);
    econCourses.put("2257", econ2257);

    Course econ3211 = new Course("Murat Yilmaz", "310 FAY", times[1], 96);
    econ3211.setEnrolledStudentCount(81);
    econCourses.put("3211", econ3211);

    Course econ3213 = new Course("Miles Leahey", "702 HAM", times[1], 86);
    econ3213.setEnrolledStudentCount(77);
    econCourses.put("3213", econ3213);

    Course econ3412 = new Course("Thomas Piskula", "702 HAM", times[0], 86);
    econ3412.setEnrolledStudentCount(81);
    econCourses.put("3412", econ3412);

    Course econ4415 = new Course("Evan D Sadler", locations[1], times[2], 110);
    econ4415.setEnrolledStudentCount(63);
    econCourses.put("4415", econ4415);

    Course econ4710 = new Course("Matthieu Gomez", "517 HAM", "8:40-9:55", 86);
    econ4710.setEnrolledStudentCount(37);
    econCourses.put("4710", econ4710);

    Course econ4840 = new Course("Mark Dean", "142 URIS", times[3], 108);
    econ4840.setEnrolledStudentCount(67);
    econCourses.put("4840", econ4840);

    //Data for ELEN department courses.
    HashMap<String, Course> elenCourses = new HashMap<>();

    Course elen1201 = new Course("David G Vallancourt", "301 PUP", times[1], 120);
    elen1201.setEnrolledStudentCount(108);
    elenCourses.put("1201", elen1201);

    Course elen3082 = new Course("Kenneth Shepard", "1205 MUDD", "4:10-6:40", 32);
    elen3082.setEnrolledStudentCount(30);
    elenCourses.put("3082", elen3082);

    Course elen3331 = new Course("David G Vallancourt", "203 MATH", times[0], 80);
    elen3331.setEnrolledStudentCount(54);
    elenCourses.put("3331", elen3331);

    Course elen3401 = new Course("Keren Bergman", "829 MUDD", times[3], 40);
    elen3401.setEnrolledStudentCount(25);
    elenCourses.put("3401", elen3401);

    Course elen3701 = new Course("Irving Kalet", "333 URIS", times[3], 50);
    elen3701.setEnrolledStudentCount(24);
    elenCourses.put("3701", elen3701);

    Course elen4510 = new Course("Mohamed Kamaludeen", "903 SSW", "7:00-9:30", 30);
    elen4510.setEnrolledStudentCount(22);
    elenCourses.put("4510", elen4510);

    Course elen4702 = new Course("Alexei Ashikhmin", "332 URIS", "7:00-9:30", 50);
    elen4702.setEnrolledStudentCount(5);
    elenCourses.put("4702", elen4702);

    Course elen4830 = new Course("Christine P Hendon", "633 MUDD", "10:10-12:40", 60);
    elen4830.setEnrolledStudentCount(22);
    elenCourses.put("4830", elen4830);

    //Data for IEOR department courses.
    HashMap<String, Course> ieorCourses = new HashMap<>();

    Course ieor2500 = new Course("Uday Menon", "627 MUDD", times[0], 50);
    ieor2500.setEnrolledStudentCount(52);
    ieorCourses.put("2500", ieor2500);

    Course ieor3404 = new Course("Christopher J Dolan", "303 MUDD", times[2], 73);
    ieor3404.setEnrolledStudentCount(80);
    ieorCourses.put("3404", ieor3404);

    Course ieor3658 = new Course("Daniel Lacker", "310 FAY", times[2], 96);
    ieor3658.setEnrolledStudentCount(87);
    ieorCourses.put("3658", ieor3658);

    Course ieor4102 = new Course("Antonius B Dieker", "209 HAM", times[2], 110);
    ieor4102.setEnrolledStudentCount(92);
    ieorCourses.put("4102", ieor4102);

    Course ieor4106 = new Course("Kaizheng Wang", "501 NWC", times[2], 150);
    ieor4106.setEnrolledStudentCount(161);
    ieorCourses.put("4106", ieor4106);

    Course ieor4405 = new Course("Yuri Faenza", "517 HAV", times[0], 80);
    ieor4405.setEnrolledStudentCount(19);
    ieorCourses.put("4405", ieor4405);

    Course ieor4511 = new Course("Michael Robbins", "633 MUDD", "9:00-11:30", 150);
    ieor4511.setEnrolledStudentCount(50);
    ieorCourses.put("4511", ieor4511);

    Course ieor4540 = new Course("Krzysztof M Choromanski", "633 MUDD", "7:10-9:40", 60);
    ieor4540.setEnrolledStudentCount(33);
    ieorCourses.put("4540", ieor4540);

    //Data for PHYS department courses.
    HashMap<String, Course> physCourses = new HashMap<>();

    Course phys1001 = new Course("Szabolcs Marka", "301 PUP", times[3], 150);
    phys1001.setEnrolledStudentCount(131);
    physCourses.put("1001", phys1001);

    Course phys1201 = new Course("Eric Raymer", "428 PUP", times[3], 145);
    phys1201.setEnrolledStudentCount(130);
    physCourses.put("1201", phys1201);

    Course phys1602 = new Course("Kerstin M Perez", "428 PUP", times[2], 140);
    phys1602.setEnrolledStudentCount(77);
    physCourses.put("1602", phys1602);

    Course phys2802 = new Course("Yury Levin", "329 PUP", "10:10-12:00", 60);
    phys2802.setEnrolledStudentCount(23);
    physCourses.put("2802", phys2802);

    Course phys3008 = new Course("William A Zajc", "329 PUP", times[2], 75);
    phys3008.setEnrolledStudentCount(60);
    physCourses.put("3008", phys3008);

    Course phys4003 = new Course("Frederik Denef", "214 PUP", times[1], 50);
    phys4003.setEnrolledStudentCount(19);
    physCourses.put("4003", phys4003);

    Course phys4018 = new Course("James W McIver", "307 PUP", times[3], 30);
    phys4018.setEnrolledStudentCount(18);
    physCourses.put("4018", phys4018);

    Course phys4040 = new Course("James C Hill", "214 PUP", times[1], 50);
    phys4040.setEnrolledStudentCount(31);
    physCourses.put("4040", phys4040);

    // Data for PSYC department courses.
    HashMap<String, Course> psycCourses = new HashMap<>();

    Course psyc1001 = new Course("Patricia G Lindemann", "501 SCH", "1:10-2:25", 200);
    psyc1001.setEnrolledStudentCount(191);
    psycCourses.put("1001", psyc1001);

    Course psyc1610 = new Course("Christopher Baldassano", "200 SCH", times[2], 45);
    psyc1610.setEnrolledStudentCount(42);
    psycCourses.put("1610", psyc1610);

    Course psyc2235 = new Course("Katherine T Fox-Glassman", "501 SCH", times[0], 125);
    psyc2235.setEnrolledStudentCount(128);
    psycCourses.put("2235", psyc2235);

    Course psyc2620 = new Course("Jeffrey M Cohen", "303 URIS", "1:10-3:40", 60);
    psyc2620.setEnrolledStudentCount(55);
    psycCourses.put("2620", psyc2620);

    Course psyc3212 = new Course("Mayron Piccolo", "200 SCH", "2:10-4:00", 15);
    psyc3212.setEnrolledStudentCount(15);
    psycCourses.put("3212", psyc3212);

    Course psyc3445 = new Course("Mariam Aly", "405 SCH", "2:10-4:00", 12);
    psyc3445.setEnrolledStudentCount(12);
    psycCourses.put("3445", psyc3445);

    Course psyc4236 = new Course("Trenton Jerde", "405 SCH", "6:10-8:00", 18);
    psyc4236.setEnrolledStudentCount(17);
    psycCourses.put("4236", psyc4236);

    Course psyc4493 = new Course("Jennifer Blaze", "200 SCH", "2:10-4:00", 15);
    psyc4493.setEnrolledStudentCount(9);
    psycCourses.put("4493", psyc4493);

    // create Department objects using course data
    Department chem = new Department("CHEM", chemCourses, "Laura J. Kaufman", 250);
    Department coms = new Department("COMS", comsCourses, "Luca Carloni", 2700);
    Department econ = new Department("ECON", econCourses, "Michael Woodford", 2345);
    Department elen = new Department("ELEN", elenCourses, "Ioannis Kymissis", 250);
    Department ieor = new Department("IEOR", ieorCourses, "Jay Sethuraman", 67);
    Department phys = new Department("PHYS", physCourses, "Dmitri N. Basov", 43);
    Department psyc = new Department("PSYC", psycCourses, "Nim Tottenham", 437);

    // create hashmap and add all mappings for dept code and department object
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("CHEM", chem);
    mapping.put("COMS", coms);
    mapping.put("ECON", econ);
    mapping.put("ELEN", elen);
    mapping.put("IEOR", ieor);
    mapping.put("PHYS", phys);
    mapping.put("PSYC", psyc);

    myFileDatabase.setMapping(mapping);
  }

  /**
   * This contains all the overheading teardown logic, it will
   * mainly be focused on saving all the created user data to a
   * file, so it will be ready for the next setup.
   */
  @PreDestroy
  public void onTermination() {
    System.out.println("Termination");
    if (saveData) {
      myFileDatabase.saveContentsToFile();
    }
  }

  //Database Instance
  public static MyFileDatabase myFileDatabase;
  private static boolean saveData = true;
}
