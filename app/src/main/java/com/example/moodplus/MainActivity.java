package com.example.moodplus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.moodplus.date.DayMood;
import com.example.moodplus.db.MyDBManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyDBManager myDBManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDBManager = new MyDBManager(this);


        myDBManager.openDB();
        List<DayMood> values = myDBManager.getFromDB();
        for (int i = 0; i < values.size(); i++) {
            Log.d("SHAAA", "mood: " + values.get(i).getMood() + ", day: " + values.get(i).getCalendar() +
                    ",emotion " + values.get(i).getEmotions() + ",information " + values.get(i).getNotes()
                    + ",advice " + values.get(i).getAdvice());
        }

    }

    private void hideEverything() {
        findViewById(R.id.change).setVisibility(View.VISIBLE);
        findViewById(R.id.accept).setVisibility(View.INVISIBLE);
        findViewById(R.id.mood_scale).setVisibility(View.INVISIBLE);
        findViewById(R.id.imageView).setVisibility(View.INVISIBLE);
        findViewById(R.id.imageView2).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.textView)).setText("Вы уже отметили ваше самочувствие");
        // ((Button) findViewById(R.id.accept)).setText("Изменить");
    }

    private void showEverything() {
        int mood = myDBManager.getTodayMood();
        findViewById(R.id.change).setVisibility(View.INVISIBLE);
        findViewById(R.id.accept).setVisibility(View.VISIBLE);
        findViewById(R.id.mood_scale).setVisibility(View.VISIBLE);
        ((SeekBar) findViewById(R.id.mood_scale)).setProgress(mood);
        findViewById(R.id.imageView).setVisibility(View.VISIBLE);
        findViewById(R.id.imageView2).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.textView)).setText(R.string.mood);
        // ((Button) findViewById(R.id.accept)).setText("Изменить");
    }

    @Override
    protected void onResume() {
        super.onResume();


        myDBManager.openDB();

        findViewById(R.id.change).setVisibility(View.INVISIBLE);

        if (myDBManager.hasTodayRecordAndMood())
            hideEverything();

        if (myDBManager.hasAdviceRecordToday())
            findViewById(R.id.advice).setVisibility(View.INVISIBLE);


        findViewById(R.id.advice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
                if (myDBManager.hasTodayRecord())
                    myDBManager.updateToDB_Advice();
                else
                    myDBManager.insertToDB_Advice();
                findViewById(R.id.advice).setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEverything();
            }
        });

//        if (myDBManager.hasTodayRecordAndMood()) {
//            hideEverything();
//        } else {
        findViewById(R.id.accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = ((SeekBar) findViewById(R.id.mood_scale)).getProgress();
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = formatter.format(date);
                if (myDBManager.hasTodayRecord())
                    myDBManager.updateToDB_Mood(value, strDate);
                else
                    myDBManager.insertToDB_Mood(value, strDate);

                hideEverything();
            }
        });
        //}
        //myDBManager.closeDB();

        List<DayMood> values = myDBManager.getFromDB();
        for (int i = 0; i < values.size(); i++) {
            Log.d("HI!!!!!", "mood: " + values.get(i).getMood() + ", day: " + values.get(i).getCalendar() +
                    ",emotion " + values.get(i).getEmotions() + ",information " + values.get(i).getNotes()
                    + ",advice " + values.get(i).getAdvice());
        }

        //  myDBManager.closeDB();
    }

    private void showAlertDialog() {
        // Создание AlertDialog.Builder

        String randomLine = Parser_text.getRandomLineFromRaw(MainActivity.this, R.raw.advice);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Совет дня").setMessage(randomLine);

        // Добавление кнопки "OK"
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Закрыть диалоговое окно
                dialogInterface.dismiss();
            }
        });

        // Создание и отображение диалогового окна
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void newMain(View view) {
        Intent intent = new Intent(this, MainActivity_graph.class);
        startActivity(intent);
    }

    public void newMain_Inform(View view) {
        Intent intent = new Intent(this, MainActivity_information.class);
        startActivity(intent);
    }

    public void newMain_AllNotes(View view) {
        Intent intent = new Intent(this, MainActivity_allNotes.class);
        startActivity(intent);
    }
}