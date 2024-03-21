package com.example.moodplus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moodplus.date.DayMood;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {


    Context context;
    List<DayMood> items;

    int mood;

    public MyAdapter(Context context, List<DayMood> items) {
        this.context = context;
        this.items = items;
        Collections.reverse(items);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_for_notes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String note = items.get(position).getEmotions();
        holder.emotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String note = items.get(position).getEmotions();
                String noteText = parseEmotion(note);
                showDialog(noteText);

            }
        });

        holder.date.setText(items.get(position).getCalendar());
        holder.note.setText(items.get(position).getNotes());
        holder.mood.setText(String.valueOf(items.get(position).getMood()));

        mood = items.get(position).getMood();
        if (mood == -1) {
            holder.mood.setText("-");
            holder.mood.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else if (mood == 0)
            holder.mood.setBackgroundColor(ContextCompat.getColor(context, R.color.mood_0));
        else if (mood == 1)
            holder.mood.setBackgroundColor(ContextCompat.getColor(context, R.color.mood_1));
        else if (mood == 2)
            holder.mood.setBackgroundColor(ContextCompat.getColor(context, R.color.mood_2));
        else if (mood == 3)
            holder.mood.setBackgroundColor(ContextCompat.getColor(context, R.color.mood_3));
        else if (mood == 4)
            holder.mood.setBackgroundColor(ContextCompat.getColor(context, R.color.mood_4));
        else if (mood == 5)
            holder.mood.setBackgroundColor(ContextCompat.getColor(context, R.color.mood_5));
        else if (mood == 6)
            holder.mood.setBackgroundColor(ContextCompat.getColor(context, R.color.mood_6));
        else if (mood == 7)
            holder.mood.setBackgroundColor(ContextCompat.getColor(context, R.color.mood_7));
        else if (mood == 8)
            holder.mood.setBackgroundColor(ContextCompat.getColor(context, R.color.mood_8));
        else if (mood == 9)
            holder.mood.setBackgroundColor(ContextCompat.getColor(context, R.color.mood_9));
        else if (mood == 10)
            holder.mood.setBackgroundColor(ContextCompat.getColor(context, R.color.mood_10));


    }

    // Метод для отображения всплывающего окна с текстом заметки
    private void showDialog(String noteText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Эмоции")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        if (noteText == null || noteText.equals(""))
            builder.setMessage("Вы не выбрали эмоции");
        else
            builder.setMessage(noteText);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private String parseEmotion(String noteText) {

        if (noteText != null) {
            StringBuilder res = new StringBuilder();
            int i = 1;
            String[] emotion_arr = noteText.split("\\s+");
            for (String x : emotion_arr) {
                if (x.equals("reverie")) {
                    res.append(i).append(".Задумчивость\n");
                    i++;
                }
                if (x.equals("happy")) {
                    res.append(i).append(".Веселье\n");
                    i++;
                }
                if (x.equals("sad")) {
                    res.append(i).append(".Грусть\n");
                    i++;
                }
                if (x.equals("shy")) {
                    res.append(i).append(".Смущение\n");
                    i++;
                }
                if (x.equals("confidence")) {
                    res.append(i).append(".Уверенность\n");
                    i++;
                }
                if (x.equals("angry")) {
                    res.append(i).append(".Злость\n");
                    i++;
                }
            }
            return res.toString();
        } else return null;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
