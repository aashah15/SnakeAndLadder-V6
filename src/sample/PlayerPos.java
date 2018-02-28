package sample;

public class PlayerPos {
    private int playerXPos;
    private int playerYPos;
    private int playerPos;

    public PlayerPos(int playerXPos, int playerYPos, int playerPos) {
        this.playerXPos = playerXPos;
        this.playerYPos = playerYPos;
        this.playerPos = playerPos;
    }

    public int getPlayerPos() {
        return playerPos;
    }

    public int getPlayerXPos() {

        return playerXPos;
    }

    public void setPlayerXPos(int playerXPos) {
        this.playerXPos = playerXPos;
    }

    public int getPlayerYPos() {
        return playerYPos;
    }

    public void setPlayerYPos(int playerYPos) {
        this.playerYPos = playerYPos;
    }
}
