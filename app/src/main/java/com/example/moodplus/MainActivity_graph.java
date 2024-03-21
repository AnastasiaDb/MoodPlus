package com.example.moodplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.example.moodplus.date.DayMood;
import com.example.moodplus.db.MyDBManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
//import com.kizitonwose.*;


public class MainActivity_graph extends AppCompatActivity {
    //    private LineChart lineChart;
    private Button button;
    private MyDBManager myDBManager;
    private List<DayMood> values;
    //   Calendar calendar;
    CalendarView calendarView;
    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_graph);


        myDBManager = new MyDBManager(this);
        myDBManager.openDB();
        chart = findViewById(R.id.chart);

        setupEmptyChart();

    }


    @Override
    protected void onResume() {

        super.onResume();

        Button seven_days = findViewById(R.id.seven_days);
        Button thirty_days = findViewById(R.id.thirty_days);

        findViewById(R.id.seven_days).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // seven_days.setBackgroundResource(R.color.background_button_change);
                // thirty_days.setBackgroundResource(R.color.background_button);
                values = myDBManager.getFromDB_7();
                // Collections.reverse(values);
                fillChartWithData(7);

            }
        });

        findViewById(R.id.thirty_days).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // thirty_days.setBackgroundResource(R.color.background_button_change);
                // seven_days.setBackgroundResource(R.color.background_button);
                values = myDBManager.getFromDB_30();
                fillChartWithData(30);

            }
        });


    }

    private void fillChartWithData(int maxX) {
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> datesToCheck = generateDatesToCheck(maxX);

        int index = 0;
        int nullIndex = 0;
        for (String date : datesToCheck) {
            // Проверяем, есть ли текущая дата в списке данных
            if (index < values.size() && values.get(index).getCalendar().equals(date)) {
                DayMood mood = values.get(index);
                if (mood.getMood() != -1 && mood.getCalendar() != null) {
                    entries.add(new Entry(nullIndex + 1, mood.getMood()));
                }
                index++;
           }
 //           else {
//                // Если дата отсутствует в списке данных, пропускаем точку на графике
//                entries.add(new Entry(nullIndex + 1, Float.NaN)); // Float.NaN - это специальное значение для пропуска точки
//            }
            nullIndex++;
        }

//        for (int i = 0; i < values.size(); i++) {
//            if (values.get(i).getMood() != -1)
//                entries.add(new Entry(i + 1, values.get(i).getMood()));
//        }

        // Получаем набор данных графика и обновляем его
        LineData data = chart.getData();
        LineDataSet dataset = (LineDataSet) data.getDataSetByIndex(0);

        dataset.setValues(entries);


        // цвет
        dataset.setColor(Color.rgb(199, 21, 133));
        dataset.setCircleRadius(5f);
        //dataset.setCircleColor(Color.BLACK);
        // График будет плавным
        dataset.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

        // График будет заполненным
        dataset.setDrawFilled(true);

        // График будет анимироваться 0.5 секунды
        chart.animateY(500);

        // Настройка оси x
        XAxis xAxis = chart.getXAxis();
        xAxis.setAxisMinimum(1);
        xAxis.setAxisMaximum(maxX);
        xAxis.setDrawGridLines(false);

        // Создадим переменную данных для графика
        data = new LineData(dataset);
        // Передадим данные для графика в сам график
        chart.setData(data);


        // Уведомляем график о изменениях в данных
        // chart.notifyDataSetChanged();
        // Не забудем отправить команду на перерисовку кадра, чтобы обновить график
        chart.invalidate();
    }

    private void setupEmptyChart() {
        // Массив координат точек (пустой)
        ArrayList<Entry> entries = new ArrayList<>();

        // На основании массива точек создадим первую линию с названием
        LineDataSet dataset = new LineDataSet(entries, "График настроения");

        // цвет
        dataset.setColor(Color.rgb(199, 21, 133));

        // График будет заполненным
        dataset.setDrawFilled(true);

        // Настройка оси y
        YAxis yAxis = chart.getAxisLeft(); // Получаем ось y слева
        yAxis.setAxisMinimum(0f); // Устанавливаем минимальное значение оси y
        yAxis.setAxisMaximum(10f); // Устанавливаем максимальное значение оси y
        yAxis.setDrawGridLines(false);

//        XAxis xAxis = chart.getXAxis();
//        xAxis.setDrawGridLines(false);

//        // Настройка оси x
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setAxisMinimum(1f);
//        xAxis.setAxisMaximum(7f);

        // Создадим переменную данных для графика
        LineData data = new LineData(dataset);
        // Передадим данные для графика в сам график
        chart.setData(data);

        // Не забудем отправить команду на перерисовку кадра, иначе график не отобразится
        chart.invalidate();
    }

    private ArrayList<String> generateDatesToCheck(int maxX) {
        ArrayList<String> dates = new ArrayList<>();
        // Получаем текущую дату
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Добавляем даты за последние maxX дней в список
        for (int i = maxX - 1; i >= 0; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_YEAR, -i);
            dates.add(dateFormat.format(calendar.getTime()));
        }
        return dates;
    }

    public void newMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}