package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.trydev.scoreoceania.R;

import java.util.ArrayList;

import model.ScoreTeam;

/**
 * Created by root on 25/03/18.
 */

public class PositionTeamAdapter extends RecyclerView.Adapter<PositionTeamAdapter.ViewHolder>{

    ArrayList<ScoreTeam> scoreTeams;
    Context context;
    int count;
    OnItemClick onItemClick;

    public interface OnItemClick{
        void getItem(ScoreTeam scoreTeam);
    }

    public PositionTeamAdapter(Context context, OnItemClick onItemClick){
        this.context = context;
        count = 0;
        scoreTeams = new ArrayList<>();
        this.onItemClick = onItemClick;
    }

    public ArrayList<ScoreTeam> getScoreTeams() {
        return scoreTeams;
    }

    public void setScoreTeams(ArrayList<ScoreTeam> scoreTeams) {
        this.scoreTeams = scoreTeams;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.position_team, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.position.setText(String.valueOf(++count) + ".");
        holder.namaTeam.setText(scoreTeams.get(position).getTeam());
        holder.namaUniv.setText(scoreTeams.get(position).getUniversitas());
        holder.nilaiTotal.setText(String.format("%.2f", scoreTeams.get(position).getFinalScore().getTotal()));
        holder.nilaiWave.setText(String.format("%.2f", scoreTeams.get(position).getFinalScore().getWave()));
        holder.nilaiPlatform.setText(String.format("%.2f", scoreTeams.get(position).getFinalScore().getPlatform()));
        holder.nilaiInovation.setText(String.format("%.2f", scoreTeams.get(position).getFinalScore().getInovation()));
        holder.nilaiPresentation.setText(String.format("%.2f", scoreTeams.get(position).getFinalScore().getPresentation()));

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, scoreTeams.get(position).getKey(), Toast.LENGTH_SHORT).show();
                onItemClick.getItem(scoreTeams.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return getScoreTeams().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaTeam, namaUniv, nilaiPresentation, nilaiInovation, nilaiPlatform, nilaiWave,
                nilaiTotal, position;
        ImageView details;

        public ViewHolder(View itemView) {
            super(itemView);
            position = (TextView) itemView.findViewById(R.id.position);
            namaTeam = (TextView) itemView.findViewById(R.id.namaTeam);
            namaUniv = (TextView) itemView.findViewById(R.id.namaUniv);
            nilaiPresentation = (TextView) itemView.findViewById(R.id.nilaiPresentation);
            nilaiInovation = (TextView) itemView.findViewById(R.id.nilaiInovation);
            nilaiPlatform = (TextView) itemView.findViewById(R.id.nilaiPlatform);
            nilaiWave = (TextView) itemView.findViewById(R.id.nilaiWave);
            nilaiTotal = (TextView) itemView.findViewById(R.id.nilaiTotal);

            details = (ImageView) itemView.findViewById(R.id.details);
        }
    }

    public void setDataChange(ArrayList<ScoreTeam> scoreTeams){
        this.scoreTeams = scoreTeams;
        count = 0;
        notifyDataSetChanged();
    }
}
