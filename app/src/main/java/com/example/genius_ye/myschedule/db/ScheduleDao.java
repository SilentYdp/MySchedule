package com.example.genius_ye.myschedule.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.genius_ye.myschedule.domain.ScheduleInfo;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class ScheduleDao {
    private ScheduleDBOpenHelper helper;

    public ScheduleDao(Context context){
        helper=new ScheduleDBOpenHelper(context);
    }

    //添加逻辑实现
    public boolean add(String date,String name,String plan){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("date",date);
        values.put("name",name);
        values.put("schedule",plan);
        long row=db.insert("info",null,values);
        db.close();
        return row==-1?false:true;
    }

    //add重载
    public boolean add(ScheduleInfo info){
        return add(info.getDay(),info.getName(),info.getPlan());
    }

    //删除逻辑实现
    public boolean delete(String name){
        SQLiteDatabase db=helper.getWritableDatabase();
        int count=db.delete("info","name=?",new String[]{name});
        db.close();
        return count<=0?false:true;
    }

    //查询记录
    public Map<String ,String> getinfo(int position){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("info",new String[]{"date","name","schedule"},null,null,null,null,null);
        cursor.moveToPosition(position);
        String date=cursor.getString(0);
        String name=cursor.getString(1);
        String plan=cursor.getString(2);
        cursor.close();
        db.close();

        HashMap<String,String> result=new HashMap<>();
        result.put("date",date);
        result.put("name",name);
        result.put("plan",plan);
        return result;
    }

    //查询一共有多少条记录
    public int getTotalcount(){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("info",null,null,null,null,null,null,null);
        int count=cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    //删除所有的数据
    public void deleteAll(){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.beginTransaction();
        try{
            Cursor cursor=db.query("info",new String[]{"name"},null,null,null,null,null,null);
            while(cursor.moveToNext()){
                String name=cursor.getString(0);
                db.delete("info","name=?",new String[]{name});
            }
            cursor.close();
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
            db.close();
        }
    }




}
