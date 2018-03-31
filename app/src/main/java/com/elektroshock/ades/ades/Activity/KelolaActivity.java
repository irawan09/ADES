package com.elektroshock.ades.ades.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elektroshock.ades.ades.R;

public class KelolaActivity extends AppCompatActivity {

    Button simpan,hapus,kirim;
    ImageView penerima_ttd;
    TextView nama,ktp, kontak,status, atasnama;
    String penerima_nama, penerima_ktp, penerima_kontak, penerima_status, penerima_rating, penerima_komentar, selfie, ttd;

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
                ktp.setText("");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pelanggan, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.kelola_pelanggan){
            Intent intent=new Intent(KelolaActivity.this, KelolaActivity.class);
            startActivity(intent);

        }

        return true;
    }
}
