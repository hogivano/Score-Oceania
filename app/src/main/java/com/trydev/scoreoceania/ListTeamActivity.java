package com.trydev.scoreoceania;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapter.TeamAdapter;
import model.Team;
import util.ConnectionDetector;

public class ListTeamActivity extends AppCompatActivity implements TeamAdapter.OnItemClick{
    RecyclerView rvTeam;
    LinearLayout llListTeam;
    DatabaseReference ref;
    DatabaseReference reference;


    TeamAdapter teamAdapter;
    ArrayList<Team> teams;

    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_team);
        cd = new ConnectionDetector(this);

        llListTeam = (LinearLayout) findViewById(R.id.llListTeam);
        teams = new ArrayList<>();
        progressDialog = new ProgressDialog(this);

        showProgressDialog();
        ref = FirebaseDatabase.getInstance().getReference().child("tim");
        reference = FirebaseDatabase.getInstance().getReference();

        loadTeam();
    }

    public void showAlertDialog(final Team team){
        builder = new AlertDialog.Builder(this);

        builder.setMessage("apakah anda yakin?");
        builder.setPositiveButton("ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (cd.isConnected()) {
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("tim").getChildrenCount() > 1 &&
                                    dataSnapshot.child("scoring").getChildrenCount() > 1 &&
                                    dataSnapshot.child("final_score").getChildrenCount() > 1) {
                                reference.child("tim").child(team.getKey()).removeValue();
                                reference.child("scoring").child(team.getKey()).removeValue();
                                reference.child("final_score").child(team.getKey()).removeValue();
                            } else {
                                Toast.makeText(ListTeamActivity.this, "harus menyisahkan 1 tim di setiap tabel", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(ListTeamActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(ListTeamActivity.this, "you must connection internet", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    private void showProgressDialog() {
        progressDialog.setMessage("please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void loadTeam(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList <Team> newTeam = new ArrayList<>();
                for (DataSnapshot mdataSnapshot : dataSnapshot.getChildren()){
                    Team team = mdataSnapshot.getValue(Team.class);
                    team.setKey(mdataSnapshot.getKey().toString());
                    newTeam.add(team);
                }
                teams = newTeam;
                Log.e("sizeIn", String.valueOf(teams.size()));
                setAdapter(newTeam);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setAdapter(ArrayList<Team> teams){
        rvTeam = (RecyclerView) findViewById(R.id.rvTeam);
        if (rvTeam.getAdapter() == null) {
//            if (teams.size() != 0) {
                teamAdapter = new TeamAdapter();
                teamAdapter.setContext(this);
                teamAdapter.setTeams(teams);
                teamAdapter.setCallback(this);
                rvTeam.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rvTeam.setAdapter(teamAdapter);
                llListTeam.setVisibility(View.VISIBLE);
//            }
            progressDialog.dismiss();
        } else {
//            if (teams.size() != 0) {
                teamAdapter.notifyDataChange(teams);
//            }
        }
    }

    @Override
    public void getTeam(Team team, String id) {
        Intent intent;
        switch (id){
            case "score":
                intent = new Intent(ListTeamActivity.this, ScoringActivity.class);
                intent.putExtra("team", team);
                startActivity(intent);
                break;
            case "edit":
                intent = new Intent(ListTeamActivity.this, EditTeamActivity.class);
                intent.putExtra("team", team);
                startActivity(intent);
                break;
            case "delete":
                showAlertDialog(team);
                break;
        }
    }
}

