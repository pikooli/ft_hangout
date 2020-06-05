package com.example.ft_hangout.Control;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ft_hangout.Model.ContactDatasource;
import com.example.ft_hangout.Model.MiniContact;
import com.example.ft_hangout.Model.MyListAdapter;
import com.example.ft_hangout.R;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ContactDatasource datasource;
    private List<MiniContact> values;
    private String[] mName;
    private String[] mNumber;
    private int[] mid;
    private String MYPREFERENCE = "hangout";
    private MyListAdapter adapter;
    private ListView lv;
    private String[] colors;
    private Date currentTime = null;
    private Boolean otherActivity = false;
    private View header;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        colors = new String[]{getResources().getString(R.string.color_red),
                getResources().getString(R.string.color_blue),
                getResources().getString(R.string.color_purple),
                getResources().getString(R.string.color_white)};
        preferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
        editor = preferences.edit();
        header = findViewById(R.id.header);
        setHeaderColor();
        datasource = new ContactDatasource(this);
        datasource.open();
        setView();
    }

    public void setHeaderColor(){
        int color = preferences.getInt("color", R.color.white);
        header.setBackgroundResource(color);
    }

    public void prepareName(){
        mName = new String[values.size()];
        mid = new int[values.size()];
        mNumber = new String[values.size()];
        for(int i = 0 ; i < values.size(); i++)
        {
            mName[i] = values.get(i).getName();
            mid[i] = values.get(i).getId();
            mNumber[i] = values.get(i).getNumber();
        }
    }

    public void setView() {

        values = datasource.getAllContact();
        prepareName();
        adapter = new MyListAdapter(this, mName, mNumber, mid);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        editor.putInt("color",R.color.red);
                        editor.apply();
                        setHeaderColor();
                        break;
                    case 1:
                        editor.putInt("color",R.color.blue);
                        editor.apply();
                        setHeaderColor();
                        break;
                    case 2:
                        editor.putInt("color",R.color.purple);
                        editor.apply();
                        setHeaderColor();
                        break;
                    default:
                        editor.putInt("color",R.color.white);
                        editor.apply();
                        setHeaderColor();
                        break;
                }
            }
        });
        builder.show();
    }

    public void call(View v){
        otherActivity = true;
        if (v.getTag().toString().length() == 0)
            print(getResources().getString(R.string.errorNumber));
        else{
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + v.getTag().toString()));
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            startActivity(intent);
            otherActivity = true;
        }

    }
    public void sms(View v){
        if (v.getTag().toString().length() == 0)
            print(getResources().getString(R.string.errorNumber));
        else{
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 2);
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED)
                    return ;
            }
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
            sendIntent.setData(Uri.parse("smsto: 521-" + v.getTag().toString()));
            startActivity(sendIntent);
        }
    }

    public void pickPhoto(View v){
        print("photo" + v.getTag().toString());
    }

    @Override
    protected void onResume() {
        datasource.open();
        setView();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (currentTime != null)
            print(currentTime.toString());
        currentTime = null;
        otherActivity = false;

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

    public boolean isApplicationInBackground()
    {
        final ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningTaskInfo> tasks = manager.getRunningTasks(1);
        if (!tasks.isEmpty()){
            final ComponentName topActivity = tasks.get(0).topActivity;
            return !topActivity.getPackageName().equals(getPackageName());
        }
        return false;
    }

    public void print(String txt)
    {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }
}
