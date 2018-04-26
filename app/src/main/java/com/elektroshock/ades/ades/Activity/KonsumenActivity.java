package com.elektroshock.ades.ades.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.elektroshock.ades.ades.R;

import java.io.File;

public class KonsumenActivity extends AppCompatActivity {

    String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/Peta/";
    String pic_name;
    String StoredPath = DIRECTORY + pic_name + ".JPEG";


    Button penerima;
    TextView nama, ttl, hape, alamat, type, warna, mesin;
    ImageView peta;

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
        peta = (ImageView) findViewById(R.id.map);

        penerima = (Button) findViewById(R.id.penerima_kendaraan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Data Pelanggan");
        toolbar.setTitleTextColor(Color.WHITE);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        nama.setText(bundle.getString("nama"));
        ttl.setText(bundle.getString("ttl"));
        hape.setText(bundle.getString("hape"));
        alamat.setText(bundle.getString("alamat"));
        type.setText(bundle.getString("type"));
        warna.setText(bundle.getString("warna"));
        mesin.setText(bundle.getString("mesin"));
        showImage(bundle.getString("peta"));

        SharedPreferences konsumen = getSharedPreferences("konsumen",MODE_PRIVATE);
        SharedPreferences.Editor simpan = konsumen.edit();
        simpan.putString("kontak_pelanggan", bundle.getString("hape"));
        simpan.putString("id_pembeli", bundle.getString("id"));
        simpan.commit();

        Log.e("ID PEMBELI", bundle.getString("id"));

        penerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(KonsumenActivity.this, PenerimaActivity.class);
                startActivity(intent);

            }
        });
    }

    private void showImage(String uri) {
        File imgFile = new  File(uri);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            peta.setImageBitmap(myBitmap);

        }
    }
}
