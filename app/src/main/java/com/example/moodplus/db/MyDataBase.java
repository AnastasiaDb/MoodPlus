package com.example.moodplus.db;

public class MyDataBase {
    public static final String TABLE_NAME = "Mood";
    public static final String _ID = "_id";
    public static final String _MOOD = "_mood";
    public static final String _DATE = "_date";

    public static final String _EMOTIONS = "_emotions";

    public static final String _INFORMATION = "_information";

    public static final String _IS_GIVEN_ADVICE = "_is_given_advice";

    public static final String DB_NAME = "mood_db.db";
    public static final int DB_VERSION = 1;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY, " + _MOOD + " INTEGER,"
            + _DATE + " VARCHAR(100), " + _EMOTIONS + " VARCHAR(100), " + _INFORMATION + " VARCHAR(300), "
            + _IS_GIVEN_ADVICE + " INTEGER DEFAULT 0)";


    public static final String GET_FROM_DATABASE =
            " SELECT * " +
                    " FROM " + TABLE_NAME +
                    " WHERE " + _DATE + " BETWEEN date(now(), INTERVAL ? WEEK) and date_sub(now(), INTERVAL ? WEEK)" +
                    " ORDER BY " + _DATE + ";";

    public static final String GET_TODAY_RECORD =
            " SELECT * " +
                    " FROM " + TABLE_NAME +
                    " WHERE " + _DATE + " = date('now');";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}

