package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.trydev.scoreoceania.R;
import com.trydev.scoreoceania.ScoringActivity;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

import model.Team;

/**
 * Created by root on 24/03/18.
 */

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    private ArrayList<Team> teams;
    private Context context;
    private ChildEventListener mChiledEventListener;
    private DatabaseReference ref;
    OnItemClick callback;

    public interface OnItemClick{
        void getTeam(Team team, String id);
    }

    public TeamAdapter(){
        teams = new ArrayList<>();
    }

    public void setCallback(OnItemClick callback) {
        this.callback = callback;
    }

    public OnItemClick getCallback() {
        return callback;
    }

    public void setRef(DatabaseReference ref) {
        this.ref = ref;
    }

    public DatabaseReference getRef() {
        return ref;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_team, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.namaTeam.setText(getTeams().get(position).getNama());
        holder.namaUniv.setText(getTeams().get(position).getUniversitas());

        holder.score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.getTeam(getTeams().get(position), "score");
//                Toast.makeText(context, getTeams().get(position).getKey(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.getTeam(getTeams().get(position), "delete");
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.getTeam(getTeams().get(position), "edit");
            }
        });
    }

    @Override
    public int getItemCount() {
        return getTeams().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaTeam, namaUniv;
        Button score, delete, edit;
        public ViewHolder(View itemView) {
            super(itemView);
            namaTeam = (TextView) itemView.findViewById(R.id.listTeam);
            namaUniv = (TextView) itemView.findViewById(R.id.listUniv);
            score = (Button) itemView.findViewById(R.id.listScore);
            delete = (Button) itemView.findViewById(R.id.delete);
            edit = (Button) itemView.findViewById(R.id.edit);
        }
    }

    public void notifyDataChange(ArrayList<Team> teams){
        this.teams = teams;
        notifyDataSetChanged();
    }
}
