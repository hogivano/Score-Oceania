package com.trydev.scoreoceania;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hendrix.pdfmyxml.PdfDocument;
import com.hendrix.pdfmyxml.viewRenderer.AbstractViewRenderer;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.PdfWriter;

import model.KriteriaInovation;
import model.KriteriaPlatform;
import model.KriteriaPresentation;
import model.ScoreTeam;
import model.Total;
import model.Wave;
import util.ConnectionDetector;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{
    TextView detailsTeam, detailsUniv;

    //Presentation
    TextView detailsJuri1Pu, detailsJuri2Pu, detailsJuri3Pu;
    TextView detailsJuri1Fpd, detailsJuri2Fpd, detailsJuri3Fpd;
    TextView detailsJuri1Tc, detailsJuri2Tc, detailsJuri3Tc;
    TextView detailsJuri1Aa, detailsJuri2Aa, detailsJuri3Aa;

    TextView pressJuri1SubTot, pressJuri2SubTot, pressJuri3SubTot;
    TextView pressJuri1TotBot, pressJuri2TotBot, pressJuri3TotBot;

    TextView pressTotal;


    //Inovation
    TextView detailsJuri1StaI, detailsJuri2StaI, detailsJuri3StaI;
    TextView detailsJuri1StrI, detailsJuri2StrI, detailsJuri3StrI;
    TextView detailsJuri1Fe, detailsJuri2Fe, detailsJuri3Fe;
    TextView detailsJuri1Oi, detailsJuri2Oi, detailsJuri3Oi;

    TextView InovJuri1SubTot, InovJuri2SubTot, InovJuri3SubTot;
    TextView InovJuri1TotBot, InovJuri2TotBot, InovJuri3TotBot;

    TextView inovTotal;

    //Platform
    TextView detailsJuri1MPre, detailsJuri2MPre, detailsJuri3MPre;
    TextView detailsJuri1MPro, detailsJuri2MPro, detailsJuri3MPro;
    TextView detailsJuri1Td, detailsJuri2Td, detailsJuri3Td;

    TextView platJuri1SubTot, platJuri2SubTot, platJuri3SubTot;
    TextView platJuri1TotBot, platJuri2TotBot, platJuri3TotBot;

    TextView platTotal;

    //Wave
    TextView detailsWaveHh, detailsWaveMinHh, detailsWaveScore;
    TextView waveTotal;

    //Akumulasi
    TextView akumulasi;

    ProgressDialog progressDialog;
    DatabaseReference ref;
    ScoreTeam scoreTeam;
    String key;

    Document document;
    Button savePdf;
    ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        scoreTeam = (ScoreTeam) getIntent().getSerializableExtra("scoring");
        key = scoreTeam.getKey();
        cd = new ConnectionDetector(this);

        savePdf = (Button) findViewById(R.id.savePdf);
        detailsTeam = (TextView) findViewById(R.id.detailsTeam);
        detailsUniv = (TextView) findViewById(R.id.detailsUniv);

        detailsTeam.setText(scoreTeam.getTeam());
        detailsUniv.setText(scoreTeam.getUniversitas());

        //Presentation
        detailsJuri1Pu = (TextView) findViewById(R.id.detailsJuri1Pu);
        detailsJuri2Pu = (TextView) findViewById(R.id.detailsJuri2Pu);
        detailsJuri3Pu = (TextView) findViewById(R.id.detailsJuri3Pu);

        detailsJuri1Fpd = (TextView) findViewById(R.id.detailsJuri1Fpd);
        detailsJuri2Fpd = (TextView) findViewById(R.id.detailsJuri2Fpd);
        detailsJuri3Fpd = (TextView) findViewById(R.id.detailsJuri3Fpd);

        detailsJuri1Tc = (TextView) findViewById(R.id.detailsJuri1Tc);
        detailsJuri2Tc = (TextView) findViewById(R.id.detailsJuri2Tc);
        detailsJuri3Tc = (TextView) findViewById(R.id.detailsJuri3Tc);

        detailsJuri1Aa = (TextView) findViewById(R.id.detailsJuri1Aa);
        detailsJuri2Aa = (TextView) findViewById(R.id.detailsJuri2Aa);
        detailsJuri3Aa = (TextView) findViewById(R.id.detailsJuri3Aa);

        pressJuri1SubTot = (TextView) findViewById(R.id.presJuri1SubTot);
        pressJuri2SubTot = (TextView) findViewById(R.id.presJuri2SubTot);
        pressJuri3SubTot = (TextView) findViewById(R.id.presJuri3SubTot);

        pressJuri1TotBot = (TextView) findViewById(R.id.presJuri1TotBot);
        pressJuri2TotBot = (TextView) findViewById(R.id.presJuri2TotBot);
        pressJuri3TotBot = (TextView) findViewById(R.id.presJuri3TotBot);

        pressTotal = (TextView) findViewById(R.id.presTotal);


        //Inovation
        detailsJuri1StaI = (TextView) findViewById(R.id.detailsJuri1StaI);
        detailsJuri2StaI = (TextView) findViewById(R.id.detailsJuri2StaI);
        detailsJuri3StaI = (TextView) findViewById(R.id.detailsJuri3StaI);

        detailsJuri1StrI = (TextView) findViewById(R.id.detailsJuri1StrI);
        detailsJuri2StrI = (TextView) findViewById(R.id.detailsJuri2StrI);
        detailsJuri3StrI = (TextView) findViewById(R.id.detailsJuri3StrI);

        detailsJuri1Fe = (TextView) findViewById(R.id.detailsJuri1Fe);
        detailsJuri2Fe = (TextView) findViewById(R.id.detailsJuri2Fe);
        detailsJuri3Fe = (TextView) findViewById(R.id.detailsJuri3Fe);

        detailsJuri1Oi = (TextView) findViewById(R.id.detailsJuri1Oi);
        detailsJuri2Oi = (TextView) findViewById(R.id.detailsJuri2Oi);
        detailsJuri3Oi = (TextView) findViewById(R.id.detailsJuri3Oi);

        InovJuri1SubTot = (TextView) findViewById(R.id.InovJuri1SubTot);
        InovJuri2SubTot = (TextView) findViewById(R.id.InovJuri2SubTot);
        InovJuri3SubTot = (TextView) findViewById(R.id.InovJuri3SubTot);

        InovJuri1TotBot = (TextView) findViewById(R.id.InovJuri1TotBot);
        InovJuri2TotBot = (TextView) findViewById(R.id.InovJuri2TotBot);
        InovJuri3TotBot = (TextView) findViewById(R.id.InovJuri3TotBot);

        inovTotal = (TextView) findViewById(R.id.inovTotal);

        //Platform
        detailsJuri1MPre = (TextView) findViewById(R.id.detailsJuri1MPre);
        detailsJuri2MPre = (TextView) findViewById(R.id.detailsJuri2MPre);
        detailsJuri3MPre = (TextView) findViewById(R.id.detailsJuri3MPre);

        detailsJuri1MPro = (TextView) findViewById(R.id.detailsJuri1MPro);
        detailsJuri2MPro = (TextView) findViewById(R.id.detailsJuri2MPro);
        detailsJuri3MPro = (TextView) findViewById(R.id.detailsJuri3MPro);

        detailsJuri1Td = (TextView) findViewById(R.id.detailsJuri1Td);
        detailsJuri2Td = (TextView) findViewById(R.id.detailsJuri2Td);
        detailsJuri3Td = (TextView) findViewById(R.id.detailsJuri3Td);

        platJuri1SubTot = (TextView) findViewById(R.id.platJuri1SubTot);
        platJuri2SubTot = (TextView) findViewById(R.id.platJuri2SubTot);
        platJuri3SubTot = (TextView) findViewById(R.id.platJuri3SubTot);

        platJuri1TotBot = (TextView) findViewById(R.id.platJuri1TotBot);
        platJuri2TotBot = (TextView) findViewById(R.id.platJuri2TotBot);
        platJuri3TotBot = (TextView) findViewById(R.id.platJuri3TotBot);

        platTotal = (TextView) findViewById(R.id.platTotal);

        //Wave
        detailsWaveHh = (TextView) findViewById(R.id.detailsWaveHh);
        detailsWaveMinHh = (TextView) findViewById(R.id.detailsWaveMinHh);
        detailsWaveScore = (TextView) findViewById(R.id.detailsWaveScore);

        waveTotal = (TextView) findViewById(R.id.waveTotal);

        //Akumulasi
        akumulasi = (TextView) findViewById(R.id.akumulasi);
        akumulasi.setText("Hasil Akhir : " + String.valueOf(scoreTeam.getFinalScore().getTotal()));

        ref = FirebaseDatabase.getInstance().getReference();
        showProggresDialog();
        loadTeam();

        savePdf.setOnClickListener(this);
    }

    public void showProggresDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();
    }

    public void loadTeam(){
        ref.child("scoring").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot ds = dataSnapshot;
                if (ds.hasChild("presentation")) {

                    //Presentation Juri1
                    KriteriaPresentation kriteriaPresentationJuri1 = new KriteriaPresentation();
                    kriteriaPresentationJuri1.setPu(ds.child("presentation").child("juri1").child("pu").
                            getValue(Double.class));
                    kriteriaPresentationJuri1.setFpd(ds.child("presentation").child("juri1").child("fpd").
                            getValue(Double.class));
                    kriteriaPresentationJuri1.setTc(ds.child("presentation").child("juri1").child("tc").
                            getValue(Double.class));
                    kriteriaPresentationJuri1.setAa(ds.child("presentation").child("juri1").child("aa").
                            getValue(Double.class));

                    //Presentation Juri2
                    KriteriaPresentation kriteriaPresentationJuri2 = new KriteriaPresentation();
                    kriteriaPresentationJuri2.setPu(ds.child("presentation").child("juri2").child("pu").
                            getValue(Double.class));
                    kriteriaPresentationJuri2.setFpd(ds.child("presentation").child("juri2").child("fpd").
                            getValue(Double.class));
                    kriteriaPresentationJuri2.setTc(ds.child("presentation").child("juri2").child("tc").
                            getValue(Double.class));
                    kriteriaPresentationJuri2.setAa(ds.child("presentation").child("juri2").child("aa").
                            getValue(Double.class));

                    //Presentation Juri3
                    KriteriaPresentation kriteriaPresentationJuri3 = new KriteriaPresentation();
                    kriteriaPresentationJuri3.setPu(ds.child("presentation").child("juri3").child("pu").
                            getValue(Double.class));
                    kriteriaPresentationJuri3.setFpd(ds.child("presentation").child("juri3").child("fpd").
                            getValue(Double.class));
                    kriteriaPresentationJuri3.setTc(ds.child("presentation").child("juri3").child("tc").
                            getValue(Double.class));
                    kriteriaPresentationJuri3.setAa(ds.child("presentation").child("juri3").child("aa").
                            getValue(Double.class));

                    Total pressTotalJuri1 = ds.child("presentation").child("juri1").child("total").getValue(Total.class);
                    Total pressTotalJuri2 = ds.child("presentation").child("juri2").child("total").getValue(Total.class);
                    Total pressTotalJuri3 = ds.child("presentation").child("juri3").child("total").getValue(Total.class);

                    setDetailPresentation(kriteriaPresentationJuri1, kriteriaPresentationJuri2,
                            kriteriaPresentationJuri3, pressTotalJuri1, pressTotalJuri2, pressTotalJuri3);

                }

                if (ds.hasChild("inovation")) {

                    //Inovation
                    KriteriaInovation kriteriaInovationJuri1 = new KriteriaInovation();
                    kriteriaInovationJuri1.setStaI(ds.child("inovation").child("juri1").child("staI").
                            getValue(Double.class));
                    kriteriaInovationJuri1.setStrI(ds.child("inovation").child("juri1").child("strI").
                            getValue(Double.class));
                    kriteriaInovationJuri1.setFe(ds.child("inovation").child("juri1").child("fe").
                            getValue(Double.class));
                    kriteriaInovationJuri1.setOi(ds.child("inovation").child("juri1").child("oi").
                            getValue(Double.class));

                    KriteriaInovation kriteriaInovationJuri2 = new KriteriaInovation();
                    kriteriaInovationJuri2.setStaI(ds.child("inovation").child("juri2").child("staI").
                            getValue(Double.class));
                    kriteriaInovationJuri2.setStrI(ds.child("inovation").child("juri2").child("strI").
                            getValue(Double.class));
                    kriteriaInovationJuri2.setFe(ds.child("inovation").child("juri2").child("fe").
                            getValue(Double.class));
                    kriteriaInovationJuri2.setOi(ds.child("inovation").child("juri2").child("oi").
                            getValue(Double.class));


                    KriteriaInovation kriteriaInovationJuri3 = new KriteriaInovation();
                    kriteriaInovationJuri3.setStaI(ds.child("inovation").child("juri3").child("staI").
                            getValue(Double.class));
                    kriteriaInovationJuri3.setStrI(ds.child("inovation").child("juri3").child("strI").
                            getValue(Double.class));
                    kriteriaInovationJuri3.setFe(ds.child("inovation").child("juri3").child("fe").
                            getValue(Double.class));
                    kriteriaInovationJuri3.setOi(ds.child("inovation").child("juri3").child("oi").
                            getValue(Double.class));

                    Total inovTotalJuri1 = ds.child("inovation").child("juri1").child("total").getValue(Total.class);
                    Total inovTotalJuri2 = ds.child("inovation").child("juri2").child("total").getValue(Total.class);
                    Total inovTotalJuri3 = ds.child("inovation").child("juri3").child("total").getValue(Total.class);

                    setDetailInovation(kriteriaInovationJuri1, kriteriaInovationJuri2, kriteriaInovationJuri3,
                            inovTotalJuri1, inovTotalJuri2, inovTotalJuri3);
                }

                if (ds.hasChild("platform")) {

                    //Platform
                    KriteriaPlatform kriteriaPlatformJuri1 = new KriteriaPlatform();
                    kriteriaPlatformJuri1.setmPre(ds.child("platform").child("juri1").child("mPre").
                            getValue(Double.class));
                    kriteriaPlatformJuri1.setmPro(ds.child("platform").child("juri1").child("mPro").
                            getValue(Double.class));
                    kriteriaPlatformJuri1.setTd(ds.child("platform").child("juri1").child("td").
                            getValue(Double.class));

                    KriteriaPlatform kriteriaPlatformJuri2 = new KriteriaPlatform();
                    kriteriaPlatformJuri2.setmPre(ds.child("platform").child("juri2").child("mPre").
                            getValue(Double.class));
                    kriteriaPlatformJuri2.setmPro(ds.child("platform").child("juri2").child("mPro").
                            getValue(Double.class));
                    kriteriaPlatformJuri2.setTd(ds.child("platform").child("juri2").child("td").
                            getValue(Double.class));

                    KriteriaPlatform kriteriaPlatformJuri3 = new KriteriaPlatform();
                    kriteriaPlatformJuri3.setmPre(ds.child("platform").child("juri3").child("mPre").
                            getValue(Double.class));
                    kriteriaPlatformJuri3.setmPro(ds.child("platform").child("juri3").child("mPro").
                            getValue(Double.class));
                    kriteriaPlatformJuri3.setTd(ds.child("platform").child("juri3").child("td").
                            getValue(Double.class));

                    Total platTotalJuri1 = ds.child("platform").child("juri1").child("total").getValue(Total.class);
                    Total platTotalJuri2 = ds.child("platform").child("juri2").child("total").getValue(Total.class);
                    Total platTotalJuri3 = ds.child("platform").child("juri3").child("total").getValue(Total.class);

                    setDetailPlatform(kriteriaPlatformJuri1, kriteriaPlatformJuri2, kriteriaPlatformJuri3
                            , platTotalJuri1, platTotalJuri2, platTotalJuri3);
                }

                if (ds.hasChild("wave")){

                    //Wave
                    Wave wave = new Wave();
                    wave.setMin(ds.child("wave").child("min").getValue(Double.class));
                    wave.setSelf(ds.child("wave").child("self").getValue(Double.class));
                    wave.setScore(ds.child("wave").child("score").getValue(Double.class));

                    setDetailWave(wave);
                }

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(DetailsActivity.this, "check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createPDF(){
        ref.child("scoring").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot ds = dataSnapshot;
                if (ds.hasChild("presentation")) {

                    //Presentation Juri1
                    KriteriaPresentation kriteriaPresentationJuri1 = new KriteriaPresentation();
                    kriteriaPresentationJuri1.setPu(ds.child("presentation").child("juri1").child("pu").
                            getValue(Double.class));
                    kriteriaPresentationJuri1.setFpd(ds.child("presentation").child("juri1").child("fpd").
                            getValue(Double.class));
                    kriteriaPresentationJuri1.setTc(ds.child("presentation").child("juri1").child("tc").
                            getValue(Double.class));
                    kriteriaPresentationJuri1.setAa(ds.child("presentation").child("juri1").child("aa").
                            getValue(Double.class));

                    //Presentation Juri2
                    KriteriaPresentation kriteriaPresentationJuri2 = new KriteriaPresentation();
                    kriteriaPresentationJuri2.setPu(ds.child("presentation").child("juri2").child("pu").
                            getValue(Double.class));
                    kriteriaPresentationJuri2.setFpd(ds.child("presentation").child("juri2").child("fpd").
                            getValue(Double.class));
                    kriteriaPresentationJuri2.setTc(ds.child("presentation").child("juri2").child("tc").
                            getValue(Double.class));
                    kriteriaPresentationJuri2.setAa(ds.child("presentation").child("juri2").child("aa").
                            getValue(Double.class));

                    //Presentation Juri3
                    KriteriaPresentation kriteriaPresentationJuri3 = new KriteriaPresentation();
                    kriteriaPresentationJuri3.setPu(ds.child("presentation").child("juri3").child("pu").
                            getValue(Double.class));
                    kriteriaPresentationJuri3.setFpd(ds.child("presentation").child("juri3").child("fpd").
                            getValue(Double.class));
                    kriteriaPresentationJuri3.setTc(ds.child("presentation").child("juri3").child("tc").
                            getValue(Double.class));
                    kriteriaPresentationJuri3.setAa(ds.child("presentation").child("juri3").child("aa").
                            getValue(Double.class));

                    Total pressTotalJuri1 = ds.child("presentation").child("juri1").child("total").getValue(Total.class);
                    Total pressTotalJuri2 = ds.child("presentation").child("juri2").child("total").getValue(Total.class);
                    Total pressTotalJuri3 = ds.child("presentation").child("juri3").child("total").getValue(Total.class);

                    setPDFPresentation(kriteriaPresentationJuri1, kriteriaPresentationJuri2,
                            kriteriaPresentationJuri3, pressTotalJuri1, pressTotalJuri2, pressTotalJuri3);
                }

                if (ds.hasChild("inovation")) {

                    //Inovation
                    KriteriaInovation kriteriaInovationJuri1 = new KriteriaInovation();
                    kriteriaInovationJuri1.setStaI(ds.child("inovation").child("juri1").child("staI").
                            getValue(Double.class));
                    kriteriaInovationJuri1.setStrI(ds.child("inovation").child("juri1").child("strI").
                            getValue(Double.class));
                    kriteriaInovationJuri1.setFe(ds.child("inovation").child("juri1").child("fe").
                            getValue(Double.class));
                    kriteriaInovationJuri1.setOi(ds.child("inovation").child("juri1").child("oi").
                            getValue(Double.class));

                    KriteriaInovation kriteriaInovationJuri2 = new KriteriaInovation();
                    kriteriaInovationJuri2.setStaI(ds.child("inovation").child("juri2").child("staI").
                            getValue(Double.class));
                    kriteriaInovationJuri2.setStrI(ds.child("inovation").child("juri2").child("strI").
                            getValue(Double.class));
                    kriteriaInovationJuri2.setFe(ds.child("inovation").child("juri2").child("fe").
                            getValue(Double.class));
                    kriteriaInovationJuri2.setOi(ds.child("inovation").child("juri2").child("oi").
                            getValue(Double.class));


                    KriteriaInovation kriteriaInovationJuri3 = new KriteriaInovation();
                    kriteriaInovationJuri3.setStaI(ds.child("inovation").child("juri3").child("staI").
                            getValue(Double.class));
                    kriteriaInovationJuri3.setStrI(ds.child("inovation").child("juri3").child("strI").
                            getValue(Double.class));
                    kriteriaInovationJuri3.setFe(ds.child("inovation").child("juri3").child("fe").
                            getValue(Double.class));
                    kriteriaInovationJuri3.setOi(ds.child("inovation").child("juri3").child("oi").
                            getValue(Double.class));

                    Total inovTotalJuri1 = ds.child("inovation").child("juri1").child("total").getValue(Total.class);
                    Total inovTotalJuri2 = ds.child("inovation").child("juri2").child("total").getValue(Total.class);
                    Total inovTotalJuri3 = ds.child("inovation").child("juri3").child("total").getValue(Total.class);

                    setPDFInovation(kriteriaInovationJuri1, kriteriaInovationJuri2, kriteriaInovationJuri3,
                            inovTotalJuri1, inovTotalJuri2, inovTotalJuri3);
                }
//
                if (ds.hasChild("platform")) {

                    //Platform
                    KriteriaPlatform kriteriaPlatformJuri1 = new KriteriaPlatform();
                    kriteriaPlatformJuri1.setmPre(ds.child("platform").child("juri1").child("mPre").
                            getValue(Double.class));
                    kriteriaPlatformJuri1.setmPro(ds.child("platform").child("juri1").child("mPro").
                            getValue(Double.class));
                    kriteriaPlatformJuri1.setTd(ds.child("platform").child("juri1").child("td").
                            getValue(Double.class));

                    KriteriaPlatform kriteriaPlatformJuri2 = new KriteriaPlatform();
                    kriteriaPlatformJuri2.setmPre(ds.child("platform").child("juri2").child("mPre").
                            getValue(Double.class));
                    kriteriaPlatformJuri2.setmPro(ds.child("platform").child("juri2").child("mPro").
                            getValue(Double.class));
                    kriteriaPlatformJuri2.setTd(ds.child("platform").child("juri2").child("td").
                            getValue(Double.class));

                    KriteriaPlatform kriteriaPlatformJuri3 = new KriteriaPlatform();
                    kriteriaPlatformJuri3.setmPre(ds.child("platform").child("juri3").child("mPre").
                            getValue(Double.class));
                    kriteriaPlatformJuri3.setmPro(ds.child("platform").child("juri3").child("mPro").
                            getValue(Double.class));
                    kriteriaPlatformJuri3.setTd(ds.child("platform").child("juri3").child("td").
                            getValue(Double.class));

                    Total platTotalJuri1 = ds.child("platform").child("juri1").child("total").getValue(Total.class);
                    Total platTotalJuri2 = ds.child("platform").child("juri2").child("total").getValue(Total.class);
                    Total platTotalJuri3 = ds.child("platform").child("juri3").child("total").getValue(Total.class);

                    setPDFPlatform(kriteriaPlatformJuri1, kriteriaPlatformJuri2, kriteriaPlatformJuri3
                            , platTotalJuri1, platTotalJuri2, platTotalJuri3);
                }
//
                if (ds.hasChild("wave")){

                    //Wave
                    Wave wave = new Wave();
                    wave.setMin(ds.child("wave").child("min").getValue(Double.class));
                    wave.setSelf(ds.child("wave").child("self").getValue(Double.class));
                    wave.setScore(ds.child("wave").child("score").getValue(Double.class));

                    setPDFWave(wave);
                }

                progressDialog.dismiss();
                Toast.makeText(DetailsActivity.this, "berhasil", Toast.LENGTH_SHORT).show();
                document.close();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(DetailsActivity.this, "check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void iText(String team) throws IOException, DocumentException {

        if (Build.VERSION.SDK_INT >= 23){
            if (ContextCompat.checkSelfPermission(DetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(DetailsActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(DetailsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                Toast.makeText(this, "permission good", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "permission not good", Toast.LENGTH_SHORT).show();
            }
        }

        String namaFile = team + ".pdf";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "PdfScoreORDC");

        if (!file.exists()) {
            file.mkdirs();
        }

        File myappFile = new File(file
                + File.separator + namaFile);

        if (Environment.getExternalStorageState() == null){
            Toast.makeText(this, "no Sd Card", Toast.LENGTH_SHORT).show();
        } else {

        }
        document = new Document();

        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(file
                + File.separator + namaFile));
        document.open();

        Paragraph nama = new Paragraph();
        nama.add(scoreTeam.getTeam());
        document.add(nama);

        Paragraph univ = new Paragraph();
        univ.add(scoreTeam.getUniversitas());
        document.add(univ);

        Paragraph totalAll = new Paragraph();
        totalAll.add("Hasil Akhir : " + String.valueOf(scoreTeam.getFinalScore().getTotal()));
        document.add(totalAll);

        Paragraph space = new Paragraph();
        space.add("   ");
        document.add(space);

        createPDF();
    }

    private void setPDFWave(Wave wave) {
        Paragraph judul = new Paragraph();
        Paragraph space = new Paragraph();
        judul.add("Wave Test (30%)");
        space.add("   ");

        PdfPCell cell;
        Paragraph p;
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);

        table.getDefaultCell().setColspan(1);

        cell = new PdfPCell();
        p = new Paragraph("Heave Height");
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setFixedHeight(30);
        table.addCell(cell);

        cell = new PdfPCell();
        p = new Paragraph("Min Heave Height");
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setFixedHeight(30);
        table.addCell(cell);

        cell = new PdfPCell();
        p = new Paragraph("Test Score");
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setFixedHeight(30);
        table.addCell(cell);

        table.addCell(String.valueOf(wave.getSelf()));
        table.addCell(String.valueOf(wave.getMin()));
        table.addCell(String.valueOf(wave.getScore()));

        table.getDefaultCell().setColspan(3);
        table.addCell("  ");
        table.addCell("Total : " + scoreTeam.getFinalScore().getWave());
        try {
            this.document.add(judul);
            this.document.add(space);
            this.document.add(table);
            this.document.add(space);
        } catch (DocumentException e) {
            e.printStackTrace();
            Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show();
        }

    }

    private void setDetailWave(Wave wave) {
        detailsWaveHh.setText(String.valueOf(wave.getSelf()));
        detailsWaveMinHh.setText(String.valueOf(wave.getMin()));
        detailsWaveScore.setText(String.valueOf(wave.getScore()));

        waveTotal.setText("Total : " + String.valueOf(scoreTeam.getFinalScore().getWave()));
    }

    private void setPDFPlatform(KriteriaPlatform kriteriaPlatformJuri1, KriteriaPlatform kriteriaPlatformJuri2, KriteriaPlatform kriteriaPlatformJuri3, Total platTotalJuri1, Total platTotalJuri2, Total platTotalJuri3) {
        Paragraph judul = new Paragraph();
        Paragraph space = new Paragraph();
        judul.add("Platform Model (15%)");
        space.add("   ");

        PdfPCell cell;
        Paragraph p;
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        table.getDefaultCell().setColspan(1);

        cell = new PdfPCell();
        p = new Paragraph("Criteria");
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setFixedHeight(30);
        table.addCell(cell);

        cell = new PdfPCell();
        p = new Paragraph("Juri 1");
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setFixedHeight(30);
        table.addCell(cell);

        cell = new PdfPCell();
        p = new Paragraph("Juri 2");
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setFixedHeight(30);
        table.addCell(cell);

        cell = new PdfPCell();
        p = new Paragraph("Juri 3");
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setFixedHeight(30);
        table.addCell(cell);

//        table.addCell("Juri 1");
//        table.addCell("Juri 2");
//        table.addCell("Juri 3");

        table.addCell("Topside Details (30%)");
        table.addCell(String.valueOf(kriteriaPlatformJuri1.getTd()));
        table.addCell(String.valueOf(kriteriaPlatformJuri2.getTd()));
        table.addCell(String.valueOf(kriteriaPlatformJuri3.getTd()));

        table.addCell("Model Presentability (20%)");
        table.addCell(String.valueOf(kriteriaPlatformJuri1.getmPre()));
        table.addCell(String.valueOf(kriteriaPlatformJuri2.getmPre()));
        table.addCell(String.valueOf(kriteriaPlatformJuri3.getmPre()));

        table.addCell("Model Proportional (50%)");
        table.addCell(String.valueOf(kriteriaPlatformJuri1.getmPro()));
        table.addCell(String.valueOf(kriteriaPlatformJuri2.getmPro()));
        table.addCell(String.valueOf(kriteriaPlatformJuri3.getmPro()));

        table.getDefaultCell().setColspan(4);
        table.addCell("  ");

        table.getDefaultCell().setColspan(1);

        table.addCell("Sub Total (100%)");
        table.addCell(String.valueOf(platTotalJuri1.getSubTotal()));
        table.addCell(String.valueOf(platTotalJuri2.getSubTotal()));
        table.addCell(String.valueOf(platTotalJuri3.getSubTotal()));

        table.addCell("Total Bobot");
        table.addCell(String.valueOf(platTotalJuri1.getTotalBobot()));
        table.addCell(String.valueOf(platTotalJuri2.getTotalBobot()));
        table.addCell(String.valueOf(platTotalJuri3.getTotalBobot()));

        table.getDefaultCell().setColspan(4);
        table.addCell("  ");
        table.addCell("Total : " + scoreTeam.getFinalScore().getPlatform());
        try {
            this.document.add(judul);
            this.document.add(space);
            this.document.add(table);
            this.document.add(space);
        } catch (DocumentException e) {
            e.printStackTrace();
            Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show();
        }
    }

    private void setDetailPlatform(KriteriaPlatform kriteriaPlatformJuri1, KriteriaPlatform kriteriaPlatformJuri2, KriteriaPlatform kriteriaPlatformJuri3, Total platTotalJuri1, Total platTotalJuri2, Total platTotalJuri3) {
        //Set mPre
        detailsJuri1MPre.setText(String.valueOf(kriteriaPlatformJuri1.getmPre()));
        detailsJuri2MPre.setText(String.valueOf(kriteriaPlatformJuri2.getmPre()));
        detailsJuri3MPre.setText(String.valueOf(kriteriaPlatformJuri3.getmPre()));

        //Set Mpro
        detailsJuri1MPro.setText(String.valueOf(kriteriaPlatformJuri1.getmPro()));
        detailsJuri2MPro.setText(String.valueOf(kriteriaPlatformJuri2.getmPro()));
        detailsJuri3MPro.setText(String.valueOf(kriteriaPlatformJuri3.getmPro()));

        //Set Td
        detailsJuri1Td.setText(String.valueOf(kriteriaPlatformJuri1.getTd()));
        detailsJuri2Td.setText(String.valueOf(kriteriaPlatformJuri2.getTd()));
        detailsJuri3Td.setText(String.valueOf(kriteriaPlatformJuri3.getTd()));

        //Set Sub Total
        platJuri1SubTot.setText(String.valueOf(platTotalJuri1.getSubTotal()));
        platJuri2SubTot.setText(String.valueOf(platTotalJuri2.getSubTotal()));
        platJuri3SubTot.setText(String.valueOf(platTotalJuri3.getSubTotal()));

        //Set Total Bobot
        platJuri1TotBot.setText(String.valueOf(platTotalJuri1.getTotalBobot()));
        platJuri2TotBot.setText(String.valueOf(platTotalJuri2.getTotalBobot()));
        platJuri3TotBot.setText(String.valueOf(platTotalJuri3.getTotalBobot()));

        platTotal.setText("Total : " + String.valueOf(scoreTeam.getFinalScore().getPlatform()));
    }

    private void setPDFInovation(KriteriaInovation kriteriaInovationJuri1, KriteriaInovation kriteriaInovationJuri2, KriteriaInovation kriteriaInovationJuri3, Total inovTotalJuri1, Total inovTotalJuri2, Total inovTotalJuri3) {
        Paragraph judul = new Paragraph();
        Paragraph space = new Paragraph();
        judul.add("Inovation And Model Feasibility (25%)");
        space.add("   ");

        PdfPCell cell;
        Paragraph p;
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        table.getDefaultCell().setColspan(1);

        cell = new PdfPCell();
        p = new Paragraph("Criteria");
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setFixedHeight(30);
        table.addCell(cell);

        cell = new PdfPCell();
        p = new Paragraph("Juri 1");
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setFixedHeight(30);
        table.addCell(cell);

        cell = new PdfPCell();
        p = new Paragraph("Juri 2");
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setFixedHeight(30);
        table.addCell(cell);

        cell = new PdfPCell();
        p = new Paragraph("Juri 3");
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setFixedHeight(30);
        table.addCell(cell);

        table.addCell("Stability Innovation (40%)");
        table.addCell(String.valueOf(kriteriaInovationJuri1.getStaI()));
        table.addCell(String.valueOf(kriteriaInovationJuri2.getStaI()));
        table.addCell(String.valueOf(kriteriaInovationJuri3.getStaI()));

        table.addCell("Structural Innovation (30%)");
        table.addCell(String.valueOf(kriteriaInovationJuri1.getStrI()));
        table.addCell(String.valueOf(kriteriaInovationJuri2.getStrI()));
        table.addCell(String.valueOf(kriteriaInovationJuri3.getStrI()));

        table.addCell("Economical Prespective (20%)");
        table.addCell(String.valueOf(kriteriaInovationJuri1.getFe()));
        table.addCell(String.valueOf(kriteriaInovationJuri2.getFe()));
        table.addCell(String.valueOf(kriteriaInovationJuri3.getFe()));

        table.addCell("Other Innovation (10%)");
        table.addCell(String.valueOf(kriteriaInovationJuri1.getOi()));
        table.addCell(String.valueOf(kriteriaInovationJuri2.getOi()));
        table.addCell(String.valueOf(kriteriaInovationJuri3.getOi()));

        table.getDefaultCell().setColspan(4);
        table.addCell("  ");

        table.getDefaultCell().setColspan(1);

        table.addCell("Sub Total (100%)");
        table.addCell(String.valueOf(inovTotalJuri1.getSubTotal()));
        table.addCell(String.valueOf(inovTotalJuri2.getSubTotal()));
        table.addCell(String.valueOf(inovTotalJuri3.getSubTotal()));

        table.addCell("Total Bobot");
        table.addCell(String.valueOf(inovTotalJuri1.getTotalBobot()));
        table.addCell(String.valueOf(inovTotalJuri2.getTotalBobot()));
        table.addCell(String.valueOf(inovTotalJuri3.getTotalBobot()));

        table.getDefaultCell().setColspan(4);
        table.addCell("  ");
        table.addCell("Total : " + scoreTeam.getFinalScore().getInovation());
        try {
            this.document.add(judul);
            this.document.add(space);
            this.document.add(table);
            this.document.add(space);
        } catch (DocumentException e) {
            e.printStackTrace();
            Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show();
        }
    }

    private void setDetailInovation(KriteriaInovation kriteriaInovationJuri1, KriteriaInovation kriteriaInovationJuri2, KriteriaInovation kriteriaInovationJuri3, Total inovTotalJuri1, Total inovTotalJuri2, Total inovTotalJuri3) {

        //Set StaI
        detailsJuri1StaI.setText(String.valueOf(kriteriaInovationJuri1.getStaI()));
        detailsJuri2StaI.setText(String.valueOf(kriteriaInovationJuri2.getStaI()));
        detailsJuri3StaI.setText(String.valueOf(kriteriaInovationJuri3.getStaI()));

        //Set StrI
        detailsJuri1StrI.setText(String.valueOf(kriteriaInovationJuri1.getStrI()));
        detailsJuri2StrI.setText(String.valueOf(kriteriaInovationJuri2.getStrI()));
        detailsJuri3StrI.setText(String.valueOf(kriteriaInovationJuri3.getStrI()));

        //Set Fe
        detailsJuri1Fe.setText(String.valueOf(kriteriaInovationJuri1.getFe()));
        detailsJuri2Fe.setText(String.valueOf(kriteriaInovationJuri2.getFe()));
        detailsJuri3Fe.setText(String.valueOf(kriteriaInovationJuri3.getFe()));

        //set Oi
        detailsJuri1Oi.setText(String.valueOf(kriteriaInovationJuri1.getOi()));
        detailsJuri2Oi.setText(String.valueOf(kriteriaInovationJuri2.getOi()));
        detailsJuri3Oi.setText(String.valueOf(kriteriaInovationJuri3.getOi()));

        //Set Sub Total
        InovJuri1SubTot.setText(String.valueOf(inovTotalJuri1.getSubTotal()));
        InovJuri2SubTot.setText(String.valueOf(inovTotalJuri2.getSubTotal()));
        InovJuri3SubTot.setText(String.valueOf(inovTotalJuri3.getSubTotal()));

        //Set Total Bobot
        InovJuri1TotBot.setText(String.valueOf(inovTotalJuri1.getTotalBobot()));
        InovJuri2TotBot.setText(String.valueOf(inovTotalJuri2.getTotalBobot()));
        InovJuri3TotBot.setText(String.valueOf(inovTotalJuri3.getTotalBobot()));

        inovTotal.setText("Total : " + String.valueOf(scoreTeam.getFinalScore().getInovation()));
    }

    private void setPDFPresentation(KriteriaPresentation kriteriaPresentationJuri1,
                                    KriteriaPresentation kriteriaPresentationJuri2,
                                    KriteriaPresentation kriteriaPresentationJuri3,
                                    Total pressTotalJuri1, Total pressTotalJuri2,
                                    Total pressTotalJuri3) {

        Paragraph judul = new Paragraph();
        Paragraph space = new Paragraph();
        judul.add("Presentation (30%)");
        space.add("   ");

        PdfPCell cell;
        Paragraph p;
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        table.getDefaultCell().setColspan(1);

        cell = new PdfPCell();
        p = new Paragraph("Criteria");
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setFixedHeight(30);
        table.addCell(cell);

        cell = new PdfPCell();
        p = new Paragraph("Juri 1");
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setFixedHeight(30);
        table.addCell(cell);

        cell = new PdfPCell();
        p = new Paragraph("Juri 2");
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setFixedHeight(30);
        table.addCell(cell);

        cell = new PdfPCell();
        p = new Paragraph("Juri 3");
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setFixedHeight(30);
        table.addCell(cell);

        table.addCell("Platform Understanding (30%)");
        table.addCell(String.valueOf(kriteriaPresentationJuri1.getPu()));
        table.addCell(String.valueOf(kriteriaPresentationJuri2.getPu()));
        table.addCell(String.valueOf(kriteriaPresentationJuri3.getPu()));

        table.addCell("Fluency of Presentation Delivery (20%)");
        table.addCell(String.valueOf(kriteriaPresentationJuri1.getFpd()));
        table.addCell(String.valueOf(kriteriaPresentationJuri2.getFpd()));
        table.addCell(String.valueOf(kriteriaPresentationJuri3.getFpd()));

        table.addCell("Team Cohesiveness (10%)");
        table.addCell(String.valueOf(kriteriaPresentationJuri1.getTc()));
        table.addCell(String.valueOf(kriteriaPresentationJuri2.getTc()));
        table.addCell(String.valueOf(kriteriaPresentationJuri3.getTc()));

        table.addCell("Answering Ability (40%)");
        table.addCell(String.valueOf(kriteriaPresentationJuri1.getAa()));
        table.addCell(String.valueOf(kriteriaPresentationJuri2.getAa()));
        table.addCell(String.valueOf(kriteriaPresentationJuri3.getAa()));

        table.getDefaultCell().setColspan(4);
        table.addCell("  ");

        table.getDefaultCell().setColspan(1);

        table.addCell("Sub Total (100%)");
        table.addCell(String.valueOf(pressTotalJuri1.getSubTotal()));
        table.addCell(String.valueOf(pressTotalJuri2.getSubTotal()));
        table.addCell(String.valueOf(pressTotalJuri3.getSubTotal()));

        table.addCell("Total Bobot");
        table.addCell(String.valueOf(pressTotalJuri1.getTotalBobot()));
        table.addCell(String.valueOf(pressTotalJuri2.getTotalBobot()));
        table.addCell(String.valueOf(pressTotalJuri3.getTotalBobot()));

        table.getDefaultCell().setColspan(4);
        table.addCell("  ");
        table.addCell("Total : " + scoreTeam.getFinalScore().getPresentation());
        try {
            this.document.add(judul);
            this.document.add(space);
            this.document.add(table);
            this.document.add(space);
        } catch (DocumentException e) {
            e.printStackTrace();
            Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show();
        }
    }

    public void setDetailPresentation(KriteriaPresentation juri1, KriteriaPresentation juri2, KriteriaPresentation juri3, Total pressTotalJuri1, Total pressTotalJuri2, Total pressTotalJuri3){
        //Set Pu
        detailsJuri1Pu.setText(String.valueOf(juri1.getPu()));
        detailsJuri2Pu.setText(String.valueOf(juri2.getPu()));
        detailsJuri3Pu.setText(String.valueOf(juri3.getPu()));

        //Set Fpd
        detailsJuri1Fpd.setText(String.valueOf(juri1.getFpd()));
        detailsJuri2Fpd.setText(String.valueOf(juri2.getFpd()));
        detailsJuri3Fpd.setText(String.valueOf(juri3.getFpd()));

        //Set Tc
        detailsJuri1Tc.setText(String.valueOf(juri1.getTc()));
        detailsJuri2Tc.setText(String.valueOf(juri2.getTc()));
        detailsJuri3Tc.setText(String.valueOf(juri3.getTc()));

        //Set Aa
        detailsJuri1Aa.setText(String.valueOf(juri1.getAa()));
        detailsJuri2Aa.setText(String.valueOf(juri2.getAa()));
        detailsJuri3Aa.setText(String.valueOf(juri3.getAa()));

        //Sub total
        pressJuri1SubTot.setText(String.valueOf(pressTotalJuri1.getSubTotal()));
        pressJuri2SubTot.setText(String.valueOf(pressTotalJuri2.getSubTotal()));
        pressJuri3SubTot.setText(String.valueOf(pressTotalJuri3.getSubTotal()));

        //Total Bobot
        pressJuri1TotBot.setText(String.valueOf(pressTotalJuri1.getTotalBobot()));
        pressJuri2TotBot.setText(String.valueOf(pressTotalJuri2.getTotalBobot()));
        pressJuri3TotBot.setText(String.valueOf(pressTotalJuri3.getTotalBobot()));

        pressTotal.setText("Total : " + String.valueOf(scoreTeam.getFinalScore().getPresentation()));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.savePdf:
                try {
                    if (cd.isConnected()) {
                        iText(scoreTeam.getTeam());
                    } else {
                        Toast.makeText(this, "you must connection internet", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error storage", Toast.LENGTH_SHORT).show();
                } catch (DocumentException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Doc Error", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
