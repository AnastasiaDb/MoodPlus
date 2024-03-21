package com.example.moodplus.date;

import java.util.ArrayList;
import java.util.Calendar;

public class DayMood {
    private int advice;
    private String calendar;
    private int mood;

    private String emotions;

    private String notes;

    public DayMood(String calendar, int mood) {
        this.calendar = calendar;
        this.mood = mood;
        this.emotions = null;
        this.notes = null;
    }

    public DayMood(String calendar, int mood, String emotions, String notes, int advice) {
        this.calendar = calendar;
        this.mood = mood;
        this.emotions = emotions;
        this.notes = notes;
        this.advice = advice;
    }

    public DayMood(String calendar, int mood, String emotions, String notes) {
        this.calendar = calendar;
        this.mood = mood;
        this.emotions = emotions;
        this.notes = notes;
    }

    public DayMood(String calendar, String emotions) {
        this.calendar = calendar;
        this.mood = 0;
        this.emotions = emotions;
        this.notes = null;
    }

    public void addInform(String emotions, String notes) {
        this.emotions = emotions;
        this.notes = notes;
    }

    public void addInform(String emotions) {
        this.emotions = emotions;
    }

//    public void addInform(String notes) {
//        this.notes = notes;
//    }

    public void addInform() {
    }

    public int getMood() {
        return mood;
    }

    public String getCalendar() {
        return calendar;
    }

    public String getEmotions() {
        return emotions;
    }

    public String getNotes() {
        return notes;
    }

    public int getAdvice() {
        return advice;
    }
}
