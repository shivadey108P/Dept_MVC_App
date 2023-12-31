package com.example.dept_mvc_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DEPTMSMVC";
    public CDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Dept (dno integer primary key " +
                "autoincrement, dname text, dloc text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Dept");
        onCreate(db);
    }

    public void addDept(CDept obj){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("dname",obj.dname);
            cv.put("dloc",obj.dloc);
            db.insert("Dept",null,cv);
            db.close();
        }catch (Exception e){
            System.out.println(e);
            Log.d("INSERT", e.toString());
        }
    }

    public CDept getOneDepartment(int dno){
        CDept obj = null;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query("Dept", new String[]{"dno",
                            "dname", "dloc"}, "dno" + "=?", new String[]{String.valueOf(dno)},
                    null, null, null, null);
            if(cursor!=null && cursor.getCount()!=0){
                cursor.moveToFirst();
                obj = new CDept();
                obj.dno = dno;
                obj.dname=cursor.getString(1);
                obj.dloc=cursor.getString(2);
            }
            else {
                obj = null;
            }
        }catch (Exception e){
            System.out.println(e);
            Log.d("SELECT:",e.toString());
        }
        return obj;
    }

    public int deleteDept(int dno){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Dept","dno=?",
                new String[]{String.valueOf(dno)});
    }

    public void update(int dno, String dn, String dl){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("dname",dn);
        values.put("dloc", dl);
        db.update("Dept",values,"dno=?",
                new String[]{String.valueOf(dno)});
        db.close();
    }

    public List<CDept> getAllValues(){
        List<CDept> recList = new ArrayList<CDept>();
        //Select All Query
        String selectQuery = "Select * from Dept";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        // Looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do {
                CDept rec = new CDept();
                rec.dno = Integer.parseInt(cursor.getString(0));
                rec.dname = cursor.getString(1);
                rec.dloc = cursor.getString(2);
                recList.add(rec);
            }while (cursor.moveToNext());
        }

        return recList;
    }


}
