package assets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import UI.GameMenuUI;
import UI.SignUpUI;
import database.DatabaseConnection;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ScarbbleGame extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Signup/Login");

        // Create the grid pane for layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add components for signup page
        TextField signupUsername = new TextField();
        signupUsername.setPromptText("Username");
        PasswordField signupPassword = new PasswordField();
        signupPassword.setPromptText("Password");
        Button signupButton = new Button("Sign up");
        signupButton.setOnAction(e -> {
            SignUpUI menuUI = new SignUpUI();
            menuUI.start(new Stage());
            primaryStage.close();
        });
        HBox signupBox = new HBox(10);
        signupBox.setAlignment(Pos.BOTTOM_LEFT);
        signupBox.getChildren().add(signupButton);

        // Add components for login page
        TextField loginUsername = new TextField();
        loginUsername.setPromptText("Username");
        PasswordField loginPassword = new PasswordField();
        loginPassword.setPromptText("Password");
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            if(loginUser(loginUsername.getText(), loginPassword.getText()))
            {
                GameMenuUI menuUI = new GameMenuUI();
                menuUI.start(new Stage());
                primaryStage.close();
            }
            else
            {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error!");
                alert.setHeaderText("Invalid Credentials!");
                alert.setContentText("Please Enter correct Username or Password");
                alert.showAndWait();
            }
        });
        HBox loginBox = new HBox(10);
        loginBox.setAlignment(Pos.BOTTOM_RIGHT);
        loginBox.getChildren().add(loginButton);

        // Add components to the grid for both pages
        grid.add(signupUsername, 0, 0);
        grid.add(signupPassword, 0, 1);
        grid.add(signupBox, 0, 2);
        grid.add(loginUsername, 0, 0);
        grid.add(loginPassword, 0, 1);
        grid.add(loginBox, 0, 2);

        // Create scene and set it to the stage
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private boolean loginUser(String username, String password) {
        boolean result=false;
        DatabaseConnection con=new DatabaseConnection();
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = con.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                result=rs.next();
                if(result)
                    System.out.println("User Logged in Successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        con.closeConnection();
        return result;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
