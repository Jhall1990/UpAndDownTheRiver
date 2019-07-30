package example.jacob.upanddowntheriver;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

public class FinalizeSettings extends AppCompatActivity {
    Spinner dealerSpinner;
    Spinner handSizeSpinner;
    Spinner trumpModeSpinner;

    void populateDealerSpinner(PlayerCollection players) {
        ArrayAdapter<Player> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, players.getPlayers());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dealerSpinner.setAdapter(adapter);
    }

    void populateHandSizeSpinner(int maxHandSize) {
        String[] handSizes = new String[maxHandSize];

        for (int i = 1; i <= maxHandSize; i++) {
            handSizes[i - 1] = Integer.toString(i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, handSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        handSizeSpinner.setAdapter(adapter);
    }

    void populateTrumpModeSpinner() {
        ArrayList<String> trumpModes = new ArrayList<>();
        HashMap<Integer, String> trumpMap = Constants.TRUMP_MODES;

        for (int i = 1; i <= trumpMap.size(); i++) {
            trumpModes.add(Constants.TRUMP_MODES.get(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, trumpModes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trumpModeSpinner.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalize_settings);

        dealerSpinner = findViewById(R.id.dealerSpinner);
        handSizeSpinner = findViewById(R.id.handSizeSpinner);
        trumpModeSpinner = findViewById(R.id.trumpModeSpinner);

        Intent intent = getIntent();
        Bundle playerData = intent.getExtras();
        PlayerCollection players = (PlayerCollection) playerData.getSerializable("players");

        // Create a game object.
        Game game = new Game(players);

        // Populate the three spinners.
        populateDealerSpinner(players);
        populateHandSizeSpinner(game.getMaxHandSize());
        populateTrumpModeSpinner();
    }
}
