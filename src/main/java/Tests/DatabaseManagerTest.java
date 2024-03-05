package Tests;

import BusinessLogicLayer.Course;
import BusinessLogicLayer.DatabaseManager;
import BusinessLogicLayer.Review;
import BusinessLogicLayer.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DatabaseManagerTest {
    private DatabaseManager testDatabaseManager;
    @BeforeEach
    public void setUp(){
        testDatabaseManager = new DatabaseManager();
    }

    @Test
    @DisplayName("Check connection fails after a second call")
    public void testDoubleConnect(){
        testDatabaseManager.connect();
        assertThrows(IllegalStateException.class, () ->
                testDatabaseManager.connect());
        testDatabaseManager.disconnect();
    }

    @Test
    @DisplayName("Check create tables throws error if not connected")
    public void testTableConnection(){
        assertThrows(IllegalStateException.class, () ->
                testDatabaseManager.createTables());
    }

    @Test
    @DisplayName("Checks that deleteTables deletes if there are tables and throws error if not")
    public void testDeleteTables(){
        testDatabaseManager.connect();
        testDatabaseManager.createTables();
        testDatabaseManager.deleteTables();
        assertThrows(IllegalStateException.class, () ->
                testDatabaseManager.deleteTables());
        testDatabaseManager.disconnect();
    }


}
