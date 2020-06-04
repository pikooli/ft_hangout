package com.example.ft_hangout.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ft_hangout";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "contacts";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_FIRSTNAME = "firstName";
    public static final String COLUMN_SECONDNAME = "secondName";
    public static final String COLUMN_RESIDENCE = "residence";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_NOTE = "note";

    private static final String DATABASE_CREATE = "create table " +
            TABLE_NAME + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_NAME + " text not null, " +
            COLUMN_FIRSTNAME + " text not null," +
            COLUMN_SECONDNAME + " text not null," +
            COLUMN_RESIDENCE + " text not null," +
            COLUMN_NUMBER + " text not null," +
            COLUMN_EMAIL + " text not null," +
            COLUMN_NOTE + " text not null);";

    public  DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
