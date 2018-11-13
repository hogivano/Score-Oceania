package com.trydev.scoreoceania;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import model.FinalScore;
import model.Total;
import model.Wave;
import util.ConnectionDetector;

public class WaveTestActivity extends AppCompatActivity implements View.OnClickListener{
    Button simpanWave;
    EditText nilaiWave;

    DatabaseReference ref;
    String key;
    Double nilai;
//    ArrayList<Double> dataWave;

    ProgressDialog progressDialog;
    FinalScore finalScore;

    ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_test);
        cd = new ConnectionDetector(this);
//        dataWave = new ArrayList<>();
        finalScore = new FinalScore();

        nilaiWave = (EditText) findViewById(R.id.nilaiWave);
        simpanWave = (Button) findViewById(R.id.simpanWave);
        key = getIntent().getStringExtra("key");

        ref = FirebaseDatabase.getInstance().getReference();

        simpanWave.setOnClickListener(this);
    }

    public void showProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void cekInput(){
        if (cd.isConnected()) {
            if (nilaiWave.getText().toString().equals("")) {
                Toast.makeText(this, "harus diisi", Toast.LENGTH_SHORT).show();
            } else if (Double.valueOf(nilaiWave.getText().toString()) < 0 || Double.valueOf(nilaiWave.getText().toString()) > 100) {
                Toast.makeText(this, "nilai skala 0 - 100", Toast.LENGTH_SHORT).show();
            } else {
                nilai = Double.valueOf(nilaiWave.getText().toString());
                showProgressDialog();
                storeWave();
            }
        } else {
            Toast.makeText(this, "you must connection internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void storeWave() {
        final DatabaseReference scoring = ref.child("scoring");
        scoring.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("wave")){
                    scoring.child(key).child("wave").child("self").setValue(nilai).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            updateWave();
                        }
                    });
                } else {
                    Wave wave = new Wave();
                    wave.setSelf(nilai);
                    scoring.child(key).child("wave").setValue(wave).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            updateWave();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(WaveTestActivity.this, "check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateWave() {
        final DatabaseReference wave = ref.child("scoring");
        wave.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Double> dataWave = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.hasChild("wave")) {
                        Wave getWave = ds.child("wave").getValue(Wave.class);
                        dataWave.add(getWave.getSelf());
                        Log.e("waveSelft", String.valueOf(getWave.getSelf()));
                    }
                }

                Collections.sort(dataWave);
                if (dataWave.size() != 0) {
                    setMinWave(dataWave.get(0));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(WaveTestActivity.this, "check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setMinWave(final Double minWave) {
        final DatabaseReference wave = ref.child("scoring");
        wave.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.hasChild("wave")) {
                        final DatabaseReference as = ds.getRef();
                        final DataSnapshot finalDs = ds;
                        as.child("wave").child("min").setValue(minWave).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Wave getWave = finalDs.child("wave").getValue(Wave.class);
                                double score = minWave/getWave.getSelf() * 100;

                                as.child("wave").child("score").setValue(score);
                                Total total = new Total();
                                total.setTotal(score * 0.3);

                                if (finalDs.child("wave").hasChild("total")){
                                    as.child("wave").child("total").child("total").setValue(score * 0.3);
                                } else {
                                    as.child("wave").child("total").setValue(total);
                                }

                                updateFinalScore();
                            }
                        });
                    }
                }

                progressDialog.dismiss();
                Toast.makeText(WaveTestActivity.this, "save done", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(WaveTestActivity.this, "check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateFinalScore(){
        DatabaseReference scoring = ref.child("scoring");
        scoring.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    FinalScore finalScore = new FinalScore();

                    if (ds.hasChild("presentation")){
                        Total total = ds.child("presentation").child("total").getValue(Total.class);
                        finalScore.setPresentation(total.getTotal());
                    } if (ds.hasChild("inovation")){
                        Total total = ds.child("inovation").child("total").getValue(Total.class);
                        finalScore.setInovation(total.getTotal());
                    } if (ds.hasChild("platform")){
                        Total total = ds.child("platform").child("total").getValue(Total.class);
                        finalScore.setPlatform(total.getTotal());
                    } if (ds.hasChild("wave")){
                        Total total = ds.child("wave").child("total").getValue(Total.class);
                        finalScore.setWave(total.getTotal());
                    }

                    finalScore.setTotal(finalScore.getInovation() + finalScore.getPresentation()
                                + finalScore.getPlatform() + finalScore.getWave());

                    ref.child("final_score").child(ds.getKey()).child("final").setValue(finalScore);
                    ref.child("final_score").child(ds.getKey()).child("nama").setValue(ds.child("nama").getValue(String.class));
                    ref.child("final_score").child(ds.getKey()).child("universitas").setValue(ds.child("universitas").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(WaveTestActivity.this, "check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.simpanWave:
                cekInput();
                break;
        }
    }
}
