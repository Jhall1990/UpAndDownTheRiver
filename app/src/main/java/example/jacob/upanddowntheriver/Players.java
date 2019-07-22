package example.jacob.upanddowntheriver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Players extends AppCompatActivity {
    public void createPlayersFile() {
        try {
            String data = "hello world";

            FileOutputStream playerFile = openFileOutput(Constants.PLAYERS_FILE, MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(playerFile);
            osw.write(data);
            osw.flush();
            osw.close();
        } catch (FileNotFoundException e) {
            Log.e("impossibleError", "This really shouldn't happen");
        } catch (IOException e) {
            Log.e("impossibleError", "This really shouldn't happen");
        }
    }

    public ArrayList<Player> getPlayersFromFile() {
        File playersFile = new File(getFilesDir(), Constants.PLAYERS_FILE);

        if (!playersFile.exists()) {
            createPlayersFile();
        } else {
            try {
                InputStream playerStream = openFileInput(Constants.PLAYERS_FILE);
                InputStreamReader inputStreamReader = new InputStreamReader(playerStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String data;
                StringBuilder stringBuilder = new StringBuilder();

                while ( (data = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(data);
                }

                playerStream.close();
                String playerData = stringBuilder.toString();
                Log.i("playerData", playerData);
            } catch (FileNotFoundException e) {
                Log.e("impossibleError", "This really shouldn't happen");
            } catch (IOException e) {
                Log.e("impossibleError", "This really shouldn't happen");
            }
        }

        return new ArrayList<>();
    }

    public void populatePlayerList() {
        // Todo: Convert this into a recycler view at some point.

        // Get the player list ListView.
        ListView playerList = findViewById(R.id.playerListView);

        // Create the ArrayList with the existing players.
        ArrayList<Player> players = getPlayersFromFile();

        // Create the ArrayAdapter.
        int layout = android.R.layout.simple_list_item_1;
        ArrayAdapter<Player> playerAdapter = new ArrayAdapter<>(this, layout, players);

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
