package com.teiath.harrys.vquiz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class GlobalStatsAdapter extends ArrayAdapter<ScoreObject> {

    private static final String BEGINNER_MODE_TAG = "Beginner";
    private static final String INTERMEDIATE_MODE_TAG = "Intermediate";
    private static final String EXPERT_MODE_TAG = "Expert";

    public GlobalStatsAdapter(@NonNull Context context, @NonNull List<ScoreObject> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ScoreObject score = getItem(position);
        ScoreObjectViewHolder viewHolder;
        // Convert view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.global_stats_list_item, parent, false);
            viewHolder = new ScoreObjectViewHolder();

            viewHolder.logoImageView = convertView.findViewById(R.id.logo_id);
            viewHolder.usernameTextView = convertView.findViewById(R.id.username_txt);
            viewHolder.modeTextView = convertView.findViewById(R.id.mode_txt);
            viewHolder.scoreTextView = convertView.findViewById(R.id.score_txt);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ScoreObjectViewHolder) convertView.getTag();
        }

        if (score.getmMode().equals(BEGINNER_MODE_TAG))
            viewHolder.scoreTextView.setText("Score: " + score.getmScore() + "/10");
        else if (score.getmMode().equals(INTERMEDIATE_MODE_TAG))
            viewHolder.scoreTextView.setText("Score: " + score.getmScore() + "/13");
        else if (score.getmMode().equals(EXPERT_MODE_TAG))
            viewHolder.scoreTextView.setText("Score: " + score.getmScore() + "/15");
        else
            viewHolder.scoreTextView.setText("Score: " + score.getmScore() + "/10");

        viewHolder.logoImageView.setImageResource(score.getmIconType());
        viewHolder.usernameTextView.setText("Username: " + score.getmUsername());
        viewHolder.modeTextView.setText("Mode: " + score.getmMode());


        return convertView;
    }

    private static class ScoreObjectViewHolder {
        ImageView logoImageView;
        TextView usernameTextView;
        TextView modeTextView;
        TextView scoreTextView;
    }
}
