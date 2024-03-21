package com.example.moodplus.db;

import static com.example.moodplus.db.MyDataBase.TABLE_NAME;
import static com.example.moodplus.db.MyDataBase._DATE;
import static com.example.moodplus.db.MyDataBase._EMOTIONS;
import static com.example.moodplus.db.MyDataBase._INFORMATION;
import static com.example.moodplus.db.MyDataBase._IS_GIVEN_ADVICE;
import static com.example.moodplus.db.MyDataBase._MOOD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.moodplus.date.DayMood;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyDBManager {
    private Context context;
    private MyDBHelper myDBHelper;
    private SQLiteDatabase db;

    public MyDBManager(Context context) {
        this.context = context;
        myDBHelper = new MyDBHelper(context);
    }

    public void openDB() {
        db = myDBHelper.getWritableDatabase();
    }

    public void insertToDB_Mood(int mood, String date) {
        ContentValues cv = new ContentValues();
        cv.put(_MOOD, mood);
        cv.put(MyDataBase._DATE, date);
        db.insert(TABLE_NAME, null, cv);
    }

    public void insertToDBEmotionsAndInform(String emotions, String information, String date) {
        ContentValues cv = new ContentValues();
        cv.put(MyDataBase._DATE, date);
        cv.put(MyDataBase._MOOD, -1);
        cv.put(_EMOTIONS, emotions);
        cv.put(_INFORMATION, information);
        db.insert(TABLE_NAME, null, cv);
    }

    public void insertToDB_Advice() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDateString = dateFormat.format(new Date());

        ContentValues cv = new ContentValues();
        cv.put(MyDataBase._MOOD, -1);
        cv.put(_IS_GIVEN_ADVICE, 1);
        cv.put(MyDataBase._DATE, todayDateString);
        db.insert(TABLE_NAME, null, cv);
    }

    public void updateToDB_Mood(int mood, String date) {
        ContentValues cv = new ContentValues();
        cv.put(_MOOD, mood);

        String whereClause = MyDataBase._DATE + "=?";
        String[] whereArgs = {date};

        db.update(TABLE_NAME, cv, whereClause, whereArgs);
    }

    public void updateToDB_EmotionsAndInform(String emotions, String information, String date) {
        ContentValues cv = new ContentValues();
        // cv.put(MyDataBase._DATE, date);
        cv.put(_EMOTIONS, emotions);
        cv.put(_INFORMATION, information);

        String whereClause = MyDataBase._DATE + "=?";
        String[] whereArgs = {date};

        db.update(TABLE_NAME, cv, whereClause, whereArgs);
    }

    public void updateToDB_Advice() {
        ContentValues cv = new ContentValues();
        cv.put(_IS_GIVEN_ADVICE, 1);  // Устанавливаем флаг совета для сегодняшней даты

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDateString = dateFormat.format(new Date());

        // Обновляем запись в базе данных, устанавливая флаг совета для сегодняшней даты
        String whereClause = _DATE + "=?";
        String[] whereArgs = {todayDateString};
        db.update(TABLE_NAME, cv, whereClause, whereArgs);
    }



    public String getTodayEmotions() {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);

        // Параметризированный SQL-запрос для выборки записей за сегодняшний день
        String[] selectionArgs = {strDate};
        Cursor cursor = db.rawQuery("SELECT " + _EMOTIONS + " FROM " + TABLE_NAME + " WHERE " + _DATE + " = ?", selectionArgs);

        String emotions = "";

        // Проверяем, есть ли строки в курсоре
        if (cursor.moveToFirst()) {
            // Получаем эмоции из курсора
            emotions = cursor.getString(cursor.getColumnIndexOrThrow(_EMOTIONS));
        }

        cursor.close();
        return emotions;
    }

    public String getTodayInformation() {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);

        // Параметризированный SQL-запрос для выборки записей за сегодняшний день
        String[] selectionArgs = {strDate};
        Cursor cursor = db.rawQuery("SELECT " + _INFORMATION + " FROM " + TABLE_NAME + " WHERE " + _DATE + " = ?", selectionArgs);

        String information = "";

        // Проверяем, есть ли строки в курсоре
        if (cursor.moveToFirst()) {
            // Получаем эмоции из курсора
            information = cursor.getString(cursor.getColumnIndexOrThrow(_INFORMATION));
        }

        cursor.close();
        return information;
    }

    public int getTodayMood() {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);

        // Параметризированный SQL-запрос для выборки записей за сегодняшний день
        String[] selectionArgs = {strDate};
        Cursor cursor = db.rawQuery("SELECT " + _MOOD + " FROM " + TABLE_NAME + " WHERE " + _DATE + " = ?", selectionArgs);

        int mood = -1;

        // Проверяем, есть ли строки в курсоре
        if (cursor.moveToFirst()) {
            // Получаем эмоции из курсора
             mood = cursor.getInt(cursor.getColumnIndexOrThrow(_MOOD));
        }

        cursor.close();
        return mood;
    }

    public List<DayMood> getFromDB() {
        List<DayMood> tempList = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null,
                null, null, null);

        while (cursor.moveToNext()) {
            int mood = cursor.getInt(cursor.getColumnIndexOrThrow(_MOOD));
            String information = cursor.getString(cursor.getColumnIndexOrThrow(_INFORMATION));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(MyDataBase._DATE));
            String emotions = cursor.getString(cursor.getColumnIndexOrThrow(_EMOTIONS));
            int advice = cursor.getInt(cursor.getColumnIndexOrThrow(_IS_GIVEN_ADVICE));
            tempList.add(new DayMood(date, mood, emotions, information,advice));
        }
        cursor.close();
        return tempList;
    }

    public List<DayMood> getFromDB_7() {

        List<DayMood> tempList = new ArrayList<>();

        String sqlQuery = "SELECT * FROM " + TABLE_NAME +
                " WHERE _date BETWEEN date('now', '-6 days') AND date('now')" +
                " ORDER BY _date";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        while (cursor.moveToNext()) {
            int mood = cursor.getInt(cursor.getColumnIndexOrThrow(_MOOD));
            String information = cursor.getString(cursor.getColumnIndexOrThrow(_INFORMATION));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(MyDataBase._DATE));
            String emotions = cursor.getString(cursor.getColumnIndexOrThrow(_EMOTIONS));
            tempList.add(new DayMood(date, mood, emotions, information));
        }
        cursor.close();
        return tempList;
    }

    public List<DayMood> getFromDB_30() {
        List<DayMood> tempList = new ArrayList<>();

        String[] arr = {};

        String sqlQuery = "SELECT * FROM " + TABLE_NAME +
                " WHERE _date BETWEEN date('now', '-29 days') AND date('now')" +
                " ORDER BY _date";

        Cursor cursor = db.rawQuery(sqlQuery, null);

        while (cursor.moveToNext()) {
            int mood = cursor.getInt(cursor.getColumnIndexOrThrow(_MOOD));
            String information = cursor.getString(cursor.getColumnIndexOrThrow(_INFORMATION));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(MyDataBase._DATE));
            String emotions = cursor.getString(cursor.getColumnIndexOrThrow(_EMOTIONS));
            tempList.add(new DayMood(date, mood, emotions, information));
        }
        cursor.close();
        return tempList;
    }


    public Boolean hasTodayRecordAndMood() {

        // Получаем сегодняшнюю дату в формате, который используется в вашей базе данных
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDateString = dateFormat.format(new Date());

        // Параметризированный SQL-запрос с условием на дату и непустое значение в поле _MOOD
        String[] selectionArgs = {todayDateString};
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + _DATE + " = ? AND " + _MOOD + " IS NOT NULL AND "
                + _MOOD + "!= ''"+" AND " + _MOOD + "!=-1" , selectionArgs);

        boolean hasNonEmptyMood = cursor.getCount() > 0;

        cursor.close();

        return hasNonEmptyMood;
