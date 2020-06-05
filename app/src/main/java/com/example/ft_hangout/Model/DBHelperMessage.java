package com.example.ft_hangout.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperMessage extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ft_hangout";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "messages";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_SENDER = "sender";
    public static final String COLUMN_INTERLOCUTOR = "interlocutor";
    private static final String DATABASE_CREATE = "create table " +
            TABLE_NAME + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_INTERLOCUTOR + " text not null," +
            COLUMN_SENDER + " integer, " +
            COLUMN_MESSAGE + " text not null);";

    public  DBHelperMessage(Context context){ super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
