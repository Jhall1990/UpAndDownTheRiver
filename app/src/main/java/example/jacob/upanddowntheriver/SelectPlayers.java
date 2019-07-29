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
import android.widget.TextView;

import java.util.Locale;

public class SelectPlayers extends AppCompatActivity {

    private PlayerCollection availablePlayers;
    private PlayerCollection selectedPlayers;
    private ArrayAdapter<Player> playerAdapter;

    private void updateBanner() {
        // Get the banner text view.
        TextView banner = findViewById(R.id.selectPlayerText);

        // Get the number of players
        int numPlayers = selectedPlayers.size() + 1;

        // Create the banner string.
        String bannerText;

        if (numPlayers <= 2) {
            bannerText = String.format(Locale.getDefault(), "Select player %d", numPlayers);
        } else {
            bannerText = String.format(Locale.getDefault(), "Select player %d, or press Finish", numPlayers);
        }

        // Update the banner's text.
        banner.setText(bannerText);
    }

    /*
    Moves to the finalize settings activity.
     */
    public void startFinalizeSettings(android.view.View view) {
        Log.i("btn", "Finish button pressed");

        Intent intent = new Intent(this, FinalizeSettings.class);
        Bundle playerBundle = new Bundle();
        playerBundle.putSerializable("players", selectedPlayers);
        intent.putExtras(playerBundle);
        startActivity(intent);
    }

    /*
    Create an on click listener for the player list view. Move the selected player
    into the selected player collection and remove it from the available players
    collection.
    */
    private void addClickListener(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Player p = availablePlayers.removePlayer(position);
                selectedPlayers.addPlayer(p);
                playerAdapter.notifyDataSetChanged();
                updateBanner();
            }
        });
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
            if (requestCode == Constants.ADD_PLAYER) {
                availablePlayers.addPlayer(data);
            } else if (requestCode == Constants.EDIT_PLAYER) {
                availablePlayers.editPlayer(data);
            }
            playerAdapter.notifyDataSetChanged();
            availablePlayers.savePlayers(this);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.selectPlayerList) {
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
            Player player = availablePlayers.getPlayer(index);
            intent.putExtra("name", player.getName());
            intent.putExtra("nickName", player.getNickName());
            intent.putExtra("requestCode", Constants.EDIT_PLAYER);
            intent.putExtra("index", index);
            startActivityForResult(intent, Constants.EDIT_PLAYER);
        } else if (itemId == R.id.delete) {
            availablePlayers.removePlayer(index);
            playerAdapter.notifyDataSetChanged();
            availablePlayers.savePlayers(this);
        } else {
            return super.onContextItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_players);

        // Get the list view to add the players to.
        ListView playerList = findViewById(R.id.selectPlayerList);

        // Get the layout which is used to create an array adapter.
        int layout = android.R.layout.simple_list_item_1;

        // Create the players collection and have it populated with
        // the players in the saved json file.
        availablePlayers = new PlayerCollection(this, Constants.PLAYERS_FILE);
        playerAdapter = availablePlayers.createPlayerAdapter(this, playerList, layout);

        // Initialize the selected players collections.
        selectedPlayers = new PlayerCollection(this);

        // Register the list view with a context manager, this allows
        // the long press menu to work.
        registerForContextMenu(playerList);

        // Update the text banner.
        updateBanner();

        // Make the list view clickable.
        addClickListener(playerList);
    }
}