//        String[] arr = {};
//        Cursor cursor = db.rawQuery(GET_TODAY_RECORD, arr);
//
//        if (cursor.getCount() != 0) {
//            cursor.close();
//            return true;
//        } else {
//            cursor.close();
//            return false;
//        }
    }

    public Boolean hasTodayRecordAndEmotion() {

        // Получаем сегодняшнюю дату в формате, который используется в вашей базе данных
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDateString = dateFormat.format(new Date());

        // Параметризированный SQL-запрос с условием на дату и непустое значение в поле _MOOD
        String[] selectionArgs = {todayDateString};
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + _DATE + " = ? AND " + _EMOTIONS + " IS NOT NULL AND "
                + _EMOTIONS + " != ''", selectionArgs);

        boolean hasNonEmptyEmo = cursor.getCount() > 0;

        cursor.close();

        return hasNonEmptyEmo;
    }

    public Boolean hasTodayRecordAndInformation() {

        // Получаем сегодняшнюю дату в формате, который используется в вашей базе данных
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDateString = dateFormat.format(new Date());

        // Параметризированный SQL-запрос с условием на дату и непустое значение в поле _MOOD
        String[] selectionArgs = {todayDateString};
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + _DATE + " = ? AND " + _INFORMATION + " IS NOT NULL AND "
                + _INFORMATION + " != ''", selectionArgs);

        boolean hasNonEmptyEmo = cursor.getCount() > 0;

        cursor.close();

        return hasNonEmptyEmo;
    }

    public Boolean hasTodayRecord() {

        // Получаем сегодняшнюю дату в формате, который используется в вашей базе данных
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDateString = dateFormat.format(new Date());

        // Параметризированный SQL-запрос с условием на дату и непустое значение в поле _MOOD
        String[] selectionArgs = {todayDateString};
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + _DATE + " = ? ", selectionArgs);

        boolean hasNonEmptyEmo = cursor.getCount() > 0;

        cursor.close();

        return hasNonEmptyEmo;
    }


    public void closeDB() {
        myDBHelper.close();
    }

//    public List<DayMood> getFromDB_all() {
//        List<DayMood> tempList = new ArrayList<>();
//
//        String[] arr = {};
//        Cursor cursor = db.rawQuery("SELECT *  FROM Mood  ORDER BY _date", arr);
//
//        while (cursor.moveToNext()) {
//            int mood = cursor.getInt(cursor.getColumnIndexOrThrow(MyDataBase._MOOD));
//            String date = cursor.getString(cursor.getColumnIndexOrThrow(MyDataBase._DATE));
//            tempList.add(new DayMood(date, mood));
//        }
//        cursor.close();
//        return tempList;
//    }


    public boolean hasAdviceRecordToday() {
        // Получаем сегодняшнюю дату в формате, который используется в вашей базе данных
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDateString = dateFormat.format(new Date());

        String[] selectionArgs = {todayDateString};
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + _DATE + " = ? AND " + _IS_GIVEN_ADVICE + " = 1", selectionArgs);

        boolean isAdviceGiven = cursor.getCount() > 0;

        cursor.close();

        return isAdviceGiven;
    }

}
