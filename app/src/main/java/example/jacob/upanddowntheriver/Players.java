package example.jacob.upanddowntheriver;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

    private PlayerCollection players;
    private ArrayAdapter<Player> playerAdapter;

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

    public void openAddEditPlayers(android.view.View view) {
        Log.i("btn", "Add player button pressed");

        Intent intent = new Intent(this, AddEditPlayer.class);
        intent.putExtra("requestCode", Constants.ADD_PLAYER);
        startActivityForResult(intent, Constants.ADD_PLAYER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra("name");
            String nickName = data.getStringExtra("nickName");

            if (requestCode == Constants.ADD_PLAYER) {
                Player p = new Player(name, nickName);
                players.addPlayer(p);
            } else if (requestCode == Constants.EDIT_PLAYER) {
                 // We have to create a new player here because for some reason
                 // on edit the player selected is removed from the array list.
                 // Not sure why android is doing that.
                int index = data.getIntExtra("index", -1);

                Player p = players.getPlayer(index);
                p.setName(name);
                p.setNickName(name);
            }
            playerAdapter.notifyDataSetChanged();
            players.savePlayers(this);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.playerListView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.player_edit_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        int itemId = item.getItemId();

        if (itemId == R.id.edit) {
            Intent intent = new Intent(this, AddEditPlayer.class);
            Player player = players.getPlayer(index);
            intent.putExtra("name", player.getName());
            intent.putExtra("nickName", player.getNickName());
            intent.putExtra("requestCode", Constants.EDIT_PLAYER);
            intent.putExtra("index", index);
            startActivityForResult(intent, Constants.EDIT_PLAYER);
        } else if (itemId == R.id.delete) {
            players.removePlayer(index);
            playerAdapter.notifyDataSetChanged();
            players.savePlayers(this);
        } else {
            return super.onContextItemSelected(item);
        }
        return true;
    }

    public void populatePlayerList() {
        // Todo: Convert this into a recycler view at some point.

        // Get the player list ListView.
        ListView playerList = findViewById(R.id.playerListView);

        // Create a player collection then add players to it.
        getPlayersFromFile(players);

        // Create the ArrayAdapter.
        int layout = android.R.layout.simple_list_item_1;
        playerAdapter = new ArrayAdapter<>(this, layout, players.getPlayers());

        // Update the playerList with the array adapter.
        playerList.setAdapter(playerAdapter);

        // Register a context manager for the list view.
        registerForContextMenu(playerList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        players = new PlayerCollection();
        populatePlayerList();
    }
}
