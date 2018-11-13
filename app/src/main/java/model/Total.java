package model;

import java.io.Serializable;

/**
 * Created by root on 24/03/18.
 */

public class Total implements Serializable {
    double subTotal;
    double totalBobot;
    double total;

    public Total(){

    }

    public double getTotal() {
        return total;
    }

    public double getTotalBobot() {
        return totalBobot;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setTotalBobot(double totalBobot) {
        this.totalBobot = totalBobot;
    }
}
