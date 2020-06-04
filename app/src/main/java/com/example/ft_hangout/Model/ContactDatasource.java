package com.example.ft_hangout.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactDatasource implements Serializable {
    private SQLiteDatabase mDatabase;
    private Context mcontext;
    private DBHelper mdbHelper;
    private String[] allColumns = {
            DBHelper.COLUMN_ID,
            DBHelper.COLUMN_NAME,
            DBHelper.COLUMN_FIRSTNAME,
            DBHelper.COLUMN_SECONDNAME,
            DBHelper.COLUMN_RESIDENCE,
            DBHelper.COLUMN_NUMBER,
            DBHelper.COLUMN_EMAIL,
            DBHelper.COLUMN_NOTE};

    public ContactDatasource(Context context){
        mcontext = context;
        mdbHelper = new DBHelper(context);
        mDatabase = mdbHelper.getWritableDatabase();
    }

    public void open() throws SQLException{
        mDatabase = mdbHelper.getWritableDatabase();
    }

    public  void close(){
        mdbHelper.close();
    }

    public void createContact(String firstName, String secondName , String residence , String number ,
                                 String email, String note)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, firstName + " " + secondName);
        values.put(DBHelper.COLUMN_FIRSTNAME, firstName);
        values.put(DBHelper.COLUMN_SECONDNAME, secondName);
        values.put(DBHelper.COLUMN_RESIDENCE, residence);
        values.put(DBHelper.COLUMN_NUMBER, number);
        values.put(DBHelper.COLUMN_EMAIL, email);
        values.put(DBHelper.COLUMN_NOTE, note);
        long insertID = mDatabase.insert(DBHelper.TABLE_NAME , null, values);
        mDatabase.query(DBHelper.TABLE_NAME, allColumns, DBHelper.COLUMN_ID + " = " + insertID,
                null, null, null, null);
    }

    public Contact getContact(String id)
    {
        Contact contact = new Contact();
        Cursor cursor = mDatabase.query(DBHelper.TABLE_NAME, allColumns,
                DBHelper.COLUMN_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        cursorToContact(cursor, contact);
        return contact;
    }

    public void updateContact(String firstName, String secondName , String residence , String number ,
                              String email, String note, String id)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, firstName + " " + secondName);
        values.put(DBHelper.COLUMN_FIRSTNAME, firstName);
        values.put(DBHelper.COLUMN_SECONDNAME, secondName);
        values.put(DBHelper.COLUMN_RESIDENCE, residence);
        values.put(DBHelper.COLUMN_NUMBER, number);
        values.put(DBHelper.COLUMN_EMAIL, email);
        values.put(DBHelper.COLUMN_NOTE, note);
        mDatabase.update(DBHelper.TABLE_NAME, values,
                DBHelper.COLUMN_ID + "=" + id, null);
    }

    public void deleteContact(String id){
        mDatabase.delete(DBHelper.TABLE_NAME, DBHelper.COLUMN_ID + " = " + id,
                null);
    }

    public List<MiniContact> getAllContact(){
        ArrayList<MiniContact> contacts = new ArrayList<>();
        Cursor cursor = mDatabase.query(DBHelper.TABLE_NAME,new String[]{
                DBHelper.COLUMN_ID, DBHelper.COLUMN_NAME, DBHelper.COLUMN_NUMBER},
        null, null, null, null ,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            contacts.add(cursorToMinicontact(cursor, new MiniContact()));
            cursor.moveToNext();
        }
        cursor.close();
        return contacts;
    }
    private void cursorToContact(Cursor cursor,  Contact contact){
        contact.setId((int) cursor.getInt(0));
        contact.setName(cursor.getString(1));
        contact.setFirstName(cursor.getString(2));
        contact.setSecondName(cursor.getString(3));
        contact.setResidence(cursor.getString(4));
        contact.setNumber(cursor.getString(5));
        contact.setEmail(cursor.getString(6));
        contact.setNote(cursor.getString(7));
    }


    private MiniContact cursorToMinicontact(Cursor cursor, MiniContact miniContact){
        miniContact.setId((int) cursor.getInt(0));
        miniContact.setName(cursor.getString(1));
        miniContact.setNumber(cursor.getString(2));
        return miniContact;
    }

    public void printContact(List<MiniContact> list){
        printError("size = " + list.size());
        for(int i=0; i < list.size(); i++){
            printError(list.get(i).getName() + i);
        }
    }

    public void print(List<Integer> list){
        printError("size = " + list.size());
        for(int i=0; i < list.size(); i++){
            printError(list.get(i));
        }
    }

    public void printError(String txt)
    {
        Toast.makeText(mcontext, "ERROR : " + txt , Toast.LENGTH_SHORT).show();
    }

    public void printError(int txt)
    {
        Toast.makeText(mcontext, "ERROR : " + txt , Toast.LENGTH_SHORT).show();
    }


}
