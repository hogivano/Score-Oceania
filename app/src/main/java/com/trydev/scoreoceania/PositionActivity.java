package com.trydev.scoreoceania;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapter.PositionTeamAdapter;
import model.FinalScore;
import model.ScoreTeam;
import model.Team;

public class PositionActivity extends AppCompatActivity implements PositionTeamAdapter.OnItemClick{
    RecyclerView rvTeamPosisi;
    ProgressDialog progressDialog;
    DatabaseReference ref;

    PositionTeamAdapter positionTeamAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        ref = FirebaseDatabase.getInstance().getReference();

        rvTeamPosisi = (RecyclerView) findViewById(R.id.rvTeamPosisi);
        positionTeamAdapter = new PositionTeamAdapter(this, this);

        showProgressDialog();
        loadTeam();
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void loadTeam(){
        ref.child("final_score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Team> newTeam = new ArrayList<>();
//                ArrayList<FinalScore> finalScores = new ArrayList<>();
                ScoreTeam[] scoreTeam = new ScoreTeam[15];
                int sizeScore = 0;
                for (DataSnapshot mdataSnapshot : dataSnapshot.getChildren()){
                    scoreTeam[sizeScore] = new ScoreTeam();
                    scoreTeam[sizeScore].setKey(mdataSnapshot.getKey());
                    scoreTeam[sizeScore].setTeam(mdataSnapshot.child("nama").getValue(String.class));
                    scoreTeam[sizeScore].setUniversitas(mdataSnapshot.child("universitas").getValue(String.class));

                    FinalScore finalScore = mdataSnapshot.child("final").getValue(FinalScore.class);
                    scoreTeam[sizeScore].setFinalScore(finalScore);
                    sizeScore++;
                }
                if (sizeScore != 0) {
                    setAdapter(scoreTeam, sizeScore);
                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(PositionActivity.this, "check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter(ScoreTeam[] scoreTeams, int sizeScore){
        for (int i = 0; i < sizeScore; i++) {
            for (int j = 0; j < sizeScore-1; j++) {
                if (scoreTeams[j].getFinalScore().getTotal() < scoreTeams[j+1].getFinalScore().getTotal()){
                    ScoreTeam temp = scoreTeams[j+1];
                    scoreTeams[j+1] = scoreTeams[j];
                    scoreTeams[j] = temp;
                    Log.e("masuk", "if loop");
                }
            }
        }

        ArrayList<ScoreTeam> arrayList = new ArrayList<>();
        for (int i = 0; i < sizeScore; i++) {
            arrayList.add(scoreTeams[i]);
        }

        if (rvTeamPosisi.getAdapter() == null ){
//            if (arrayList.size() != 0) {
                positionTeamAdapter.setScoreTeams(arrayList);
                rvTeamPosisi.setLayoutManager(new LinearLayoutManager(this));
                rvTeamPosisi.setAdapter(positionTeamAdapter);
//            }
        } else {
//            if (arrayList.size() != 0) {
                positionTeamAdapter.setDataChange(arrayList);
//            }
        }

        progressDialog.dismiss();
    }

    @Override
    public void getItem(ScoreTeam scoreTeam) {
        Intent intent = new Intent(PositionActivity.this, DetailsActivity.class);
        intent.putExtra("scoring", scoreTeam);
        startActivity(intent);
    }
}
