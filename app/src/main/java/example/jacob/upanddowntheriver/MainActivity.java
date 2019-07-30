package example.jacob.upanddowntheriver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    public void loadNewGameActivity(android.view.View view) {
        Log.i("btn", "New Game button pressed");

        Intent intent = new Intent(this, SelectPlayers.class);
        startActivity(intent);
    }

    public void loadLoadGameActivity(android.view.View view) {
        Log.i("btn", "Load Game button pressed");
    }

    public void loadPlayersActivity(android.view.View view) {
        Log.i("btn", "Players button pressed");

        Intent intent = new Intent(this, Players.class);
        startActivity(intent);
    }

    public void loadSettingsActivity(android.view.View view) {
        Log.i("btn", "GameSettings button pressed");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
