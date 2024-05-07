package UI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.DatabaseConnection;

public class SignUpUI extends Application {

    private DatabaseConnection databaseConnection;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sign Up");

        databaseConnection = new DatabaseConnection();

        Label lblTitle = new Label("Sign Up");
        lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblTitle.setTextFill(Color.WHITE);

        Label lblUsername = new Label("Username:");
        TextField txtUsername = new TextField();

        Label lblPassword = new Label("Password:");
        PasswordField txtPassword = new PasswordField();

        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField();

        Label lblDOB = new Label("Date of Birth:");
        TextField txtDOB = new TextField();

        Button btnSignUp = new Button("Sign Up");
        btnSignUp.setOnAction(e -> {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String email = txtEmail.getText();
            String dob = txtDOB.getText();

            if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty() && !dob.isEmpty()) {
                signUpUser(username, password, email, dob);
            }
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(lblTitle, lblUsername, txtUsername, lblPassword, txtPassword, lblEmail, txtEmail, lblDOB, txtDOB, btnSignUp);
        layout.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(layout, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void signUpUser(String username, String password, String email, String dob) {
        String query = "INSERT INTO Users (Username, Password, Email, DOB) VALUES (?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, dob);
            preparedStatement.executeUpdate();
            System.out.println("User signed up successfully!");
        } catch (SQLException e) {
            System.err.println("Error signing up user: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
