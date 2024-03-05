package GUI;
import BusinessLogicLayer.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class MainMenuController {
    @FXML
    private Button backButton;

    @FXML
    private Button submitCourseReviewButton;

    @FXML
    private TextField courseInputField;
    @FXML
    private Label errorMessageLabel;

    private DatabaseManager database;

    public void initialize(){
        DataSingleton singleton = DataSingleton.getInstance();
        database = singleton.getDatabaseManager();
    }


    @FXML
    private void handleBackButtonAction() {
        // Load the FXML file for the login screen
        loadFXML("login-screen.fxml");
    }

    @FXML
    private void handleSubmitReviewButtonPress() {
        // Load the FXML file for the course review screen
        if (validateCourse(courseInputField.getText())) {
            loadFXML("submit-review.fxml");
        }
    }
    @FXML
    private void handleSeeReviewsForCourseButtonAction() {
        if (validateCourse(courseInputField.getText())) {
            loadFXML("course-review.fxml");
        }
    }

    private boolean validateCourse(String courseName){
        String updatedString = courseName;
        String[] arr = updatedString.split(" ");
        try {
            String department = arr[0];
            int catalogNumber = Integer.parseInt(arr[1]);
            Course temp = new Course(department, catalogNumber);
            DataSingleton.getInstance().setCurrentCourse(temp);
            return true;
        }
        catch( IndexOutOfBoundsException e ) {
            errorMessageLabel.setText("Wrong input format");
        }
        catch (NumberFormatException e){
            errorMessageLabel.setText("Please enter numbers for catalog number.");
        }
        catch (IllegalArgumentException e){
            errorMessageLabel.setText(e.getMessage());
        }
        return false;
    }

    private void loadFXML(String fxmlFileName) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML file
            Scene scene = new Scene(root);

            // Get the stage from the current scene
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Set the new scene on the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any potential exceptions while loading the FXML file
        }
    }

}
