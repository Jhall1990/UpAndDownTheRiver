package example.jacob.upanddowntheriver;

import java.util.HashMap;

class Constants {
    static final String PLAYERS_FILE = "players.yaml";
    static final int ADD_PLAYER = 1;
    static final int EDIT_PLAYER = 2;
    static final int IMPORT_PLAYERS = 3;
    static final int DECK_SIZE = 52;

    // Trump modes
    static HashMap<Integer, String> TRUMP_MODES = createTrumpModeMap();
    static final int TRUMP_MODE_CLASSIC = 1;
    static final int TRUMP_MODE_RANDOM = 2;
    static final int TRUMP_MODE_USER_CHOICE = 3;

    private static HashMap<Integer, String> createTrumpModeMap() {
        HashMap<Integer, String> trumpModes = new HashMap<>();
        trumpModes.put(TRUMP_MODE_CLASSIC, "Classic");
        trumpModes.put(TRUMP_MODE_RANDOM, "Random");
        trumpModes.put(TRUMP_MODE_USER_CHOICE, "User Choice");
        return trumpModes;
    }
}
