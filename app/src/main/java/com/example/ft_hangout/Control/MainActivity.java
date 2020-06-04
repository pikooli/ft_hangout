package com.example.ft_hangout.Control;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ft_hangout.Model.ColorPicker;
import com.example.ft_hangout.Model.ContactDatasource;
import com.example.ft_hangout.Model.MiniContact;
import com.example.ft_hangout.Model.MyListAdapter;
import com.example.ft_hangout.R;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ContactDatasource datasource;
    private List<MiniContact> values;
    private String[] mName;
    private int[] mid;
    private MyListAdapter adapter;
    private ListView lv;
    private String[] colors = {"Red", "Blue", "Purple", "White"};
    private Date currentTime = null;
    private Boolean otherActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datasource = new ContactDatasource(this);
        datasource.open();
        setView();

    }

    public void prepareName(){
        mName = new String[values.size()];
        mid = new int[values.size()];
        for(int i = 0 ; i < values.size(); i++)
        {
            mName[i] = values.get(i).getName();
            mid[i] = values.get(i).getId();
        }
    }

    public void setView() {

        values = datasource.getAllContact();
        prepareName();
        adapter = new MyListAdapter(this, mName, mid);
        lv = (ListView) findViewById(android.R.id.list);
        lv.setAdapter(adapter);
    }

    public void AddContact(View v){
        Intent intent = new Intent(this, AddContactActivity.class);
        intent.putExtra("id","");
        otherActivity = true;
        startActivity(intent);
    }
    public void editContact(View v){
        TextView tv = (TextView) v;
        Intent intent = new Intent(this, AddContactActivity.class);
        intent.putExtra("id",tv.getTag().toString());
        otherActivity = true;
        startActivity(intent);
    }

    public void Option(View v){
        final View navigate = findViewById(R.id.header);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        navigate.setBackgroundResource(R.color.red);
                        break;
                    case 1:
                        navigate.setBackgroundResource(R.color.blue);
                        break;
                    case 2:
                        navigate.setBackgroundResource(R.color.purple);
                        break;
                    default:
                        navigate.setBackgroundResource(R.color.white);
                        break;
                }
            }
        });
        builder.show();
    }

    public void call(View v){
        print("call" + v.getTag().toString());
    }
    public void sms(View v){
        print("sms " + v.getTag().toString());
    }

    public void pickPhoto(View v){
        print("photo" + v.getTag().toString());
    }

    @Override
    protected void onResume() {
        datasource.open();
        setView();
        if (currentTime != null)
            print(currentTime.toString());
        otherActivity = false;
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (!otherActivity)
            currentTime = Calendar.getInstance().getTime();
        else
            currentTime = null;
        datasource.close();
        super.onPause();
    }

    public void print(String txt)
    {
        Toast.makeText(this, "Error : " + txt, Toast.LENGTH_SHORT).show();
    }
}
