package GUI;
import BusinessLogicLayer.*;
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

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;
    @FXML
    private Label errorMessageLabel;

    private DatabaseManager database;

    public void initialize(){
        DataSingleton singleton = DataSingleton.getInstance();
        database = singleton.getDatabaseManager();
    }

    @FXML
    protected void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Perform your login validation here
        if (isValidLogin(username, password)) {
            // Open a new window
            openWindow("main-menu.fxml");
        }
    }

    private boolean isValidLogin(String username, String password) {
        if(username.equals("") || password.equals("")){
            errorMessageLabel.setText("One of the fields is null");
            return false;
        }
        Student loginAttempt = new Student(username, password);
        if (database.isStudentRegistered(loginAttempt)){
            DataSingleton.getInstance().setCurrentStudent(loginAttempt);
            return true;
        }
        else{
            errorMessageLabel.setText("Invalid Login Information");
            return false;
        }
    }

    private void openWindow(String filename) {
        try {
            // Load the FXML file for the main app window
            FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML file
            Scene scene = new Scene(root);

            // Get the stage from the current scene
            Stage stage = (Stage) loginButton.getScene().getWindow();

            // Set the new scene on the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any potential exceptions while loading the FXML file
        }
    }

    @FXML
    private void handleCreateAccountButtonAction() {
        openWindow("create-account.fxml");
    }
}
