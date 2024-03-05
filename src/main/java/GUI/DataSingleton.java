package GUI;
import BusinessLogicLayer.*;

//LOAD DATA HERE IN ORDER TO SHARE DATA ACROSS CONTROLLERS
public class DataSingleton {
    // Private static instance of the singleton
    private static DataSingleton instance;

    private DatabaseManager database;
    private Course currentCourse;
    private Student currentStudent;
    // Private constructor to prevent instantiation from outside the class
    private DataSingleton() {
        // Initialization code, if needed
        database = new DatabaseManager();
    }

    // Public static method to get the instance of the singleton
    public static DataSingleton getInstance() {
        if (instance == null) {
            // Create a new instance if it doesn't exist
            instance = new DataSingleton();
        }
        return instance;
    }

    // Other methods and variables of the singleton class
    // ...
    public DatabaseManager getDatabaseManager(){return database;}
    public void setCurrentCourse(Course course){
        currentCourse = course;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public Course getCurrentCourse() {
        return currentCourse;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }
}




