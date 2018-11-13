package com.trydev.scoreoceania;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.FinalScore;
import model.KriteriaInovation;
import model.KriteriaPlatform;
import model.KriteriaPresentation;
import model.Total;
import util.ConnectionDetector;

public class InputScoreActivity extends AppCompatActivity  implements View.OnClickListener{
    EditText juri1,juri2,juri3;
    TextView tvJudul,tvCriteria;
    Button simpanNilai;

    DatabaseReference ref;
    String judul;
    String criteria;
    String key;

    ProgressDialog progressDialog;
    ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_score);
        cd = new ConnectionDetector(this);

        judul = getIntent().getStringExtra("judul");
        criteria = getIntent().getStringExtra("criteria");
        key = getIntent().getStringExtra("key");

        Log.e("jd", judul);
        Log.e("crit", criteria);
        Log.e("key", key);

        tvJudul = (TextView) findViewById(R.id.judul);
        tvCriteria = (TextView) findViewById(R.id.criteria);
        juri1 = (EditText) findViewById(R.id.juri1);
        juri2 = (EditText) findViewById(R.id.juri2);
        juri3 = (EditText) findViewById(R.id.juri3);
        simpanNilai = (Button) findViewById(R.id.simpanNilai);
        ref = FirebaseDatabase.getInstance().getReference();
        tvJudul.setText(judul);
        tvCriteria.setText(criteria);


        simpanNilai.setOnClickListener(this);
    }

    public void showProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();
    }

    private void cekInput() {
        if (juri1.getText().toString().equals("") || juri2.getText().toString().equals("")
                || juri3.getText().toString().equals("")){
            Toast.makeText(this, "semua harus diisi", Toast.LENGTH_SHORT).show();
        } else if ((Double.valueOf(juri1.getText().toString()) < 0 || Double.valueOf(juri1.getText().toString()) > 100)
                || (Double.valueOf(juri2.getText().toString()) < 0 || Double.valueOf(juri2.getText().toString()) > 100)
                || (Double.valueOf(juri3.getText().toString()) < 0 || Double.valueOf(juri3.getText().toString()) > 100)){
            Toast.makeText(this, "inputan skala 0 - 100", Toast.LENGTH_SHORT).show();
        } else {
            showProgressDialog();
            storeDatabase();
        }
    }

    private void storeDatabase() {
        storeJuri1();
    }

    private void storeJuri1() {
        KriteriaPresentation kriteriaPresentation = new KriteriaPresentation();
        KriteriaInovation kriteriaInovation = new KriteriaInovation();
        KriteriaPlatform kriteriaPlatform = new KriteriaPlatform();

        double valueJuri1 = 0;

        //Presentation
        if (criteria.equals("pu")){
            kriteriaPresentation.setPu(Double.valueOf(juri1.getText().toString()));
            valueJuri1 = Double.valueOf(juri1.getText().toString());
        } else if (criteria.equals("fpd")){
            kriteriaPresentation.setFpd(Double.valueOf(juri1.getText().toString()));
            valueJuri1 = Double.valueOf(juri1.getText().toString());
        } else if (criteria.equals("tc")){
            kriteriaPresentation.setTc(Double.valueOf(juri1.getText().toString()));
            valueJuri1 = Double.valueOf(juri1.getText().toString());
        } else if (criteria.equals("aa")){
            kriteriaPresentation.setAa(Double.valueOf(juri1.getText().toString()));
            valueJuri1 = Double.valueOf(juri1.getText().toString());
        }

        //Inovation
        else if (criteria.equals("strI")){
            kriteriaInovation.setStrI(Double.valueOf(juri1.getText().toString()));
            valueJuri1 = Double.valueOf(juri1.getText().toString());
        } else if (criteria.equals("staI")){
            kriteriaInovation.setStaI(Double.valueOf(juri1.getText().toString()));
            valueJuri1 = Double.valueOf(juri1.getText().toString());
        } else if (criteria.equals("fe")){
            kriteriaInovation.setFe(Double.valueOf(juri1.getText().toString()));
            valueJuri1 = Double.valueOf(juri1.getText().toString());
        } else if (criteria.equals("oi")){
            kriteriaInovation.setOi(Double.valueOf(juri1.getText().toString()));
            valueJuri1 = Double.valueOf(juri1.getText().toString());
        }

        //Platform
        else if (criteria.equals("td")){
            kriteriaPlatform.setTd(Double.valueOf(juri1.getText().toString()));
            valueJuri1 = Double.valueOf(juri1.getText().toString());
        } else if (criteria.equals("mPre")){
            kriteriaPlatform.setmPre(Double.valueOf(juri1.getText().toString()));
            valueJuri1 = Double.valueOf(juri1.getText().toString());
        } else if (criteria.equals("mPro")){
            kriteriaPlatform.setmPro(Double.valueOf(juri1.getText().toString()));
            valueJuri1 = Double.valueOf(juri1.getText().toString());
        }

        final DatabaseReference juri1 = ref.child("scoring").child(key);
        final double finalValueJuri1 = valueJuri1;

        final KriteriaPresentation finalKriteriaPresentation = kriteriaPresentation;
        final KriteriaInovation finalKriteriaInovation = kriteriaInovation;
        final KriteriaPlatform finalKriteriaPlatform = kriteriaPlatform;
        juri1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(judul)){
                    if (dataSnapshot.child(judul).hasChild("juri1")) {
                        juri1.child(judul).child("juri1").child(criteria).setValue(finalValueJuri1);
                    } else {
                        if (judul.equals("presentation")) {
                            juri1.child(judul).child("juri1").setValue(finalKriteriaPresentation);
                        } else if (judul.equals("inovation")){
                            juri1.child(judul).child("juri1").setValue(finalKriteriaInovation);
                        } else if (judul.equals("platform")){
                            juri1.child(judul).child("juri1").setValue(finalKriteriaPlatform);
                        }
                    }
                } else {
                    if (judul.equals("presentation")) {
                        juri1.child(judul).child("juri1").setValue(finalKriteriaPresentation);
                    } else if (judul.equals("inovation")){
                        juri1.child(judul).child("juri1").setValue(finalKriteriaInovation);
                    } else if (judul.equals("platform")){
                        juri1.child(judul).child("juri1").setValue(finalKriteriaPlatform);
                    }
                }

                storeJuri2();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(InputScoreActivity.this, "fail save", Toast.LENGTH_SHORT).show();
                Toast.makeText(InputScoreActivity.this, "check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeJuri2() {
        KriteriaPresentation kriteriaPresentation = new KriteriaPresentation();
        KriteriaInovation kriteriaInovation = new KriteriaInovation();
        KriteriaPlatform kriteriaPlatform = new KriteriaPlatform();
        double valueJuri2 = 0;

        //Presentation
        if (criteria.equals("pu")){
            kriteriaPresentation.setPu(Double.valueOf(juri2.getText().toString()));
            valueJuri2 = Double.valueOf(juri2.getText().toString());
        } else if (criteria.equals("fpd")){
            kriteriaPresentation.setFpd(Double.valueOf(juri2.getText().toString()));
            valueJuri2 = Double.valueOf(juri2.getText().toString());
        } else if (criteria.equals("tc")){
            kriteriaPresentation.setTc(Double.valueOf(juri2.getText().toString()));
            valueJuri2 = Double.valueOf(juri2.getText().toString());
        } else if (criteria.equals("aa")){
            kriteriaPresentation.setAa(Double.valueOf(juri2.getText().toString()));
            valueJuri2 = Double.valueOf(juri2.getText().toString());
        }

        //Inovation
        else if (criteria.equals("strI")){
            kriteriaInovation.setStrI(Double.valueOf(juri2.getText().toString()));
            valueJuri2 = Double.valueOf(juri2.getText().toString());
        } else if (criteria.equals("staI")){
            kriteriaInovation.setStaI(Double.valueOf(juri2.getText().toString()));
            valueJuri2 = Double.valueOf(juri2.getText().toString());
        } else if (criteria.equals("fe")){
            kriteriaInovation.setFe(Double.valueOf(juri2.getText().toString()));
            valueJuri2 = Double.valueOf(juri2.getText().toString());
        } else if (criteria.equals("oi")){
            kriteriaInovation.setOi(Double.valueOf(juri2.getText().toString()));
            valueJuri2 = Double.valueOf(juri2.getText().toString());
        }

        //Platform
        else if (criteria.equals("td")){
            kriteriaPlatform.setTd(Double.valueOf(juri2.getText().toString()));
            valueJuri2 = Double.valueOf(juri2.getText().toString());
        } else if (criteria.equals("mPre")){
            kriteriaPlatform.setmPre(Double.valueOf(juri2.getText().toString()));
            valueJuri2 = Double.valueOf(juri2.getText().toString());
        } else if (criteria.equals("mPro")){
            kriteriaPlatform.setmPro(Double.valueOf(juri2.getText().toString()));
            valueJuri2 = Double.valueOf(juri2.getText().toString());
        }

        final DatabaseReference juri2 = ref.child("scoring").child(key);
        final double finalValueJuri2 = valueJuri2;
        final KriteriaPresentation finalKriteriaPresentation = kriteriaPresentation;
        final KriteriaInovation finalKriteriaInovation = kriteriaInovation;
        final KriteriaPlatform finalKriteriaPlatform = kriteriaPlatform;
        juri2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(judul)){
                    if (dataSnapshot.child(judul).hasChild("juri2")) {
                        juri2.child(judul).child("juri2").child(criteria).setValue(finalValueJuri2);
                    } else {
                        if (judul.equals("presentation")) {
                            juri2.child(judul).child("juri2").setValue(finalKriteriaPresentation);
                        } else if (judul.equals("inovation")){
                            juri2.child(judul).child("juri2").setValue(finalKriteriaInovation);
                        } else if (judul.equals("platform")){
                            juri2.child(judul).child("juri2").setValue(finalKriteriaPlatform);
                        }
                    }
                } else {
                    if (judul.equals("presentation")) {
                        juri2.child(judul).child("juri2").setValue(finalKriteriaPresentation);
                    } else if (judul.equals("inovation")){
                        juri2.child(judul).child("juri2").setValue(finalKriteriaInovation);
                    } else if (judul.equals("platform")){
                        juri2.child(judul).child("juri2").setValue(finalKriteriaPlatform);
                    }
                }

                storeJuri3();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(InputScoreActivity.this, "fail save", Toast.LENGTH_SHORT).show();
                Toast.makeText(InputScoreActivity.this, "check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeJuri3() {
        KriteriaPresentation kriteriaPresentation = new KriteriaPresentation();
        KriteriaInovation kriteriaInovation = new KriteriaInovation();
        KriteriaPlatform kriteriaPlatform = new KriteriaPlatform();
        double valueJuri3 = 0;

        //Presentation
        if (criteria.equals("pu")){
            kriteriaPresentation.setPu(Double.valueOf(juri3.getText().toString()));
            valueJuri3 = Double.valueOf(juri3.getText().toString());
        } else if (criteria.equals("fpd")){
            kriteriaPresentation.setFpd(Double.valueOf(juri3.getText().toString()));
            valueJuri3 = Double.valueOf(juri3.getText().toString());
        } else if (criteria.equals("tc")){
            kriteriaPresentation.setTc(Double.valueOf(juri3.getText().toString()));
            valueJuri3 = Double.valueOf(juri3.getText().toString());
        } else if (criteria.equals("aa")){
            kriteriaPresentation.setAa(Double.valueOf(juri3.getText().toString()));
            valueJuri3 = Double.valueOf(juri3.getText().toString());
        }

        //Inovation
        else if (criteria.equals("strI")){
            kriteriaInovation.setStrI(Double.valueOf(juri3.getText().toString()));
            valueJuri3 = Double.valueOf(juri3.getText().toString());
        } else if (criteria.equals("staI")){
            kriteriaInovation.setStaI(Double.valueOf(juri3.getText().toString()));
            valueJuri3 = Double.valueOf(juri3.getText().toString());
        } else if (criteria.equals("fe")){
            kriteriaInovation.setFe(Double.valueOf(juri3.getText().toString()));
            valueJuri3 = Double.valueOf(juri3.getText().toString());
        } else if (criteria.equals("oi")){
            kriteriaInovation.setOi(Double.valueOf(juri3.getText().toString()));
            valueJuri3 = Double.valueOf(juri3.getText().toString());
        }

        //Platfrom
        else if (criteria.equals("td")){
            kriteriaPlatform.setTd(Double.valueOf(juri3.getText().toString()));
            valueJuri3 = Double.valueOf(juri3.getText().toString());
        } else if (criteria.equals("mPre")){
            kriteriaPlatform.setmPre(Double.valueOf(juri3.getText().toString()));
            valueJuri3 = Double.valueOf(juri3.getText().toString());
        } else if (criteria.equals("mPro")){
            kriteriaPlatform.setmPro(Double.valueOf(juri3.getText().toString()));
            valueJuri3 = Double.valueOf(juri3.getText().toString());
        }

        final DatabaseReference juri3 = ref.child("scoring").child(key);
        final double finalValueJuri3 = valueJuri3;
        final KriteriaPresentation finalKriteriaPresentation = kriteriaPresentation;
        final KriteriaInovation finalKriteriaInovation = kriteriaInovation;
        final KriteriaPlatform finalKriteriaPlatform = kriteriaPlatform;
        juri3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(judul)){
                    if (dataSnapshot.child(judul).hasChild("juri3")) {
                        juri3.child(judul).child("juri3").child(criteria).setValue(finalValueJuri3);
                    } else {
                        if (judul.equals("presentation")) {
                            juri3.child(judul).child("juri3").setValue(finalKriteriaPresentation);
                        } else if (judul.equals("inovation")){
                            juri3.child(judul).child("juri3").setValue(finalKriteriaInovation);
                        } else if (judul.equals("platform")){
                            juri3.child(judul).child("juri3").setValue(finalKriteriaPlatform);
                        }
                    }
                } else {
                    if (judul.equals("presentation")) {
                        juri3.child(judul).child("juri3").setValue(finalKriteriaPresentation);
                    } else if (judul.equals("inovation")){
                        juri3.child(judul).child("juri3").setValue(finalKriteriaInovation);
                    } else if (judul.equals("platform")){
                        juri3.child(judul).child("juri3").setValue(finalKriteriaPlatform);
                    }
                }

                storeTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(InputScoreActivity.this, "fail save", Toast.LENGTH_SHORT).show();
                Toast.makeText(InputScoreActivity.this, "check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeTotal() {
        DatabaseReference coba = ref.child("scoring").child(key).child(judul);
        coba.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double totalAll = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    if (judul.equals("presentation")){
                        KriteriaPresentation kriteriaPresentation =
                                ds.getValue(KriteriaPresentation.class);
                        Total total = new Total();
                        total.setSubTotal(kriteriaPresentation.getPu()*0.3 +
                                kriteriaPresentation.getFpd() * 0.2 +
                                kriteriaPresentation.getTc() * 0.1 +
                                kriteriaPresentation.getAa() * 0.4);
                        total.setTotalBobot(total.getSubTotal() * 0.3);
                        ref.child("scoring").child(key).child(judul).child(ds.getKey())
                                    .child("total").setValue(total);
                        totalAll = totalAll + total.getTotalBobot();

                        Log.e("subTotal", String.valueOf(total.getSubTotal()));
                        Log.e("totalAll", String.valueOf(totalAll));
                    } else if (judul.equals("inovation")){
                        KriteriaInovation kriteriaInovation =
                                ds.getValue(KriteriaInovation.class);
                        Total total = new Total();
                        total.setSubTotal(kriteriaInovation.getStrI()* 0.3 +
                                kriteriaInovation.getStaI() * 0.4 +
                                kriteriaInovation.getFe() * 0.2 + kriteriaInovation.getOi() * 0.1);

                        total.setTotalBobot(total.getSubTotal() * 0.25);
                        ref.child("scoring").child(key).child(judul).child(ds.getKey())
                                .child("total").setValue(total);
                        totalAll = totalAll + total.getTotalBobot();

                        Log.e("subTotal", String.valueOf(total.getSubTotal()));
                        Log.e("totalAll", String.valueOf(totalAll));
                    } else if (judul.equals("platform")){
                        KriteriaPlatform kriteriaPlatform =
                                ds.getValue(KriteriaPlatform.class);
                        Total total = new Total();
                        total.setSubTotal(kriteriaPlatform.getTd() * 0.3 +
                                kriteriaPlatform.getmPre() * 0.2 +
                                kriteriaPlatform.getmPro() * 0.5);

                        total.setTotalBobot(total.getSubTotal() * 0.15);
                        ref.child("scoring").child(key).child(judul).child(ds.getKey())
                                .child("total").setValue(total);
                        totalAll = totalAll + total.getTotalBobot();

                        Log.e("subTotal", String.valueOf(total.getSubTotal()));
                        Log.e("totalAll", String.valueOf(totalAll));
                    }
                }
                Total tot = new Total();
                tot.setTotal(totalAll/3);
                ref.child("scoring").child(key).child(judul).child("total").setValue(tot).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateFinalScore();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(InputScoreActivity.this, "fail save", Toast.LENGTH_SHORT).show();
                Toast.makeText(InputScoreActivity.this, "check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateFinalScore(){
        DatabaseReference scoring = ref.child("scoring").child(key);
        scoring.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot ds = dataSnapshot;
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

                progressDialog.dismiss();
                Toast.makeText(InputScoreActivity.this, "save done", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(InputScoreActivity.this, "fail save", Toast.LENGTH_SHORT).show();
                Toast.makeText(InputScoreActivity.this, "check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.simpanNilai:
                if (cd.isConnected()) {
                    cekInput();
                } else {
                    Toast.makeText(this, "you must connection internet", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
