package model;

import java.io.Serializable;

/**
 * Created by root on 24/03/18.
 */

public class KriteriaPresentation implements Serializable{
    double pu;
    double fpd;
    double tc;
    double aa;

    public KriteriaPresentation(){
        pu = 0;
        fpd = 0;
        tc = 0;
        aa = 0;
    }

    public void setPu(double pu) {
        this.pu = pu;
    }

    public void setFpd(double fpd) {
        this.fpd = fpd;
    }

    public void setTc(double tc) {
        this.tc = tc;
    }

    public void setAa(double aa) {
        this.aa = aa;
    }

    public double getPu() {
        return pu;
    }

    public double getFpd() {
        return fpd;
    }

    public double getTc() {
        return tc;
    }

    public double getAa() {
        return aa;
    }
}
