package model;

import java.io.Serializable;

/**
 * Created by root on 25/03/18.
 */

public class KriteriaInovation implements Serializable{
    double strI;
    double staI;
    double fe;
    double oi;

    public KriteriaInovation(){

    }

    public double getOi() {
        return oi;
    }

    public void setOi(double oi) {
        this.oi = oi;
    }

    public double getFe() {
        return fe;
    }

    public double getStaI() {
        return staI;
    }

    public double getStrI() {
        return strI;
    }

    public void setFe(double fe) {
        this.fe = fe;
    }

    public void setStaI(double staI) {
        this.staI = staI;
    }

    public void setStrI(double strI) {
        this.strI = strI;
    }
}
