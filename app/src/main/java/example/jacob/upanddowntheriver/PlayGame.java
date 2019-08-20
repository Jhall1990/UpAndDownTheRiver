package example.jacob.upanddowntheriver;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PlayGame extends AppCompatActivity {
    private int dpToInt(int dp) {
        // Converts dp into its equivalent px
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void createTextView(String text, TableRow row) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextAppearance(this, R.style.TextAppearance_AppCompat_Medium);
        textView.setTextColor(Color.parseColor("#000000"));
        row.addView(textView);
    }

    private void addHorizontalDivider(TableLayout table) {
        View v = new View(this);
        v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, dpToInt(1)));
        v.setBackgroundColor(Color.rgb(0, 0, 0));

        table.addView(v);
    }

    private void addVerticalDivider(TableRow row) {
        View v = new View(this);
        v.setLayoutParams(new TableRow.LayoutParams(dpToInt(1), TableRow.LayoutParams.FILL_PARENT));
        v.setBackgroundColor(Color.rgb(0, 0, 0));

        row.addView(v);
    }

    private void createPlayerRow(Player p, int rounds, TableLayout table) {
        int dp16 = dpToInt(16);

        TableRow row = new TableRow(this);
        row.setPadding(dp16, dp16, dp16, dp16);
        createTextView(p.getName(), row);

        for (int i = 0; i < rounds; i++) {
            addVerticalDivider(row);
            createTextView("0", row);
        }

        table.addView(row);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        // Get the game data from the intent.
        Intent intent = getIntent();
        Bundle playerData = intent.getExtras();
        Game game = (Game) playerData.getSerializable("game");

        // Create a table
        PlayerCollection players = game.getPlayers();
        TableLayout table = findViewById(R.id.scoreboardTable);
        addHorizontalDivider(table);

        for (int i = 0; i < players.size(); i++) {
            Player p = players.getPlayer(i);
            createPlayerRow(p, game.getRounds(), table);
            addHorizontalDivider(table);
        }

        // Create a text view for each player.
//        Player firstPlayer = game.getPlayer(0);
//        TextView playerOne = new TextView(this);
//        playerOne.setText(firstPlayer.getName());
//        playerOne.setId(ViewCompat.generateViewId());
//        playerOne.setTextAppearance(this, R.style.TextAppearance_AppCompat_Medium);
//
//        Player secondPlayer = game.getPlayer(1);
//        TextView playerTwo = new TextView(this);
//        playerTwo.setText(secondPlayer.getName());
//        playerTwo.setId(ViewCompat.generateViewId());
//        playerTwo.setTextAppearance(this, R.style.TextAppearance_AppCompat_Medium);
//
//        Player thirdPlayer = game.getPlayer(2);
//        TextView playerThree = new TextView(this);
//        playerThree.setText(thirdPlayer.getName());
//        playerThree.setId(ViewCompat.generateViewId());
//        playerThree.setTextAppearance(this, R.style.TextAppearance_AppCompat_Medium);
//
//        ConstraintLayout layout = findViewById(R.id.scoreboardLayout);
//        layout.addView(playerOne);
//        layout.addView(playerTwo);
//        layout.addView(playerThree);
//
//        ConstraintSet con = new ConstraintSet();
//        con.clone(layout);
//        con.connect(playerOne.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
//        con.connect(playerOne.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
//
//        con.connect(playerTwo.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
//        con.connect(playerTwo.getId(), ConstraintSet.TOP, playerOne.getId(), ConstraintSet.BOTTOM, 16);
//
//        con.connect(playerThree.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
//        con.connect(playerThree.getId(), ConstraintSet.TOP, playerTwo.getId(), ConstraintSet.BOTTOM, 16);
//
//        con.applyTo(layout);
    }
}
