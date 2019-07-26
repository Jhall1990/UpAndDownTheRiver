package example.jacob.upanddowntheriver;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

class PlayerCollection {
    private ArrayList<Player> players;

    PlayerCollection() {
        players = new ArrayList<>();
    }

    void addPlayer(Player p) {
        players.add(p);
    }

    void insertPlayer(Player p, int index) {
        players.add(index, p);
    }

    void removePlayer(int index) {
        players.remove(index);
    }

    ArrayList<Player> getPlayers() {
        return players;
    }

    Player getPlayer(int index) {
        return players.get(index);
    }

    /*
    Convert player collection to json.
     */
    void savePlayers(Context c) {
        JSONObject playersJson = createJson();
        writeJsonToFile(c, playersJson);
    }

    /*
    Create JSON from the players.
     */
    private JSONObject createJson() {
        JSONObject playersJson = new JSONObject();
        JSONArray playersJsonArray = new JSONArray();

        for (Player p : players) {
            playersJsonArray.put(p.writeJson());
        }

        try {
            playersJson.put("players", playersJsonArray);
        } catch (JSONException e) {
            Log.i("jsonError", "Could not create players json.");
        }

        return playersJson;
    }

    /*
    Writes the JSON to the players file.
     */
    private void writeJsonToFile(Context c, JSONObject playersJson) {
        try {
            FileOutputStream playerFile = c.openFileOutput(Constants.PLAYERS_FILE, MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(playerFile);
            osw.write(playersJson.toString());
            osw.close();
        } catch (FileNotFoundException e) {
            Log.i("fileNotFound", "Could not open players file.");
        } catch (IOException e) {
            Log.i("ioError", "Couldn't write to the players file>");
        }
    }
}
