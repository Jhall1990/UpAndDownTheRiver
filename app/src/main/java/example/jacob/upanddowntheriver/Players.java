package example.jacob.upanddowntheriver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Players extends AppCompatActivity {
    public void getPlayersFromFile(PlayerCollection players) {
        try {
            // All this to read the players json file.
            FileInputStream playersFile = openFileInput(Constants.PLAYERS_FILE);
            InputStreamReader isr = new InputStreamReader(playersFile);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder playerJson = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                playerJson.append(line);
            }

            // Create a json object from the text read out of the players file.
            JSONObject playerData = new JSONObject(playerJson.toString());

            // Create a JSON array object from the "players" object in the players JSON.
            JSONArray playerArray = (JSONArray) playerData.get("players");

            // Iterate over each entry in the playerArray, create a player
            // and add it to the player collection.
            for (int i = 0; i < playerArray.length(); i++) {
                Player p = new Player(playerArray.getJSONObject(i));
                players.addPlayer(p);
            }
        } catch (JSONException e) {
            Log.i("jsonError", "Couldn't read players json");
        } catch (FileNotFoundException e) {
            Log.i("fileNotFound", "Could not find players file");
        } catch (IOException e) {
            Log.i("ioError", "Could not read players file");
        }
    }

    public void populatePlayerList() {
        // Todo: Convert this into a recycler view at some point.

        // Get the player list ListView.
        ListView playerList = findViewById(R.id.playerListView);

        // Create a player collection then add players to it.
        PlayerCollection players = new PlayerCollection();
        getPlayersFromFile(players);

        // Create the ArrayAdapter.
        int layout = android.R.layout.simple_list_item_1;
        ArrayAdapter<Player> playerAdapter = new ArrayAdapter<>(this, layout,
                                                                players.getPlayers());

        // Update the playerList with the array adapter.
        playerList.setAdapter(playerAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        populatePlayerList();
    }
}
