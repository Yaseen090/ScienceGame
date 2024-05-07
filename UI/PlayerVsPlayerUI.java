package UI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import UI.ClientUI;
import UI.GameMenuUI;
import UI.PlayerVsComputerUI;
import UI.ServerUI;
import UI.TimedSinglePlayerUI;
import assets.Board;
import assets.Dictionary;
import Server.Server;
import assets.Tile;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.effect.Glow;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.input.Dragboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.TransferMode;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PlayerVsPlayerUI extends Application {

    @Override
    public void start(Stage primaryStage) {
         primaryStage.setTitle("Scrabble Multiplayer");

        // Load image
        // Image logoImage = new Image("yourcompany/logo.png");
        // ImageView imageView = new ImageView(logoImage);

        Text titleText = new Text("Play Multiplayer");
        titleText.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 24));
        titleText.setFill(Color.DARKSLATEBLUE);
        titleText.setEffect(new Glow(0.8));  // Add glow effect to text

        // Create buttons with custom style
        Button btnPlayerVsPlayer = createStyledButton("Start New Server");
        btnPlayerVsPlayer.setOnAction(e -> {
            ServerUI pvpUI = new ServerUI();
            pvpUI.start(new Stage());
            primaryStage.close();
        });

        Button btnPlayerVsComputer = createStyledButton("Join Game");
        btnPlayerVsComputer.setOnAction(e -> {
            ClientUI pvcUI = new ClientUI();
            pvcUI.start(new Stage());
            primaryStage.close();
        });

        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
        new Stop(0, Color.web("White")), new Stop(1, Color.web("Blue")));

        // Create layout
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(titleText,btnPlayerVsPlayer, btnPlayerVsComputer);
        layout.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

        // Set background color
        // layout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.5), layout);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        // Show the scene
        Scene scene = new Scene(layout, 300, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(200);
        button.setPrefHeight(40);
        button.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 14));
        // button.setFont(Font.loadFont(getClass().getResourceAsStream("C:\\Users\\ATOnline\\Desktop\\fonts\\Montserrat-Light.ttf"),14));
        button.setStyle("-fx-background-color: #1e81b0; -fx-text-fill: lightgreen; -fx-border-color: #1e81b0; -fx-border-radius: 10;");
        // Hover Effect
        ScaleTransition st = new ScaleTransition(Duration.millis(200), button);
        st.setToX(1.1);
        st.setToY(1.1);
        button.setOnMouseEntered(e -> {
            st.playFromStart();
            button.setStyle("-fx-background-color: #3a9ad9; -fx-text-fill: yellow;");
        });
        button.setOnMouseExited(e -> {
            st.stop();
            button.setScaleX(1.0);
            button.setScaleY(1.0);
            button.setStyle("-fx-background-color: #1e81b0; -fx-text-fill: lightgreen;");
        });
        return button;
    }

    // public void startGame() {
    //     while (!isGameOver()) {
    //         Player currentPlayer = players[currentTurn];
    //         // Await player's move (e.g., placing tiles on the board)
    //         // Validate and apply the move
    //         // Update scores
    //         // Check for game end conditions
    //         currentTurn = (currentTurn + 1) % players.length;
    //     }
    // }
  
    public static void main(String[] args) {
        launch(args);
    }
}
