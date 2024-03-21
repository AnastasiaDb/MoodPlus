package com.example.moodplus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodplus.db.MyDBManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity_information extends AppCompatActivity {

    private MyDBManager myDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_information);
        myDBManager = new MyDBManager(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        myDBManager.openDB();
        Boolean[] set = {false, false, false, false, false, false};

        Button happyButton = findViewById(R.id.happy);
        Button sadButton = findViewById(R.id.sad);
        Button shyButton = findViewById(R.id.shy);
        Button reverieButton = findViewById(R.id.reverie);
        Button angryButton = findViewById(R.id.angry);
        Button confidenceButton = findViewById(R.id.confidence);

        EditText editText = findViewById(R.id.editTextEmotion);

        if(myDBManager.hasTodayRecordAndInformation()){
            String text = myDBManager.getTodayInformation();
            editText.setText(text);
        }


        if (myDBManager.hasTodayRecordAndEmotion()) {
//            Date date = new Date();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            String strDate = formatter.format(date);
            String emotionsToday = myDBManager.getTodayEmotions();

            String[] emotion_arr = emotionsToday.split("\\s+");
            for (String x : emotion_arr) {
                if (x.equals("reverie")) {
                    set[0] = true;
                    reverieButton.setBackgroundResource(R.color.background_button_change);
                } else if (x.equals("happy")) {
                    happyButton.setBackgroundResource(R.color.background_button_change);
                    set[1] = true;
                } else if (x.equals("sad")) {
                    sadButton.setBackgroundResource(R.color.background_button_change);
                    set[2] = true;
                } else if (x.equals("shy")) {
                    shyButton.setBackgroundResource(R.color.background_button_change);
                    set[3] = true;
                } else if (x.equals("confidence")) {
                    confidenceButton.setBackgroundResource(R.color.background_button_change);
                    set[4] = true;
                } else if (x.equals("angry")) {
                    angryButton.setBackgroundResource(R.color.background_button_change);
                    set[5] = true;
                }
            }
        }


        Button okButton = findViewById(R.id.ok);


        // Устанавливаем обработчики нажатия на каждую кнопку
        reverieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!set[0]) {
                    // Устанавливаем цвет фона кнопки в зависимости от ее состояния
                    set[0] = true;
                    reverieButton.setBackgroundResource(R.color.background_button_change);
                } else {
                    set[0] = false;
                    reverieButton.setBackgroundResource(R.color.background_button);
                }
            }
        });

        // Устанавливаем обработчики нажатия на каждую кнопку
        happyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!set[1]) {
                    // Устанавливаем цвет фона кнопки в зависимости от ее состояния
                    set[1] = true;
                    happyButton.setBackgroundResource(R.color.background_button_change);
                } else {
                    set[1] = false;
                    happyButton.setBackgroundResource(R.color.background_button);
                }
            }
        });

        // Устанавливаем обработчики нажатия на каждую кнопку
        sadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!set[2]) {
                    // Устанавливаем цвет фона кнопки в зависимости от ее состояния
                    set[2] = true;
                    sadButton.setBackgroundResource(R.color.background_button_change);
                } else {
                    set[2] = false;
                    sadButton.setBackgroundResource(R.color.background_button);
                }
            }
        });
        // Устанавливаем обработчики нажатия на каждую кнопку
        shyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!set[3]) {
                    // Устанавливаем цвет фона кнопки в зависимости от ее состояния
                    set[3] = true;
                    shyButton.setBackgroundResource(R.color.background_button_change);
                } else {
                    set[3] = false;
                    shyButton.setBackgroundResource(R.color.background_button);
                }
            }
        });

        // Устанавливаем обработчики нажатия на каждую кнопку
        confidenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!set[4]) {
                    // Устанавливаем цвет фона кнопки в зависимости от ее состояния
                    set[4] = true;
                    confidenceButton.setBackgroundResource(R.color.background_button_change);
                } else {
                    set[4] = false;
                    confidenceButton.setBackgroundResource(R.color.background_button);
                }
            }
        });


        // Устанавливаем обработчики нажатия на каждую кнопку
        angryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!set[5]) {
                    // Устанавливаем цвет фона кнопки в зависимости от ее состояния
                    set[5] = true;
                    angryButton.setBackgroundResource(R.color.background_button_change);
                } else {
                    set[5] = false;
                    angryButton.setBackgroundResource(R.color.background_button);
                }
            }
        });


        StringBuilder emotions = new StringBuilder();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (set[0])
                    emotions.append(" reverie");
                if (set[1])
                    emotions.append(" happy");
                if (set[2])
                    emotions.append(" sad");
                if (set[3])
                    emotions.append(" shy");
                if (set[4])
                    emotions.append(" confidence");
                if (set[5])
                    emotions.append(" angry");
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = formatter.format(date);
                String res = emotions.toString();
                String information = editText.getText().toString();
                if (myDBManager.hasTodayRecord())
                    myDBManager.updateToDB_EmotionsAndInform(res, information, strDate);
                else
                    myDBManager.insertToDBEmotionsAndInform(res, information, strDate);
                mainActivity(view);

            }
        });


    }

    public void mainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
