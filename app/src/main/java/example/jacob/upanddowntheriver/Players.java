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

public class Players extends AppCompatActivity {

    private PlayerCollection players;
    private ArrayAdapter<Player> playerAdapter;

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
                players.addPlayer(data);
            } else if (requestCode == Constants.EDIT_PLAYER) {
                players.editPlayer(data);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        ListView playerList = findViewById(R.id.playerListView);
        int layout = android.R.layout.simple_list_item_1;
        players = new PlayerCollection(this, Constants.PLAYERS_FILE);
        playerAdapter = players.createPlayerAdapter(this, playerList, layout);
        registerForContextMenu(playerList);
    }
}
