package model;

import android.os.Parcel;

import java.io.Serializable;

/**
 * Created by root on 24/03/18.
 */

public class Team implements Serializable{
    String nama;
    String universitas;
    String key;

    public Team(){}

    public void setKey(String key) {
        this.key = key;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setUniversitas(String universitas) {
        this.universitas = universitas;
    }

    public String getKey() {
        return key;
    }

    public String getNama() {
        return nama;
    }

    public String getUniversitas() {
        return universitas;
    }

}
