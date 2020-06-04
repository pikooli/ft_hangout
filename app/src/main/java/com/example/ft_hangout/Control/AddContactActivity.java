package com.example.ft_hangout.Control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ft_hangout.Model.Contact;
import com.example.ft_hangout.Model.ContactDatasource;
import com.example.ft_hangout.R;

public class AddContactActivity extends AppCompatActivity {
    private ContactDatasource datasource;
    private TextView title;
    private  EditText firstName;
    private  EditText secondName;
    private  EditText residence;
    private  EditText number;
    private  EditText email;
    private  EditText note;
    private  String id;
    private Contact contact;

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
        datasource = new ContactDatasource(this);

        getIntent();
        id = getIntent().getStringExtra("id");
        if (id.length() > 0)
            edit();
    }
    public void edit()
    {
        title.setText("Edit contact");
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
            printError("no first name input");
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
        Toast.makeText(this, "Error : " + txt, Toast.LENGTH_SHORT).show();
    }
}
