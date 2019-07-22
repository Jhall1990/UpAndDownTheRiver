package example.jacob.upanddowntheriver;

import android.support.annotation.NonNull;

public class Player {
    private String name;
    private String nickName;
    private int score;

    Player(String name) {
        this.name = name;
        this.nickName = name;
        this.score = 0;
    }

    Player(String name, String nickName) {
        this.name = name;
        this.nickName = nickName;
        this.score = 0;
    }

    Player(String name, int score) {
        this.name = name;
        this.nickName = name;
        this.score = score;
    }

    Player(String name, String nickName, int score) {
        this.name = name;
        this.nickName = nickName;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public int getScore() {
        return score;
    }

    @NonNull
    public String toString() {
        return name;
    }
}
