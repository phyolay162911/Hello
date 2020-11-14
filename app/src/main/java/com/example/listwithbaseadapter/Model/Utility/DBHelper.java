package com.example.listwithbaseadapter.Model.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.listwithbaseadapter.Model.LanguageInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {


    private static final String DB_Name = "Language_DB";
    private static final Integer DB_Ver = 1;

    private String tbl_language = "tbl_language";
    private String colID = "ID";
    private String colName = "Name";
    private String colDesc = "Description";
    private String colReleaseDate = "ReleaseDate";
    SQLiteDatabase db;
    public DBHelper (Context context)
    {
        super(context,DB_Name,null,DB_Ver);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "Create TABLE " + tbl_language + "(" + colID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + colName + "TEXT ,"
                + colDesc + "TEXT ,"
                + colReleaseDate + "DATE)";

        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + tbl_language ;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);

    }

    public int Insert(LanguageInfo info)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colName,info.getName());
        values.put(colDesc,info.getDescription());
        values.put(colReleaseDate,info.getReleasedDate().toString());

        long result = db.insert(tbl_language,null,values);
        Cursor cur = db.rawQuery("select max("+colID+")ID from "+tbl_language,null);
        cur.moveToNext();
        int results = Integer.parseInt(cur.getString(0));
        return  results;
    }

    public int Update(LanguageInfo inf)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colName,inf.getName());
        values.put(colDesc,inf.getDescription());
        values.put(colReleaseDate,inf.getReleasedDate().toString());
        int result = db.update(tbl_language,values,colID + "= ?",new String[]{String.valueOf(inf.getId())});

        return  result;
    }

    public ArrayList<LanguageInfo> GetAll() throws ParseException {
        ArrayList<LanguageInfo> infolists = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        Cursor cur = db.rawQuery("SELECT * from " + tbl_language, null);

        while (cur.moveToNext()) {

            LanguageInfo info = new LanguageInfo();
            info.setId(Integer.parseInt(cur.getString(cur.getColumnIndex(colID))));
            info.setName(cur.getString(cur.getColumnIndex(colName)));
            info.setReleasedDate(new SimpleDateFormat("yyyy-MM-dd").parse(cur.getString(cur.getColumnIndex(colReleaseDate))));
            info.setDescription(cur.getString(cur.getColumnIndex(colDesc)));
            infolists.add(info);
        }


        return infolists;
    }

    public void Delete(LanguageInfo inf)
    {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("delete from "+tbl_language+" where " +colID+" ="+ inf.getId());

    }

}
