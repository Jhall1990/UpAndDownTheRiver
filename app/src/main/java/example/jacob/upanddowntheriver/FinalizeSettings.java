package example.jacob.upanddowntheriver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class FinalizeSettings extends AppCompatActivity {
    Spinner dealerSpinner;
    Spinner handSizeSpinner;
    Spinner trumpModeSpinner;
    Game game;

    public void startGame(android.view.View view) {
        // Get the repeat text box view.
        TextView repeatTextView = findViewById(R.id.numberOfRepeatsTextBox);

        // Get the data from the page and update the game object.
        Player dealer = (Player) dealerSpinner.getSelectedItem();
        int handSize = Integer.parseInt(handSizeSpinner.getSelectedItem().toString());
        int trumpMode = getTrumpMode();
        int repeats = Integer.parseInt(repeatTextView.getText().toString());
        boolean up = getUpOrDown();

        // Update the game object.
        game.setDealer(dealer);
        game.setHandSize(handSize);
        game.setTrumpMode(trumpMode);
        game.setRepeats(repeats);
        game.setGoingUp(up);

        // Serialize the game object, create a bundle, and add it to the intent.

    }

    /*
    Gets the selected radio button returns True if Up is selected
    otherwise False is returned.
     */
    boolean getUpOrDown() {
        RadioGroup upDownRadio = findViewById(R.id.upOrDownRadio);
        int selectedRadio = upDownRadio.getCheckedRadioButtonId();
        return selectedRadio == R.id.upRadio;
    }

    int getTrumpMode() {
        String trumpString = trumpModeSpinner.getSelectedItem().toString();

        if (trumpString.equals(Constants.TRUMP_MODES.get(Constants.TRUMP_MODE_CLASSIC))) {
            return Constants.TRUMP_MODE_CLASSIC;
        } else if (trumpString.equals(Constants.TRUMP_MODES.get(Constants.TRUMP_MODE_RANDOM))) {
            return Constants.TRUMP_MODE_RANDOM;
        } else {
            return Constants.TRUMP_MODE_USER_CHOICE;
        }
    }

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

        // Get the player data from the intent.
        Intent intent = getIntent();
        Bundle playerData = intent.getExtras();
        PlayerCollection players = (PlayerCollection) playerData.getSerializable("players");

        // Create a game object.
        game = new Game(players);

        // Populate the three spinners.
        populateDealerSpinner(players);
        populateHandSizeSpinner(game.getMaxHandSize());
        populateTrumpModeSpinner();
    }
}
