package example.jacob.upanddowntheriver;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class AddEditPlayer extends AppCompatActivity {
    public void savePlayer(android.view.View view) {
        // Get the edit texts for the two player attributes.
        // Use these later to fill in the intent data.
        EditText nameText = findViewById(R.id.nameTextField);
        EditText nickNameText = findViewById(R.id.nickNameTextField);

        // Create the intent object add name and nickName extra.
        Intent playerData = new Intent();
        playerData.putExtra("name", nameText.getText().toString());
        playerData.putExtra("nickName", nickNameText.getText().toString());

        // Set the activities result and finish.
        setResult(Activity.RESULT_OK, playerData);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_player);
    }
}
