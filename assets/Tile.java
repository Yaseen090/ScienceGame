package assets;

import java.io.Serializable;

public class Tile implements Cloneable,Serializable {
    private char letter;
    private int score;
    private int x;
    private int y;
    private boolean isDoubleWord;
    private boolean isTripleLetter;
    private boolean isDoubleLetter;
    private boolean isTripleWord;
    private static final long serialVersionUID = 1L;

    public Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    public Tile(char letter, int x, int y) {
        this.letter = letter;
        this.x = x;
        this.y=y;
    }

    public Tile(char letter, int x, int y,int score) {
        this.letter = letter;
        this.x = x;
        this.y=y;
        this.score=score;
    }

    public Tile(char letter, int score, boolean isDoubleWord, boolean isTripleLetter) {
        this.letter = letter;
        this.score = score;
        this.isDoubleWord = isDoubleWord;
        this.isTripleLetter = isTripleLetter;
    }
    public Tile clone() throws CloneNotSupportedException {
        return (Tile)super.clone();
    }
    // Getters and setters
    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
    
    public boolean isDoubleWord() {
        return isDoubleWord;
    }

    public boolean isDoubleLetter() {
        return isDoubleLetter;
    }

    public void setDoubleWord(boolean doubleWord) {
        isDoubleWord = doubleWord;
    }

    public void setDoubleLetter(boolean doubleletter) {
        isDoubleLetter = doubleletter;
    }

    public boolean isTripleLetter() {
        return isTripleLetter;
    }

    public boolean isTripleWord() {
        return isTripleWord;

    }
    public void setTripleLetter(boolean tripleLetter) {
        isTripleLetter = tripleLetter;
    }
    public void setTripleWord(boolean tripleword) {
        isTripleLetter = tripleword;
    }
}
