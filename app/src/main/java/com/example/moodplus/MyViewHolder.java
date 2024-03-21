package com.example.moodplus;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView date, note, mood;
    Button emotion;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.date);
        note = itemView.findViewById(R.id.note);
        mood = itemView.findViewById(R.id.mood);
        emotion = itemView.findViewById(R.id.emotion);
    }
}
