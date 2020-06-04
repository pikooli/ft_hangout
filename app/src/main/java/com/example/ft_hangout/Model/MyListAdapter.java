package com.example.ft_hangout.Model;

import android.app.Activity;
import android.graphics.Rect;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ft_hangout.R;

public class MyListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] mName;
    private final String[] mNumber;
    private final int[] mId;

    public MyListAdapter(Activity context, String[] name, String[] number, int[] id){
        super(context, R.layout.mylist, name);
                this.context = context;
                this.mName = name;
                this.mId = id;
                this.mNumber = number;
    }

    public View getView(int position , View view , ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);
        final  TextView name = (TextView) rowView.findViewById(R.id.Vname);
        final Button call = (Button) rowView.findViewById(R.id.Vcall);
        final Button sms = (Button) rowView.findViewById(R.id.Vsms);
        final ImageButton image = (ImageButton) rowView.findViewById(R.id.Vicon);
        name.setTag("" + mId[position]);
        image.setTag("" + mId[position]);
        call.setTag(mNumber[position]);
        sms.setTag(mNumber[position]);

        if (mName[position].length() > 20)
            name.setText(mName[position].substring(0,7) + "...");
        else
            name.setText(mName[position]);

        final View parent2 = (View) name.getParent();
        parent.post(new Runnable() {
            @Override
            public void run() {
                final Rect r = new Rect();
                name.getHitRect(r);
                r.right += 1000;
                r.bottom += 1000;
                parent2.setTouchDelegate(new TouchDelegate(r, name));
            }
        });
        return rowView;
    }
}
