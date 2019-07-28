package example.jacob.upanddowntheriver;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Player {
    private String name;
    private String nickName;
    private int score;

    /*
    Player constructors
     */
    Player(String name) {
        this.name = name;
        this.nickName = "";
        this.score = 0;
    }

    Player(String name, String nickName) {
        this.name = name;
        this.nickName = nickName;
        this.score = 0;
    }

    Player(String name, int score) {
        this.name = name;
        this.nickName = "";
        this.score = score;
    }

    Player(String name, String nickName, int score) {
        this.name = name;
        this.nickName = nickName;
        this.score = score;
    }

    Player(JSONObject player) {
        try {
            this.name = player.getString("name");
            this.nickName = player.getString("name");
            this.score = player.getInt("score");
        } catch (JSONException e) {
            Log.i("jsonError", "Could not create player from json");
        }
    }

    /*
    Getter/setter methods because fucking java
     */
    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getNickName() {
        return nickName;
    }

    void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getScore() {
        return score;
    }

    /*
    toString so the ListView works correctly.
     */
    @NonNull
    public String toString() {
        return name;
    }

    /*
    Convert a player object into json.
     */
    public JSONObject writeJson() {
        JSONObject jsonData = new JSONObject();

        try {
            jsonData.put("name", name);
            jsonData.put("nickName", nickName);
            jsonData.put("score", score);
        } catch (JSONException e) {
            Log.i("jsonError", "Could not convert to json.");
        }

        return jsonData;
    }
}
