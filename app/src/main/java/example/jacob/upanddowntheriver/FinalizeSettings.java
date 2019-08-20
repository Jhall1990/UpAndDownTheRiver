package example.jacob.upanddowntheriver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

public class FinalizeSettings extends AppCompatActivity {
    ArrayList<Integer> maxHandSizes;
    ArrayAdapter<Integer> maxHandSizeAdapter;
    Spinner dealerSpinner;
    Spinner startingHandSizeSpinner;
    Spinner maxHandSizeSpinner;
    Spinner trumpModeSpinner;
    RadioGroup upDownRadio;
    Button finishButton;
    Game game;

    public void startGame(android.view.View view) {
        // Get the data from the page and update the game object.
        Player dealer = (Player) dealerSpinner.getSelectedItem();
        int startingHandSize = Integer.parseInt(startingHandSizeSpinner.getSelectedItem().toString());
        int maxHandSize = Integer.parseInt(maxHandSizeSpinner.getSelectedItem().toString());
        int trumpMode = getTrumpMode();
        boolean up = getUpOrDown();

        // Update the game object.
        game.setDealer(dealer);
        game.setStartingHandSize(startingHandSize);
        game.setMaxHandSize(maxHandSize);
        game.setTrumpMode(trumpMode);
        game.setGoingUp(up);

        // Serialize the game object, create a bundle, and add it to the intent.
        Intent intent = new Intent(this, PlayGame.class);
        Bundle gameBundle = new Bundle();
        gameBundle.putSerializable("game", game);
        intent.putExtras(gameBundle);
        startActivityForResult(intent, 1);
    }

    /*
    Gets the selected radio button returns True if Up is selected
    otherwise False is returned.
     */
    boolean getUpOrDown() {
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

    void populateStartingHandSizeSpinner(int handSize) {
        String[] handSizes = new String[handSize];

        for (int i = 1; i <= handSize; i++) {
            handSizes[i - 1] = Integer.toString(i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, handSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startingHandSizeSpinner.setAdapter(adapter);
    }

    void populateMaxHandSizeSpinner() {
        int startingHandSize = Integer.parseInt(startingHandSizeSpinner.getSelectedItem().toString());

        if (maxHandSizes == null) {
            maxHandSizes = new ArrayList<>();
        }

        // Remove all the entries from the array list.
        maxHandSizes.clear();

        // Generate new data for the max hand size array.
        for (int i = startingHandSize; i <= game.getMaxHandSize(); i++) {
            maxHandSizes.add(i);
        }

        if (maxHandSizeAdapter == null) {
            maxHandSizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, maxHandSizes);
            maxHandSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            maxHandSizeSpinner.setAdapter(maxHandSizeAdapter);
        } else {
            maxHandSizeAdapter.notifyDataSetChanged();
        }
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

        // Get the finish button and disable it.
        finishButton = findViewById(R.id.buttonStart);
        finishButton.setEnabled(false);

        // Get all the spinners and radio group.
        dealerSpinner = findViewById(R.id.dealerSpinner);
        startingHandSizeSpinner = findViewById(R.id.handSizeSpinner);
        maxHandSizeSpinner = findViewById(R.id.maxHandSizeSpinner);
        trumpModeSpinner = findViewById(R.id.trumpModeSpinner);
        upDownRadio = findViewById(R.id.upOrDownRadio);

        // Get the player data from the intent.
        Intent intent = getIntent();
        Bundle playerData = intent.getExtras();
        PlayerCollection players = (PlayerCollection) playerData.getSerializable("players");

        // Create a game object.
        game = new Game(players);

        // Populate the three spinners.
        populateDealerSpinner(players);
        populateStartingHandSizeSpinner(game.getMaxHandSize());
        populateMaxHandSizeSpinner();
        populateTrumpModeSpinner();

        // Create the on click listener for the select hand size spinner.
        startingHandSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Update the max hand size spinner so it's never less than the
                // starting hand size.
                populateMaxHandSizeSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Create the on click listener for the radio group, just need this
        // to enable the finish button after one of the options was selected.
        upDownRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (!finishButton.isEnabled()) {
                    finishButton.setEnabled(true);
                }
            }
        });

    }
}
