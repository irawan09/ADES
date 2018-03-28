package com.elektroshock.ades.ades.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elektroshock.ades.ades.R;

public class KelolaActivity extends AppCompatActivity {

    Button simpan,hapus,kirim;
    ImageView penerima_ttd;
    TextView nama,kontak,status, atasnama;
    String penerima_nama, penerima_kontak,penerima_status;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Kelola Data Penerima");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        simpan = (Button) findViewById(R.id.simpan);
        hapus = (Button) findViewById(R.id.hapus);
        kirim = (Button) findViewById(R.id.kirim);

        penerima_ttd = (ImageView) findViewById(R.id.penerima_ttd);

        nama = (TextView) findViewById(R.id.penerima_name);
        kontak = (TextView) findViewById(R.id.penerima_kontak);
        status = (TextView) findViewById(R.id.penerima_status);
        atasnama = (TextView) findViewById(R.id.atasnama);

        //take shared preferences from
        SharedPreferences preferences = getSharedPreferences("Penerima",MODE_PRIVATE);
        String img_str=preferences.getString("ttd", "");
        if (!img_str.equals("")){
            //decode string to image
            String base=img_str;
            byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
            penerima_ttd.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );

            penerima_nama = preferences.getString("penerima_nama","");
            penerima_kontak = preferences.getString("penerima_kontak","");
            penerima_status = preferences.getString("penerima_status","");
            nama.setText(penerima_nama);
            kontak.setText(penerima_kontak);
            status.setText(penerima_status);
            atasnama.setText("("+penerima_nama+")");

        }

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan", Toast.LENGTH_SHORT);
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("Penerima", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear().commit();

                nama.setText("");
                kontak.setText("");
                status.setText("");
                penerima_ttd.setImageResource(android.R.color.transparent);;
                atasnama.setText("");
            }
        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Data Berhasil Dikirim", Toast.LENGTH_SHORT);
            }
        });

    }
}
