package example.jacob.upanddowntheriver;


public class Game implements java.io.Serializable {
    private PlayerCollection players;
    private Player dealer;
    private int numPlayers;
    private int trumpMode;
    private int handSize;
    private int repeats;
    private boolean goingUp;

    Game(PlayerCollection players) {
        this.players = players;
        this.numPlayers = players.size();
    }

    public int getMaxHandSize() {
        return (Constants.DECK_SIZE * GameSettings.numDecks) / numPlayers;
    }

    /*
    Setters...
     */
    public void setHandSize(int handSize) {
        this.handSize = handSize;
    }

    public void setTrumpMode(int trumpMode) {
        this.trumpMode = trumpMode;
    }

    public void setGoingUp(boolean goingUp) {
        this.goingUp = goingUp;
    }

    void setDealer(Player dealer) {
        this.dealer = dealer;
    }

    void setRepeats(int repeats) {
        this.repeats = repeats;
    }
}
