package com.example.genius_ye.myschedule.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScheduleDBOpenHelper extends SQLiteOpenHelper {
    public ScheduleDBOpenHelper( Context context) {
        super(context, "Schedule.db", null, 1);
    }

    //数据库第一次创建调用的方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table info (personid integer primary key autoincrement,date varchar(30),name varchar(20),schedule varchar(200));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
