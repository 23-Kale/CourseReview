package GUI;
import BusinessLogicLayer.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class CreateAccountController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button submitButton;

    @FXML
    private Label errorMessageLabel;
    @FXML
    private Button backButton;
    private DatabaseManager database;

    public void initialize(){
        DataSingleton singleton = DataSingleton.getInstance();
        database = singleton.getDatabaseManager();
    }
    @FXML
    private void handleSubmitButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Perform necessary actions with the account information, such as creating a new account

        // Example: Print the account information
        if (validateAccountCreation(username, password, confirmPassword)){
            loadFXML("main-menu.fxml");
        }
    }
    private boolean validateAccountCreation(String username, String password, String confirmPassword) {
        if (username.equals("") || password.equals("") || confirmPassword.equals("")){
            errorMessageLabel.setText("One of the fields are null.");
            return false;
        }
        if (userExistsInTable(username)){
            errorMessageLabel.setText("User already exists in database.");
            return false;
        }
        if (password.equals(confirmPassword)){
            addAccountToDatabase(username, password);
            DataSingleton.getInstance().setCurrentStudent(new Student(username, password));
            return true;
        }
        else{
            errorMessageLabel.setText("Passwords don't match.");
            return false;
        }
    }

    //TO DO: return true of if the user exists in the database with just the username
    private boolean userExistsInTable(String username) {
        return database.doesUserExist(username);
    }

    private void addAccountToDatabase(String username, String password) {
        database.addStudent(new Student(username, password));
    }

    @FXML
    private void handleBackButtonAction() {
        // Load the FXML file for the login screen
        loadFXML("login-screen.fxml");
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
