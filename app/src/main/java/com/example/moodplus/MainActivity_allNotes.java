package com.example.moodplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.moodplus.date.DayMood;
import com.example.moodplus.db.MyDBManager;

import java.util.List;

public class MainActivity_allNotes extends AppCompatActivity {

    MyDBManager myDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_all_notes);

        myDBManager = new MyDBManager(this);
        myDBManager.openDB();

        List<DayMood> items = myDBManager.getFromDB();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(this,items));
     }

    public void newMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}