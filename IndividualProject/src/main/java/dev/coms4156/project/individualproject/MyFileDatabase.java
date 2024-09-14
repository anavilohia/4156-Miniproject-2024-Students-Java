package dev.coms4156.project.individualproject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents a file-based database containing department mappings.
 */
public class MyFileDatabase {

  /**
   * Constructs a MyFileDatabase object and loads up the data structure with
   * the contents of the file.
   *
   * @param flag     used to distinguish mode of database
   * @param filePath the path to the file containing the entries of the database
   */
  public MyFileDatabase(int flag, String filePath) {
    this.filePath = filePath;

    if (flag == 0) {
      this.departmentMapping = deSerializeObjectFromFile();
    } else {
      this.departmentMapping = new HashMap<>();
    }
  }

  /**
   * Sets the department mapping of the database.
   *
   * @param mapping the mapping of department names to Department objects
   */
  public void setMapping(HashMap<String, Department> mapping) {
    this.departmentMapping = mapping == null ? new HashMap<>() : mapping;
  }

  /**
   * Deserializes the object from the file and returns the department mapping.
   * Throws exception if data in file is invalid
   *
   * @return the deserialized department mapping
   */
  public final HashMap<String, Department> deSerializeObjectFromFile() {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
      Object obj = in.readObject();
      if (obj instanceof HashMap<?, ?> mapObj) {
        HashMap<String, Department> finalDepartmentMap = new HashMap<>();

        for (Object key : mapObj.keySet()) {
          if (!(key instanceof String)) {
            throw new IllegalArgumentException("Invalid object type in file.");
          }
          Object value = mapObj.get(key);
          if (!(value instanceof Department)) {
            throw new IllegalArgumentException("Invalid object type in file.");
          }
          finalDepartmentMap.put((String) key, (Department) value);
        }
        return finalDepartmentMap;
      }
      throw new IllegalArgumentException("Invalid object type in file.");
    } catch (IOException | ClassNotFoundException e) {
      logger.log(Level.SEVERE, e.getMessage());
      return new HashMap<>();
    }
  }

  /**
   * Saves the contents of the internal data structure to the file. Contents of the file are
   * overwritten with this operation.
   */
  public void saveContentsToFile() {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
      out.writeObject(departmentMapping);
      logger.info("Object serialized successfully.");
    } catch (IOException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }

  /**
   * Gets the department mapping of the database.
   *
   * @return the department mapping
   */
  public HashMap<String, Department> getDepartmentMapping() {
    return this.departmentMapping;
  }

  /**
   * Returns a string representation of the database.
   *
   * @return a string representation of the database
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, Department> entry : departmentMapping.entrySet()) {
      String key = entry.getKey();
      Department value = entry.getValue();
      result.append("For the ").append(key).append(" department: \n").append(value.toString());
    }
    return result.toString();
  }

  /**
   * The path to the file containing the database entries.
   */
  private final String filePath;

  /**
   * The mapping of department names to Department objects.
   */
  private HashMap<String, Department> departmentMapping = new HashMap<>();

  /**
   * Logger to print exceptions.
   */
  private static final Logger logger = Logger.getLogger(MyFileDatabase.class.getName());
}
