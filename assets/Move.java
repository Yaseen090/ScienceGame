package assets;

public class Move {
    private int startX;
    private int startY;
    private String word;
    private boolean isHorizontal;

    // Constructor
    public Move(int startX, int startY, String word, boolean isHorizontal) {
        this.startX = startX;
        this.startY = startY;
        this.word = word;
        this.isHorizontal = isHorizontal;
    }

    // Default Constructor
    public Move() {
        // Optionally initialize default values
    }

    // Getters
    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public String getWord() {
        return word;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    // Setters
    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setHorizontal(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }
}
