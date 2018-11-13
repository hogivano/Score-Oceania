package model;

import java.io.Serializable;

/**
 * Created by root on 25/03/18.
 */

public class Wave implements Serializable {
    double min;
    double self;
    double score;

    public Wave(){

    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setSelf(double self) {
        this.self = self;
    }

    public double getMin() {
        return min;
    }

    public double getScore() {
        return score;
    }

    public double getSelf() {
        return self;
    }
}
