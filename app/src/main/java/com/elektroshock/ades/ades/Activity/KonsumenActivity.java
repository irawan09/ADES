package com.elektroshock.ades.ades.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.elektroshock.ades.ades.R;

public class KonsumenActivity extends AppCompatActivity {

    Button cust,penerima;
    TextView nama, ttl, hape, alamat, type, warna, mesin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsumen);

        nama = (TextView) findViewById(R.id.nama);
        ttl = (TextView) findViewById(R.id.ttl);
        hape = (TextView) findViewById(R.id.hape);
        alamat = (TextView) findViewById(R.id.alamat);
        type = (TextView) findViewById(R.id.type);
        warna = (TextView) findViewById(R.id.warna);
        mesin = (TextView) findViewById(R.id.mesin);

        penerima = (Button) findViewById(R.id.penerima_kendaraan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Data Pelanggan");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);


        penerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(KonsumenActivity.this, PenerimaActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pelanggan, menu);
        //getMenuInflater().inflate(R.menu.menu_pelanggan, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.kelola_pelanggan){
            Intent intent=new Intent(KonsumenActivity.this, DataPelangganActivity.class);
            startActivity(intent);

        }
        return true;
    }


}
