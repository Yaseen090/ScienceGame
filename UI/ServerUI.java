package UI;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.scene.Node;

import UI.GameMenuUI;
import assets.Board;
import assets.Dictionary;
import assets.Move;
import assets.Tile;
import Server.Server;
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


public class ServerUI extends Application {

    List<Rectangle> selectedRackTiles;
    List<Tile> clientList;
    List<Tile> combinedList;
    Dictionary dictionary;
    boolean firstMove;
    int totalscore;
    int totaltiles;

    @Override
    public void start(Stage primaryStage) {
        totaltiles=0;
        Server server=new Server(2000);
        totalscore=0;
        dictionary=new Dictionary("D:\\JavaProjects\\Current\\updated_game_project\\sowpods.txt");
        firstMove=true;
        selectedRackTiles=new ArrayList<>();
        primaryStage.setTitle("Player Vs Player");

        // Load background image
        // Image backgroundImage = new Image("yourcompany/background.jpg");
        // ImageView backgroundView = new ImageView(backgroundImage);

        // Create a label
        Text titleText = new Text("Host");
        titleText.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 24));
        titleText.setFill(Color.DARKSLATEBLUE);
        titleText.setEffect(new Glow(0.8));  // Add glow effect to text


        // Create a button to return to the menu
        Button btnMenu = createStyledButton("Back to Menu");
        btnMenu.setOnAction(e -> {
            PlayerVsPlayerUI menuUI = new PlayerVsPlayerUI();
            menuUI.start(new Stage());
            primaryStage.close();
        });

        Label score=new Label("Score: 0");

        Board gameBoard=new Board();
        GridPane gridBoard=gameBoard.initializeBoard();

        Button submitButton = new Button("Submit Move");
        submitButton.setOnAction(event -> {
            if (validateMove(gameBoard)) {
                firstMove=false;
                for (Rectangle tile: selectedRackTiles){
                    if(((Pane) tile.getParent())!=null)
                       ((Pane) tile.getParent()).getChildren().clear();
                }
                totaltiles+=gameBoard.getTempTilesList().size();
                totalscore+=calculateScore(gameBoard.getTempTilesList());
                score.setText("Score: "+totalscore);
                primaryStage.show();
                if(totaltiles>=10)
                {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Game Over!");
                    alert.setHeaderText("Tiles Finished!");
                    alert.setContentText("you Won!");
                
                    alert.showAndWait();
                    PlayerVsPlayerUI menuUI = new PlayerVsPlayerUI();
                    menuUI.start(new Stage());
                    server.close();
                    primaryStage.close();
                }
                else {
                    server.sendObject(gameBoard.getTempTilesList());
                    clientList = (List) server.receiveObject();
                    for (Tile tile : clientList) {
                        StackPane cell = (StackPane) getNodebyIndex(tile.getX(), tile.getY(), gridBoard);
                        StackPane letterCell = new StackPane();
                        letterCell.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
                        Text droppedLetter = new Text(Character.toString(tile.getLetter()));
                        droppedLetter.setStroke(Color.BLACK);
                        letterCell.getChildren().add(droppedLetter);
                        cell.getChildren().add(letterCell);
                        gameBoard.getTiles()[GridPane.getRowIndex(cell)][GridPane.getColumnIndex(cell)].setLetter(tile.getLetter());
                    }
                }
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error!");
                alert.setHeaderText("Invalid Word Error!");
                alert.setContentText("Please Select a valid word!");
                
                alert.showAndWait();
                for(Tile tile: gameBoard.getTempTilesList()){
                    gameBoard.getTiles()[tile.getX()][tile.getY()].setLetter('\0');
                  ((StackPane) getNodebyIndex(tile.getX(),tile.getY(),gridBoard)).getChildren().clear();
                }
                for (Rectangle tile: selectedRackTiles){
                    tile.setOpacity(1);
                }
                primaryStage.show();
            }
            gameBoard.clearCurrentTurnTiles();
            if(combinedList!=null)
                combinedList.clear();
        });

        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
        new Stop(0, Color.web("White")), new Stop(1, Color.web("Blue")));

        VBox tileRack = new VBox(5); // 5 is the spacing between tiles
        tileRack.setAlignment(Pos.CENTER);
        
        // Sample tiles for the rack, you would generate these based on the actual game state
        Map<String,Integer> tileLetters=new HashMap<>();
        tileLetters.put("A", 9);
        tileLetters.put("B", 2);
        tileLetters.put("C", 2);
        tileLetters.put("D", 4);
        tileLetters.put("E", 12);
        tileLetters.put("F", 2);
        tileLetters.put("G", 3);
        tileLetters.put("H", 2);
        tileLetters.put("I", 9);
        tileLetters.put("J", 1);
        tileLetters.put("K", 1);
        tileLetters.put("L", 4);
        tileLetters.put("M", 2);
        tileLetters.put("N", 6);
        tileLetters.put("O", 8);
        tileLetters.put("P", 2);
        tileLetters.put("Q", 1);
        tileLetters.put("R", 6);
        tileLetters.put("S", 4);
        tileLetters.put("T", 6);
        tileLetters.put("U", 4);
        tileLetters.put("V", 2);
        tileLetters.put("W", 2);
        tileLetters.put("X", 1);
        tileLetters.put("Y", 2);
        tileLetters.put("Z", 1);
        tileLetters.put("[",2);

        // Sample tiles for the rack, you would generate these based on the actual game state
        Random random=new Random();
        Map<String,Integer> tempLetters=new HashMap<>();
        tempLetters.put("A", 0);
        tempLetters.put("B", 0);
        tempLetters.put("C", 0);
        tempLetters.put("D", 0);
        tempLetters.put("E", 0);
        tempLetters.put("F", 0);
        tempLetters.put("G", 0);
        tempLetters.put("H", 0);
        tempLetters.put("I", 0);
        tempLetters.put("J", 0);
        tempLetters.put("K", 0);
        tempLetters.put("L", 0);
        tempLetters.put("M", 0);
        tempLetters.put("N", 0);
        tempLetters.put("O", 0);
        tempLetters.put("P", 0);
        tempLetters.put("Q", 0);
        tempLetters.put("R", 0);
        tempLetters.put("S", 0);
        tempLetters.put("T", 0);
        tempLetters.put("U", 0);
        tempLetters.put("V", 0);
        tempLetters.put("W", 0);
        tempLetters.put("X", 0);
        tempLetters.put("Y", 0);
        tempLetters.put("Z", 0);
        tempLetters.put("[", 0);
        char[] tiles = {' ',' ',' ',' ',' ',' ',' '}; // Example set of tiles
        for(int i=0;i<7;i++)
        {
            char temp=(char)(random.nextInt(26)+65);
            while((tempLetters.get(Character.toString(temp))+1)>(tileLetters.get(Character.toString(temp))))
                temp=(char)(random.nextInt(26)+65);
            tempLetters.put(Character.toString(temp),tempLetters.get(Character.toString(temp))+1);
            tiles[i]=temp;
        }
        for (char tileLetter : tiles) {
            if(tileLetter=='A' || tileLetter== 'E' || tileLetter== 'I' || tileLetter== 'O' || tileLetter== 'U' || tileLetter== 'L' || tileLetter== 'N' || tileLetter== 'S' || tileLetter== 'T' || tileLetter== 'R')
                tileRack.getChildren().add(createTile(String.valueOf(tileLetter),1));
            else if(tileLetter=='D' || tileLetter== 'G')
                tileRack.getChildren().add(createTile(String.valueOf(tileLetter),2));
            else if(tileLetter=='B' || tileLetter== 'C' || tileLetter== 'M' || tileLetter== 'P')
                tileRack.getChildren().add(createTile(String.valueOf(tileLetter),3));
            else if(tileLetter=='F' || tileLetter== 'H' || tileLetter== 'V' || tileLetter== 'W' || tileLetter== 'Y')
                tileRack.getChildren().add(createTile(String.valueOf(tileLetter),4));
            else if(tileLetter=='K')
                tileRack.getChildren().add(createTile(String.valueOf(tileLetter),5));
            else if(tileLetter=='J' || tileLetter== 'X')
                tileRack.getChildren().add(createTile(String.valueOf(tileLetter),8));
            else if(tileLetter=='Q' || tileLetter== 'Z')
                tileRack.getChildren().add(createTile(String.valueOf(tileLetter),10));
            else
                tileRack.getChildren().add(createTile(String.valueOf(" "),1));
        }
        Button shuffleButton = new Button("Shuffle Tiles");
        shuffleButton.setOnAction(event->{
            tileRack.getChildren().clear();
            Map<String,Integer> tempLetters2=new HashMap<>();
            tempLetters2.put("A", 0);
            tempLetters2.put("B", 0);
            tempLetters2.put("C", 0);
            tempLetters2.put("D", 0);
            tempLetters2.put("E", 0);
            tempLetters2.put("F", 0);
            tempLetters2.put("G", 0);
            tempLetters2.put("H", 0);
            tempLetters2.put("I", 0);
            tempLetters2.put("J", 0);
            tempLetters2.put("K", 0);
            tempLetters2.put("L", 0);
            tempLetters2.put("M", 0);
            tempLetters2.put("N", 0);
            tempLetters2.put("O", 0);
            tempLetters2.put("P", 0);
            tempLetters2.put("Q", 0);
            tempLetters2.put("R", 0);
            tempLetters2.put("S", 0);
            tempLetters2.put("T", 0);
            tempLetters2.put("U", 0);
            tempLetters2.put("V", 0);
            tempLetters2.put("W", 0);
            tempLetters2.put("X", 0);
            tempLetters2.put("Y", 0);
            tempLetters2.put("Z", 0);
            tempLetters2.put("[", 0);
            char[] tiles2 = {' ',' ',' ',' ',' ',' ',' '}; // Example set of tiles
            for(int i=0;i<7;i++)
            {
                char temp=(char)(random.nextInt(27)+65);
                while((tempLetters2.get(Character.toString(temp))+1)>(tileLetters.get(Character.toString(temp))))
                    temp=(char)(random.nextInt(27)+65);
                tempLetters2.put(Character.toString(temp),tempLetters2.get(Character.toString(temp))+1);
                tiles2[i]=temp;
            }
            for (char tileLetter : tiles2) {
                if(tileLetter=='A' || tileLetter== 'E' || tileLetter== 'I' || tileLetter== 'O' || tileLetter== 'U' || tileLetter== 'L' || tileLetter== 'N' || tileLetter== 'S' || tileLetter== 'T' || tileLetter== 'R')
                    tileRack.getChildren().add(createTile(String.valueOf(tileLetter),1));
                else if(tileLetter=='D' || tileLetter== 'G')
                    tileRack.getChildren().add(createTile(String.valueOf(tileLetter),2));
                else if(tileLetter=='B' || tileLetter== 'C' || tileLetter== 'M' || tileLetter== 'P')
                    tileRack.getChildren().add(createTile(String.valueOf(tileLetter),3));
                else if(tileLetter=='F' || tileLetter== 'H' || tileLetter== 'V' || tileLetter== 'W' || tileLetter== 'Y')
                    tileRack.getChildren().add(createTile(String.valueOf(tileLetter),4));
                else if(tileLetter=='K')
                    tileRack.getChildren().add(createTile(String.valueOf(tileLetter),5));
                else if(tileLetter=='J' || tileLetter== 'X')
                    tileRack.getChildren().add(createTile(String.valueOf(tileLetter),8));
                else if(tileLetter=='Q' || tileLetter== 'Z')
                    tileRack.getChildren().add(createTile(String.valueOf(tileLetter),10));
                else
                    tileRack.getChildren().add(createTile(String.valueOf(" "),1));
            }
            primaryStage.show();
        });

        VBox butlayout=new VBox(20,tileRack,shuffleButton);

        HBox gameLayout = new HBox(20, butlayout, gridBoard); // 10 is the spacing between the rack and the board
        gameLayout.setAlignment(Pos.CENTER);

        HBox scoreLayout = new HBox(20, score, btnMenu); // 10 is the spacing between the rack and the board
        scoreLayout.setAlignment(Pos.TOP_LEFT);


        // Create layout
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(titleText,scoreLayout,gameLayout,submitButton);
        layout.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.5), layout);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        // Show the scene
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private Node getNodebyIndex(int row,int column, GridPane grid){
            
        Node result=null;
        for(Node node: grid.getChildren()){
            if(GridPane.getRowIndex(node)!=null && GridPane.getRowIndex(node)==row && GridPane.getColumnIndex(node)==column && GridPane.getColumnIndex(node)!=null){
                result=node;
                break;
            }
        }
        return result;

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
    private StackPane createTile(String letter, int number) {
        Text letterText = new Text(letter);
        letterText.setFont(Font.font(18)); // Adjust font size as needed

        Text subscriptText = new Text(String.valueOf(number));
        subscriptText.setStyle("-fx-font-size: 10px;"); // Smaller font size for subscript

        HBox hbox = new HBox(5, letterText, subscriptText); // Spacing of 5 between letter and number
        hbox.setAlignment(Pos.CENTER);// Align to the bottom right of the tile

        Rectangle tile = new Rectangle(30, 30); // The tile itself
        tile.setFill(Color.WHITE);
        tile.setStroke(Color.BLACK);

        StackPane stackPane = new StackPane(tile, hbox); // Stack the tile and text on top of each other
        stackPane.setAlignment(Pos.CENTER); // Center everything within the StackPane

        stackPane.setOnDragDetected(event -> {
            Dragboard db = tile.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(letterText.getText()); // Transfer the tile's letter
            db.setContent(content);
            tile.setOpacity(0.4);
            SnapshotParameters snapshotParams = new SnapshotParameters();
            snapshotParams.setFill(Color.WHITE);
            Image tileImage = tile.snapshot(snapshotParams, null);
            db.setDragView(tileImage);
            event.consume();
        });
        tile.setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                // The following line removes the tile from the rack (or source container)
                selectedRackTiles.add(tile);
                //((Pane) tile.getParent()).getChildren().remove(tile);
            } else {
                // If the drag was not successful, reset the visual feedback
                tile.setOpacity(1.0);
            }
            event.consume();
        });

        return stackPane;
    }
    private int calculateScore(List<Tile> tempList)
    {
        int score=0;
        for(Tile tile:tempList)
        {
            if(tile.getLetter()=='A' || tile.getLetter()== 'E' || tile.getLetter()== 'I' || tile.getLetter()== 'O' || tile.getLetter()== 'U' || tile.getLetter()== 'L' || tile.getLetter()== 'N' || tile.getLetter()== 'S' || tile.getLetter()== 'T' || tile.getLetter()== 'R')
                score++;
            else if(tile.getLetter()=='D' || tile.getLetter()== 'G')
                score+=2;
            else if(tile.getLetter()=='B' || tile.getLetter()== 'C' || tile.getLetter()== 'M' || tile.getLetter()== 'P')
                score+=3;
            else if(tile.getLetter()=='F' || tile.getLetter()== 'H' || tile.getLetter()== 'V' || tile.getLetter()== 'W' || tile.getLetter()== 'Y')
                score+=4;
            else if(tile.getLetter()=='K')
                score+=5;
            else if(tile.getLetter()=='J' || tile.getLetter()== 'X')
                score+=8;
            else if(tile.getLetter()=='Q' || tile.getLetter()== 'Z')
                score+=10;
            else
                score++;
        }
        return score;
    }
    public boolean validateMove(Board gameBoard) {
        List<Tile> currentTurnTiles = gameBoard.getTempTilesList(); // Tiles placed in the current turn
        if (currentTurnTiles.isEmpty()) {
            return false; // No tiles placed
        }

        if (firstMove && !gameBoard.isOnCenterTile(currentTurnTiles)) {
            System.out.println("Center");
            return false;
        }

        // Checking if the newly placed tiles form a linear word
        boolean isLinear = gameBoard.areTilesInLine(currentTurnTiles);
        if (!isLinear) {
            System.out.println("Tiles must be placed in a line.");
            return false;
        }
        // Check for and validate intersecting words
        for (Tile tile : currentTurnTiles) {
            // Assuming getIntersectingWords method returns all words formed with this tile
            List<String> intersectingWords = gameBoard.getIntersectingWords(tile);
            for (String word : intersectingWords) {
                if(word.strip().length()>1) {
                    if (!dictionary.isValidWord(word.strip())) {
                        System.out.println("Invalid intersecting word: " + word);
                        return false;
                    }
                }
            }
        }

        // If all checks pass, the move is valid
        return true;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
