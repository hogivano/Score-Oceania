package model;

import java.io.Serializable;

/**
 * Created by root on 25/03/18.
 */

public class FinalScore implements Serializable {
    double total;
    double presentation;
    double inovation;
    double platform;
    double wave;

    public FinalScore(){
        total = 0;
        presentation = 0;
        inovation = 0;
        platform = 0;
        wave = 0;
    }

    public double getTotal() {
        return total;
    }

    public double getInovation() {
        return inovation;
    }

    public double getPlatform() {
        return platform;
    }

    public double getPresentation() {
        return presentation;
    }

    public double getWave() {
        return wave;
    }

    public void setInovation(double inovation) {
        this.inovation = inovation;
    }

    public void setPlatform(double platform) {
        this.platform = platform;
    }

    public void setPresentation(double presentation) {
        this.presentation = presentation;
    }

    public void setWave(double wave) {
        this.wave = wave;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
