package com.metacoders.nextfly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class details_launch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_launch);
        TextView title = (TextView)findViewById(R.id.deatils_title);
        TextView date = (TextView)findViewById(R.id.detaile_date);
        Intent m = getIntent();
        String nam = m.getStringExtra("namee");
        String da = m.getStringExtra("date");
        title.setText(nam);
        date.setText(da);





    }
}
