package model;

import java.io.Serializable;

/**
 * Created by root on 25/03/18.
 */

public class ScoreTeam implements Serializable {
    String team;
    String universitas;
    String key;
    FinalScore finalScore;

    public ScoreTeam(){
        finalScore = new FinalScore();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getUniversitas() {
        return universitas;
    }

    public void setUniversitas(String universitas) {
        this.universitas = universitas;
    }

    public FinalScore getFinalScore() {
        return finalScore;
    }

    public String getTeam() {
        return team;
    }

    public void setFinalScore(FinalScore finalScore) {
        this.finalScore = finalScore;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
