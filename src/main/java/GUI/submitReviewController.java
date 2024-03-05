package GUI;
import BusinessLogicLayer.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;

public class submitReviewController {
    @FXML
    private TextField submissionTextField;

    @FXML
    private Button submitButton;

    @FXML
    private Button backButton;

    @FXML
    private Label errorMessageLabel;
    @FXML
    private TextField ratingText;

    @FXML
    private void handleSubmitButtonAction() {
        // Handle submit button action
        String submission = submissionTextField.getText();
        // Perform necessary logic with the submission
        if(validateSubmission(submission)){
            submitReview(submission);
            loadFXML("main-menu.fxml");
        }
    }


    private void submitReview(String submission) {
        //do something with singleton here that submits a review for a course
        DataSingleton temp = DataSingleton.getInstance();
        Review reviewToSubmit = new Review(temp.getCurrentStudent(), temp.getCurrentCourse(), submission, Integer.parseInt(ratingText.getText()));
        temp.getDatabaseManager().addReview(reviewToSubmit);
    }

    private boolean validateSubmission(String submission) {
        if (submission.isEmpty()){
            errorMessageLabel.setText("Submission field empty.");
            return false;
        }
        if (!validateRating(ratingText.getText())){
            errorMessageLabel.setText("Please set the rating box to an integer from 1-5.");
            return false;
        }
        DataSingleton temp = DataSingleton.getInstance();
        if (temp.getDatabaseManager().studentHasReviewedCourse(temp.getCurrentStudent(),temp.getCurrentCourse())){
            errorMessageLabel.setText("Student has already reviewed this course.");
            return false;
        }
        return true;
    }

    private boolean validateRating(String text) {
        try {
            int value = Integer.parseInt(text);
            return value >= 1 && value <= 5;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
    private void handleBackButtonAction() {
        // Handle back button action
        // Example: Go back to the previous screen or close the window
        loadFXML("main-menu.fxml");
    }

    private void loadFXML(String fxmlFileName) {
        try{
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
