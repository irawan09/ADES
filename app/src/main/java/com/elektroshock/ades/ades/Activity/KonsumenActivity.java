package com.elektroshock.ades.ades.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.elektroshock.ades.ades.R;

public class KonsumenActivity extends Activity {

    Button cust,not_cust;
    TextView nama, mesin, type, ttl, hape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsumen);

        nama = (TextView) findViewById(R.id.nama);
        mesin = (TextView) findViewById(R.id.no_mesin);
        type = (TextView) findViewById(R.id.type);
        ttl = (TextView) findViewById(R.id.ttl);
        hape = (TextView) findViewById(R.id.hape);

        cust = (Button) findViewById(R.id.cust);
        not_cust = (Button) findViewById(R.id.no_cust);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Data Pelanggan");
        toolbar.setTitleTextColor(Color.WHITE);


        cust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(KonsumenActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });

        not_cust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(KonsumenActivity.this, PenerimaActivity.class);
                startActivity(intent);

            }
        });
    }
}
