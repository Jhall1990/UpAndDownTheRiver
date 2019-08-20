package example.jacob.upanddowntheriver;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game implements java.io.Serializable {
    private PlayerCollection players;
    private Player dealer;
    private int numPlayers;
    private int trumpMode;
    private int startingHandSize;
    private int maxHandSize;
    private int rounds;
    private int[] whatsTrump;
    private boolean goingUp;
    private ArrayList<Integer> classicTrumpOrder;

    Game(PlayerCollection players) {
        this.players = players;
        this.numPlayers = players.size();
    }

    public int getMaxHandSize() {
        return (Constants.DECK_SIZE * GameSettings.numDecks) / numPlayers;
    }

    public PlayerCollection getPlayers() {
        return this.players;
    }

    public Player getPlayer(int index) {
        return this.players.getPlayer(index);
    }

    public int getRounds() {
        return this.rounds;
    }

    /*
    Setters...
     */
    public void setStartingHandSize(int handSize) {
        this.startingHandSize = handSize;
    }

    public void setMaxHandSize(int handSize ) {
        this.maxHandSize = handSize;
        this.rounds = handSize;
    }

    public void setTrumpMode(int trumpMode) {
        this.trumpMode = trumpMode;

        if (this.trumpMode == Constants.TRUMP_MODE_CLASSIC) {
            classicTrumpOrder = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                classicTrumpOrder.add(i);
            }

            Collections.shuffle(classicTrumpOrder);
        }
    }

    public void setGoingUp(boolean goingUp) {
        this.goingUp = goingUp;
    }

    void setDealer(Player dealer) {
        this.dealer = dealer;
    }

    /*
    This gets the next trump suit in classic trump mode. It removes
    the first item in the shuffled list then adds it to the end. It
    returns the initially removed item.
     */
    public int getNextClassicTrump() {
        int nextTrump = classicTrumpOrder.remove(0);
        classicTrumpOrder.add(nextTrump);
        return nextTrump;
    }

    public int getRandomTrump(int[] excludes) {
        Random rand = new Random();
        return rand.nextInt(5);
    }
}
