package example.jacob.upanddowntheriver;


public class Game {
    private PlayerCollection players;
    private int numPlayers;
    private int trumpMode;
    private int handSize;
    private boolean goingUp;

    Game(PlayerCollection players) {
        this.players = players;
        this.numPlayers = players.size();
    }

    public int getMaxHandSize() {
        return (Constants.DECK_SIZE * GameSettings.numDecks) / numPlayers;
    }

    public void setHandSize(int handSize) {
        this.handSize = handSize;
    }

    public void setTrumpMode(int trumpMode) {
        this.trumpMode = trumpMode;
    }

    public void setGoingUp(boolean goingUp) {
        this.goingUp = goingUp;
    }
}
