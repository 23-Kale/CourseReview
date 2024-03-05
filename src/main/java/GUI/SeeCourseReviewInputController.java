package GUI;
import BusinessLogicLayer.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class SeeCourseReviewInputController {
    @FXML
    private ScrollPane messageScrollPane;


    @FXML
    private Label averageRatingLabel;

    @FXML
    private Button backButton;

    // Other fields and methods

    private VBox messageContainer;

    private DatabaseManager database;
    public void initialize() {
        messageContainer = new VBox();
        database = DataSingleton.getInstance().getDatabaseManager();
        List<Review> getReview = database.seeReviewsForCourse(DataSingleton.getInstance().getCurrentCourse());
        int average = 0;
        int count = 0;
        for (Review temp: getReview){
            addMessage(temp.getMessage());
            average += temp.getRating();
            count += 1;
        }
        double avg = average/count;
        averageRatingLabel.setText("Course Average Rating: " + (Double.toString(avg)));
        messageScrollPane.setContent(messageContainer);

    }

    public void addMessage(String message) {
        Label messageText = new Label(message); //NEED TO FIX THIS
        messageContainer.getChildren().add(messageText);
    }

    public void addTexts(List<String> texts) {
        for (String text : texts) {
            Label textNode = new Label(text);
            messageContainer.getChildren().add(textNode);
        }
    }

    @FXML
    private void handleBackButtonAction() {
        // Load the FXML file for the main menu screen
        loadFXML("main-menu.fxml");
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
