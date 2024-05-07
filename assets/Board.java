// package com.yourcompany.model;
package assets;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.w3c.dom.Node;

import assets.*;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.input.Dragboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.TransferMode;

public class Board {
    private static final int BOARD_SIZE = 15; // Assuming a 15x15 board
    private Tile[][] tiles;
    private Map<Character, Integer> letterScores;
    private List<Tile> currentTurnTiles;
    private List<Tile> tempTilesList;
    private static final int NO_OF_TILES = 4;

    public Board() {
        tiles = new Tile[BOARD_SIZE][BOARD_SIZE];
        currentTurnTiles = new ArrayList<>();
        tempTilesList=new ArrayList<>();
        // initializeBoard();
        initializeLetterScores();
    }

    public GridPane initializeBoard() {
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        int arr[][]={{2,0,0,3,0,0,0,2,0,0,0,3,0,0,2},
                     {0,1,0,0,0,4,0,0,0,4,0,0,0,1,0},//
                     {0,0,1,0,0,0,3,0,3,0,0,0,1,0,0},//
                     {3,0,0,1,0,0,0,3,0,0,0,1,0,0,3},//
                     {0,0,0,0,1,0,0,0,0,0,1,0,0,0,0},//
                     {0,4,0,0,0,4,0,0,0,4,0,0,0,4,0},//
                     {0,0,3,0,0,0,3,0,3,0,0,0,3,0,0},//
                     {2,0,0,3,0,0,0,1,0,0,0,3,0,0,2},//
                     {0,0,3,0,0,0,3,0,3,0,0,0,3,0,0},//
                     {0,4,0,0,0,4,0,0,0,4,0,0,0,4,0},//
                     {0,0,0,0,1,0,0,0,0,0,1,0,0,0,0},//
                     {3,0,0,1,0,0,0,3,0,0,0,1,0,0,3},//
                     {0,0,1,0,0,0,3,0,3,0,0,0,1,0,0},//
                     {0,1,0,0,0,4,0,0,0,4,0,0,0,1,0},//
                     {2,0,0,3,0,0,0,2,0,0,0,3,0,0,2}};
        // Initialize all tiles on the board
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                tiles[i][j] = new Tile('\0', 0); // Empty tile with zero score
                StackPane cell = new StackPane();
                if(arr[i][j]==1)
                    cell.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: pink; -fx-text-fill: lightgreen; -fx-border-color: #1e81b0;");
                else if(arr[i][j]==2)
                    cell.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: red; -fx-text-fill: lightgreen; -fx-border-color: #1e81b0;");
                else if(arr[i][j]==3)
                    cell.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightblue; -fx-text-fill: lightgreen; -fx-border-color: #1e81b0;");
                else if(arr[i][j]==4)
                    cell.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: blue; -fx-text-fill: lightgreen; -fx-border-color: #1e81b0;");
                else
                    cell.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: yellow; -fx-text-fill: lightgreen; -fx-border-color: #1e81b0;");
                cell.setPrefSize(40,40);
                // Customize cell based on special tiles, e.g., double word score
                Text text = new Text(String.valueOf(tiles[i][j].getLetter()));
                cell.getChildren().add(text);
                cell.setAlignment(Pos.CENTER);
                setupTargetCell(cell);
                gridPane.add(cell, i, j);
            }
        }
        gridPane.setAlignment(Pos.CENTER);
        setSpecialScoreTiles();
        return gridPane;
        // Set special score tiles (e.g., double/triple letter, double/triple word)
        // This implementation assumes special score tiles are predefined
        // You may adjust it based on your game's rules
    }
    public int getNoOfTiles(){
        return NO_OF_TILES;
    }
    private void initializeLetterScores() {
        // Initialize letter scores (assuming standard Scrabble letter scores)
        letterScores = new HashMap<>();
        letterScores.put('A', 1);
        letterScores.put('B', 3);
        letterScores.put('C', 3);
        letterScores.put('D', 2);
        letterScores.put('E', 1);
        // Add scores for other letters as per Scrabble rules
    }

    private void setSpecialScoreTiles() {
        // Set double/triple word and letter scores at specific positions
        // This implementation assumes predefined positions for special score tiles
        // You can modify it as per your game's rules
        int arr[][]={{2,0,0,3,0,0,0,2,0,0,0,3,0,0,2},
                         {0,1,0,0,0,4,0,0,0,4,0,0,0,1,0},//
                         {0,0,1,0,0,0,3,0,3,0,0,0,1,0,0},//
                         {3,0,0,1,0,0,0,3,0,0,0,1,0,0,3},//
                         {0,0,0,0,1,0,0,0,0,0,1,0,0,0,0},//
                         {0,4,0,0,0,4,0,0,0,4,0,0,0,4,0},//
                         {0,0,3,0,0,0,3,0,3,0,0,0,3,0,0},//
                         {2,0,0,3,0,0,0,1,0,0,0,3,0,0,2},//
                         {0,0,3,0,0,0,3,0,3,0,0,0,3,0,0},//
                         {0,4,0,0,0,4,0,0,0,4,0,0,0,4,0},//
                         {0,0,0,0,1,0,0,0,0,0,1,0,0,0,0},//
                         {3,0,0,1,0,0,0,3,0,0,0,1,0,0,3},//
                         {0,0,1,0,0,0,3,0,3,0,0,0,1,0,0},//
                         {0,1,0,0,0,4,0,0,0,4,0,0,0,1,0},//
                         {2,0,0,3,0,0,0,2,0,0,0,3,0,0,2}};
        for(int i=0;i<15;i++)
            for(int j=0;j<15;j++)
                    setTileSpecialType(i, j, arr[i][j]==1, arr[i][j]==4,arr[i][j]==2,arr[i][j]==2);
        // Add more special tiles as needed
    }
    private void setTileSpecialType(int row,int column,boolean doubword,boolean tripleLet,boolean doublelet,boolean tripleword) {
        tiles[row][column].setDoubleWord(doubword);
        tiles[row][column].setTripleLetter(tripleLet);
        tiles[row][column].setDoubleLetter(doublelet);
        tiles[row][column].setTripleWord(tripleword);
    }
    public boolean isValidPlacement(String word, int startX, int startY, boolean horizontal) {
        // Check if the word can be placed at the specified position
        int row = startX, column = startY;
        for (char letter : word.toCharArray()) {
            if (row < 0 || row >= BOARD_SIZE || column < 0 || column >= BOARD_SIZE ||
                    tiles[row][column].getLetter() != ' ' && tiles[row][column].getLetter() != letter)
                return false;
            if (horizontal) column++;
            else row++;
        }
        return true;
    }

    public int calculateScore(String word, int startX, int startY, boolean horizontal) {
        int score = 0;
        int wordMultiplier = 1;

        for (char letter : word.toCharArray()) {
            int row = startX, column = startY;
            while (tiles[row][column].getLetter() != letter) {
                if (horizontal) column++;
                else row++;
            }
            int letterScore = letterScores.getOrDefault(Character.toUpperCase(letter), 0);
            score += letterScore * tiles[row][column].getScore();
        }

        // Apply word multipliers
        int row = startX, column = startY;
        for (char letter : word.toCharArray()) {
            int letterMultiplier = 1;
            if (tiles[row][column].isDoubleWord()) {
                wordMultiplier *= 2;
            } else if (tiles[row][column].isTripleWord()) {
                wordMultiplier *= 3;
            } else if (tiles[row][column].isDoubleLetter()) {
                score += letterScores.getOrDefault(Character.toUpperCase(letter), 0);
            } else if (tiles[row][column].isTripleLetter()) {
                score += 2 * letterScores.getOrDefault(Character.toUpperCase(letter), 0);
            }
            if (horizontal) column++;
            else row++;
        }

        return score * wordMultiplier;
    }
    public boolean isOnCenterTile(List<Tile> combinedList) {
        return combinedList.stream().anyMatch(tile -> tile.getX() == 7 && tile.getY() == 7);
    }

    public boolean areTilesInLine(List<Tile> tiles) {
        // boolean sameRow = combinedList.stream().allMatch(tile -> tile.getY() == combinedList.get(0).getY());
        // boolean sameColumn = combinedList.stream().allMatch(tile -> tile.getX() == combinedList.get(0).getX());
        // return sameRow || sameColumn;
        if (tiles.size() <= 1) return true;

        boolean sameRow = true, sameColumn = true;
        int firstRow = tiles.get(0).getX(), firstColumn = tiles.get(0).getY();

        for (Tile tile : tiles) {
            if (tile.getX() != firstRow) sameColumn = false;
            if (tile.getY() != firstColumn) sameRow = false;
        }

        return sameRow || sameColumn; 
    }

    public boolean areTilesContiguous(List<Tile> combinedList) {
        // Sort tiles based on x and y
        List<Tile> sortedTiles = new ArrayList<>(combinedList);
        sortedTiles.sort(Comparator.comparingInt(Tile::getX).thenComparingInt(Tile::getY));

        // Check for contiguous tiles
        for (int i = 0; i < sortedTiles.size() - 1; i++) {
            Tile current = sortedTiles.get(i);
            Tile next = sortedTiles.get(i + 1);

            // Check adjacency
            if (next.getX() != current.getX() && next.getY() != current.getY()) {
                return false; // Tiles are not contiguous
            }
        }
        return true;
    }
    // In your Board class
    public String getWordFormedByTiles(List<Tile> tiles) {
    // if (tiles.isEmpty()) return "";

    // // Sort tiles based on position
    // tiles.sort(Comparator.comparing(Tile::getX).thenComparing(Tile::getY));
    // Tile firstTile = tiles.get(0);
    // Tile lastTile = tiles.get(tiles.size() - 1);

    // StringBuilder word = new StringBuilder();
    // if (firstTile.getY() == lastTile.getY()) { // Horizontal word
    //     for (int x = firstTile.getX(); x <= lastTile.getX(); x++) {
    //         word.append(getTiles()[x][firstTile.getY()].getLetter());
    //     }
    // } else { // Vertical word
    //     for (int y = firstTile.getY(); y <= lastTile.getY(); y++) {
    //         word.append(getTiles()[firstTile.getX()][y].getLetter());
    //     }
    // }
    // return word.toString();
        if (tiles.isEmpty()) return "";

        // Sort tiles by position
        tiles.sort(Comparator.comparingInt(Tile::getX).thenComparingInt(Tile::getY));
        StringBuilder word = new StringBuilder();
        for (Tile tile : tiles) {
            word.append(tile.getLetter());
        }
        return word.toString();
    }

    // public List<String> getAllFormedWords(List<Tile> combinedList) {
    //     List<String> formedWords = new ArrayList<>();

    //     for (Tile tile : combinedList) {
    //     // Check horizontal and vertical words
    //         String horizontalWord = getWordFromPosition(tile.getX(), tile.getY(), true, this);
    //         String verticalWord = getWordFromPosition(tile.getX(), tile.getY(), false, this);

    //         if (!horizontalWord.isEmpty()) {
    //             System.out.println(horizontalWord);
    //             formedWords.add(horizontalWord);
    //         }
    //         if (!verticalWord.isEmpty()) {
    //             System.out.println(verticalWord);
    //             formedWords.add(verticalWord);
    //         }
    //     }
    //     return formedWords.stream().distinct().collect(Collectors.toList());
    // }
    public List<String> getIntersectingWords(Tile tile) {
        List<String> intersectingWords = new ArrayList<>();
    // Check horizontally and vertically for intersecting words
    // Assuming board.getWordAt() constructs a word starting at the given position
        intersectingWords.add(getWordAt(tile.getX(), tile.getY(), true));
        intersectingWords.add(getWordAt(tile.getX(), tile.getY(), false));
        return intersectingWords.stream().filter(word -> !word.isEmpty()).collect(Collectors.toList());
    }
    private String getWordAt(int row,int column, boolean horizontal) {
        // StringBuilder word = new StringBuilder();
        // int startX = horizontal ? findStartOfWord(tile.getX(), tile.getY(), true) : tile.getX();
        // int startY = horizontal ? tile.getY() : findStartOfWord(tile.getY(), tile.getX(), false);
    
        // for (int i = (horizontal ? startX : startY); i < (horizontal ? Board.BOARD_SIZE : Board.BOARD_SIZE); i++) {
        //     Tile currentTile = horizontal ? getTiles()[i][tile.getY()] : getTiles()[tile.getX()][i];
        //     if (currentTile == null || currentTile.getLetter() == '\0') {
        //         break;
        //     }
        //     word.append(currentTile.getLetter());
        // }
        // return word.toString();
        StringBuilder word = new StringBuilder();

        // Starting position
        int startPos = horizontal ? column : row;
        
        // Find the beginning of the word
        while (startPos > 0) {
            Tile checkTile = horizontal ? getTileAt(row, startPos - 1) : getTileAt(startPos - 1, column);
            if (checkTile == null || checkTile.getLetter() == '\0') {
                break; // Found the beginning of the word
            }
            startPos--;
        }

        // Construct the word from the starting position
        Tile currentTile;
        do {
            currentTile = horizontal ? getTileAt(row, startPos) : getTileAt(startPos, column);
            if (currentTile != null && currentTile.getLetter() != '\0') {
                word.append(currentTile.getLetter());
                startPos++;
            }
        } while (currentTile != null && currentTile.getLetter() != '\0');

        return word.toString();
    }
    public Tile getTileAt(int row, int column) {
        if (row >= 0 && row < BOARD_SIZE && column >= 0 && column < BOARD_SIZE) {
            return tiles[row][column];
        } else {
            return null; // or throw an exception if accessing out-of-bounds is an error
        }
    }
    // private String getWordFromPosition(int x, int y, boolean horizontal, Board board) {
    //     StringBuilder word = new StringBuilder();
    //     int startX = horizontal ? findStartOfWord(x, y, true, board) : x;
    //     int startY = horizontal ? y : findStartOfWord(y, x, false, board);

    //     for (int i = horizontal ? startX : startY; i < (horizontal ? Board.BOARD_SIZE : Board.BOARD_SIZE); i++) {
    //         Tile tile = horizontal ? board.getTile(y, i) : board.getTile(i, x);
    //         if (tile == null || tile.getLetter() == '\0') {
    //             break;
    //         }
    //         word.append(tile.getLetter());
    //     }
    //     return word.toString();
    // }
    private void setupTargetCell(StackPane cell) {
        // Allow for dragging over the cell
        cell.setOnDragOver(event -> {
            if (event.getGestureSource() != cell && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
    
        // Handle the dropping of the tile onto the cell
        cell.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                StackPane letterCell=new StackPane();
                letterCell.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
                Text droppedLetter = new Text(db.getString());
                droppedLetter.setStroke(Color.BLACK);
                letterCell.getChildren().add(droppedLetter);
                cell.getChildren().add(letterCell);
                success = true;
                tempTilesList.add(new Tile(db.getString().charAt(0),GridPane.getRowIndex(cell),GridPane.getColumnIndex(cell)));
                tiles[GridPane.getRowIndex(cell)][GridPane.getColumnIndex(cell)].setLetter(db.getString().charAt(0));
            }
            event.setDropCompleted(success);
            event.consume();
        });
        cell.setOnDragOver(event -> {
            if (event.getGestureSource() != cell && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
    }
    public List<Tile> getCurrentTurnTiles() {
        // Assuming 'currentTurnTiles' is a List<Tile> that's updated during gameplay
        return new ArrayList<>(currentTurnTiles);
    }
    public List<Tile> getTempTilesList() {
        // Assuming 'currentTurnTiles' is a List<Tile> that's updated during gameplay
        return new ArrayList<>(tempTilesList);
    }
    public void clearCurrentTurnTiles(){
        tempTilesList.clear();
    }
    public Tile[][] getTiles()
    {
        return tiles;
    }
    public void updateCurrentTurnTiles(List<Tile> combinedList){
        currentTurnTiles=combinedList.stream().map(obj->{try {
            return obj.clone(); // Deep copy for list2
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }}).collect(Collectors.toList());
    }
    

    // public boolean areTilesContiguous(List<Tile> tiles) {
    //     if (tiles.size() <= 1) {
    //         return true; // Single tile or empty is trivially contiguous
    //     }

    //     // Sort by x and y to check for continuity in both directions
    //     List<Tile> sortedByX = new ArrayList<>(tiles);
    //     sortedByX.sort(Comparator.comparingInt(Tile::getX));

    //     List<Tile> sortedByY = new ArrayList<>(tiles);
    //     sortedByY.sort(Comparator.comparingInt(Tile::getY));

    //     // Check if they are contiguous in either x or y direction
    //     boolean contiguousInX = isContiguousInOneDirection(sortedByX, true);
    //     boolean contiguousInY = isContiguousInOneDirection(sortedByY, false);

    //     return contiguousInX || contiguousInY;
    // }

    public void placeWord(String word, int startX, int startY, boolean horizontal) {
        int row = startX, column = startY;
        for (char letter : word.toCharArray()) {
            tiles[row][column].setLetter(letter);
            if (horizontal) column++;
            else row++;
        }
    }
    public void addTile(Tile tile)
    {
        tempTilesList.add(tile);
    }
    public Tile getTile(int row, int column) {
        return tiles[row][column];
    }

    // Add methods for handling tile swapping, checking for valid words, etc.
}
