package com.trydev.scoreoceania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.Team;

public class ScoringActivity extends AppCompatActivity implements View.OnClickListener{
    Team team;
    DatabaseReference ref;

    Button presentation, inovation, platform, wave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);
        presentation = (Button) findViewById(R.id.presentation);
        inovation = (Button) findViewById(R.id.inovation);
        platform = (Button) findViewById(R.id.platform);
        wave = (Button) findViewById(R.id.wave);

        ref = FirebaseDatabase.getInstance().getReference();

        team = (Team) getIntent().getSerializableExtra("team");
        storeDatabase(team.getKey(), team.getNama(), team.getUniversitas());
        presentation.setOnClickListener(this);
        inovation.setOnClickListener(this);
        platform.setOnClickListener(this);
        wave.setOnClickListener(this);
//        Toast.makeText(this, team.getKey() + " " + team.getNama(), Toast.LENGTH_SHORT).show();
    }

    public void storeDatabase(final String key, final String team, final String univ){
        ref.child("scoring").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(key)){
                    Team tim = new Team();
                    tim.setNama(team);
                    tim.setUniversitas(univ);
                    ref.child("scoring").child(key).setValue(tim);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.presentation:
                intent = new Intent(ScoringActivity.this, PresentationActivity.class);
                intent.putExtra("key", team.getKey());
                startActivity(intent);
                break;
            case R.id.inovation:
                intent = new Intent(ScoringActivity.this, InovationActivity.class);
                intent.putExtra("key", team.getKey());
                startActivity(intent);
                break;
            case R.id.platform:
                intent = new Intent(ScoringActivity.this, PlatformActivity.class);
                intent.putExtra("key", team.getKey());
                startActivity(intent);
                break;
            case R.id.wave:
                intent = new Intent(ScoringActivity.this, WaveTestActivity.class);
                intent.putExtra("key", team.getKey());
                startActivity(intent);
                break;
        }
    }
}
