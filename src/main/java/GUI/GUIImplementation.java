package GUI;

import BusinessLogicLayer.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIImplementation extends Application{

        @Override
        public void start(Stage stage) throws IOException {
            DatabaseManager database = addData();
            database.connect();
            FXMLLoader fxmlLoader = new FXMLLoader(GUIImplementation.class.getResource("login-screen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Course Review");
            stage.setScene(scene);
            stage.show();
        }

        //FOR TESTING PURPOSES WILL CHANGE LATER ? CODE FROM MAIN IN THE BUSINESS LOGIC LAYER
    private DatabaseManager addData() {
        DataSingleton singleton = DataSingleton.getInstance();
        DatabaseManager databaseManager = singleton.getDatabaseManager();
        databaseManager.connect();
        databaseManager.createTables();
        Student tony = new Student("Tony Chang", "milk");
        Student jake = new Student("Jake Li", "juice");
        Student jofu = new Student("Joshua Fu", "coffee");
        databaseManager.addStudent(tony);
        databaseManager.addStudent(jake);
        databaseManager.addStudent(jofu);
        Course sde = new Course("CS", 3140);
        Course dsa = new Course("CS", 3100);
        databaseManager.addCourse(sde);
        databaseManager.addCourse(dsa);
        Review tonyReview = new Review(tony, sde, "Loved it", 5);
        Review jakeReview = new Review(jake, sde, "Hated it", 3);
        Review jofuReview = new Review(jofu, sde, "Kinda mid", 3);
        Review tonyReview2 = new Review(tony, dsa, "What even is this class", 2);
        Review jakeReview2 = new Review(jake, dsa, "I'm doing great", 4);
        databaseManager.addReview(tonyReview);
        databaseManager.addReview(jakeReview);
        databaseManager.addReview(jofuReview);
        databaseManager.addReview(tonyReview2);
        databaseManager.addReview(jakeReview2);
        databaseManager.seeReviewsForCourse(sde);
//        databaseManager.seeReviewsForCourse(dsa);
        databaseManager.disconnect();
        return databaseManager;
    }

    public static void main(String[] args) {
            launch();
        }

}
