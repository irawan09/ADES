package com.elektroshock.ades.ades.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.elektroshock.ades.ades.R;

public class DriverActivity extends AppCompatActivity {

    TextView namas,ttls, ktps, alamats, kontaks;
    ImageView foto_driver;
    Button lanjut;

    protected String nama, ttl, ktp, alamat, kontak, foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        lanjut = (Button) findViewById(R.id.lanjut);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Data Driver");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences("driver", Context.MODE_PRIVATE);
        nama = preferences.getString("nama_driver", "" );
        ttl = preferences.getString("tgl_lahir_driver", "" );
        ktp = preferences.getString("no_ktp_driver", "" );
        alamat = preferences.getString("alamat_driver", "" );
        kontak = preferences.getString("tlp_driver", "" );
        foto = preferences.getString("foto_driver", "" );

        namas = (TextView) findViewById(R.id.driver_name);
        ttls = (TextView) findViewById(R.id.driver_ttl);
        ktps = (TextView) findViewById(R.id.driver_ktp);
        alamats = (TextView) findViewById(R.id.driver_alamat);
        kontaks = (TextView) findViewById(R.id.driver_hape);
        foto_driver = (ImageView) findViewById(R.id.driver_foto);

        namas.setText(nama);
        ttls.setText(ttl);
        ktps.setText(ktp);
        alamats.setText(alamat);
        kontaks.setText(kontak);
        Glide.with(this)
                .load(foto)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.5f)
                .crossFade()
                .into(foto_driver);

        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DriverActivity.this, ListKonsumenActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_penerima, menu);
        //getMenuInflater().inflate(R.menu.menu_pelanggan, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.detail){
            Intent intent=new Intent(DriverActivity.this, DetailDataPenerimaActivity.class);
            startActivity(intent);

        }  else if (item.getItemId() == R.id.list) {
            Intent intent=new Intent(DriverActivity.this, ListPenerimaActivity.class);
            startActivity(intent);
        }

        return true;
    }
}