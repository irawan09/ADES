package com.elektroshock.ades.ades.Activity;

import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.elektroshock.ades.ades.R;

public class DetailDataPenerimaActivity extends AppCompatActivity {

    ImageView penerima_ttd;
    TextView nama,ktp, kontak,status, atasnama;
    protected String penerima_nama, penerima_ktp, penerima_kontak, penerima_status, penerima_rating, penerima_komentar, selfie, ttd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penerima);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Data Penerima");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        penerima_ttd = (ImageView) findViewById(R.id.penerima_ttd);

        nama = (TextView) findViewById(R.id.penerima_name);
        ktp = (TextView) findViewById(R.id.penerima_id);
        kontak = (TextView) findViewById(R.id.penerima_kontak);
        status = (TextView) findViewById(R.id.penerima_status);
        atasnama = (TextView) findViewById(R.id.atasnama);

        SharedPreferences pref = getSharedPreferences("Selfie",MODE_PRIVATE);
        selfie=pref.getString("selfie", "");

        Log.e("tag", "String selfie mentah " + selfie);

        //take shared preferences from
        SharedPreferences gambar = getSharedPreferences("Signature",MODE_PRIVATE);
        ttd =gambar.getString("ttd", "");

        Log.e("tag", "String Gambar mentah " + ttd);

        if (!ttd.equals("")){
            //decode string to image
            String base= ttd;
            byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
            penerima_ttd.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );

            SharedPreferences preferences = getSharedPreferences("Penerima",MODE_PRIVATE);

            penerima_nama = preferences.getString("penerima_nama","");
            penerima_ktp = preferences.getString("penerima_ktp","");
            penerima_kontak = preferences.getString("penerima_kontak","");
            penerima_status = preferences.getString("penerima_status","");
            penerima_rating = preferences.getString("rating","");
            penerima_komentar = preferences.getString("komentar","");

            nama.setText(penerima_nama);
            ktp.setText(penerima_ktp);
            kontak.setText(penerima_kontak);
            status.setText(penerima_status);
            atasnama.setText("("+penerima_nama+")");
        }
    }
}
