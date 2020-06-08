package com.example.ft_hangout.Control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ft_hangout.Model.Contact;
import com.example.ft_hangout.Model.ContactDatasource;
import com.example.ft_hangout.R;

import java.util.Calendar;
import java.util.Date;

public class AddContactActivity extends AppCompatActivity {
    private ContactDatasource datasource;
    private TextView title;
    private  EditText firstName;
    private  EditText secondName;
    private  EditText residence;
    private  EditText number;
    private  EditText email;
    private  EditText note;
    private Button delete;
    private  String id;
    private Contact contact;
    private View header;
    private Date currentTime = null;
    private SharedPreferences mPreferences;
    private String MYPREFERENCE = "hangout";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Intent intent = getIntent();
        title = (TextView) findViewById(R.id.title);
        firstName = (EditText) findViewById(R.id.firstName);
        secondName = (EditText) findViewById(R.id.SecondName);
        residence = (EditText) findViewById(R.id.Residence);
        number = (EditText) findViewById(R.id.Number);
        email = (EditText) findViewById(R.id.Email);
        note = (EditText) findViewById(R.id.Note);
        header = (TextView) findViewById(R.id.title);
        delete = (Button) findViewById(R.id.delete);
        datasource = new ContactDatasource(this);
        mPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
        header.setBackgroundResource(mPreferences.getInt("color", R.color.white));

        getIntent();
        id = getIntent().getStringExtra("id");
        if (id.length() > 0)
            edit();
        else
            delete.setVisibility(View.GONE);
    }

    public void edit()
    {
        title.setText(R.string.edit_contact);
        contact = datasource.getContact(id);
        firstName.setText(contact.getFirstName());
        secondName.setText(contact.getSecondName());
        residence.setText(contact.getResidence());
        number.setText(contact.getNumber());
        email.setText(contact.getEmail());
        note.setText(contact.getNote());
    }

    public void save(View v){
        if (firstName.getText().toString().length() == 0)
        {
            printError(getResources().getString(R.string.error));
            return;
        }

        if (id.length() == 0)
            datasource.createContact(firstName.getText().toString(),
                secondName.getText().toString(),
                residence.getText().toString(),
                number.getText().toString(),
                email.getText().toString(),
                note.getText().toString());
        else
            datasource.updateContact(firstName.getText().toString(),
                secondName.getText().toString(),
                residence.getText().toString(),
                number.getText().toString(),
                email.getText().toString(),
                note.getText().toString(), id);
        datasource.close();
        finish();
    }
    public void delete(View v){
        datasource.deleteContact(id);
        datasource.close();
        finish();
    }
    public void cancel(View v){
        datasource.close();
        finish();
    }
    public void printError(String txt)
    {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        if (currentTime != null)
            printError(currentTime.toString());
        super.onResume();
    }

    @Override
    protected void onPause() {
        currentTime = Calendar.getInstance().getTime();
        super.onPause();
    }
}
