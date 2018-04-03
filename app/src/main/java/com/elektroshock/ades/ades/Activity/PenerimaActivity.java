package com.elektroshock.ades.ades.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.elektroshock.ades.ades.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PenerimaActivity extends AppCompatActivity {

    Button next;
    String status, hobi, nama_penerima, no_ktp, kontak, email, instagram, twitter, youtube, facebook;
    protected EditText penerima_nama,penerima_ktp, penerima_kontak, penerima_email,
            penerima_instagram, penerima_twitter, penerima_youtube, penerima_facebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penerima);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Data Penerima");
        toolbar.setTitleTextColor(Color.WHITE);

        next = (Button) findViewById(R.id.next);

        penerima_nama = (EditText) findViewById(R.id.nama_penerima);
        penerima_ktp = (EditText) findViewById(R.id.no_ktp);
        penerima_kontak = (EditText) findViewById(R.id.no_hp);
        penerima_email = (EditText) findViewById(R.id.email);
        penerima_instagram = (EditText) findViewById(R.id.instagram);
        penerima_twitter = (EditText) findViewById(R.id.twitter);
        penerima_youtube = (EditText) findViewById(R.id.youtube);
        penerima_facebook = (EditText) findViewById(R.id.facebook);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nama_penerima = penerima_nama.getText().toString();
                no_ktp = penerima_ktp.getText().toString();
                kontak = penerima_kontak.getText().toString();
                email = penerima_email.getText().toString();
                instagram = penerima_instagram.getText().toString();
                twitter = penerima_twitter.getText().toString();
                youtube = penerima_youtube.getText().toString();
                facebook = penerima_facebook.getText().toString();

                SharedPreferences preferences = getSharedPreferences("Penerima",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("penerima_nama",nama_penerima);
                editor.putString("penerima_ktp",no_ktp);
                editor.putString("penerima_kontak",kontak);
                editor.putString("penerima_email",email);
                editor.putString("penerima_status",status);
                editor.putString("penerima_hobi",hobi);
                editor.putString("penerima_instagram",instagram);
                editor.putString("penerima_twitter",twitter);
                editor.putString("penerima_youtube",youtube);
                editor.putString("penerima_facebook",facebook);
                editor.commit();

                if (nama_penerima.trim().length() > 0){
                    if (no_ktp.trim().length() > 0){
                        if (kontak.trim().length() > 0){
                            if(status != "0"){
                                if ((instagram.trim().length() > 0) || (twitter.trim().length() >0) || (youtube.trim().length() >0) || (facebook.trim().length() >0)) {

                                    Intent intent = new Intent(PenerimaActivity.this, VideoActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Mohon isi salah satu sosial media !",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Pilih status kekeluargaan !",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Isi data nomor handphone !",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Isi data no. KTP !", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Isi data nama !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Spinner spinner1 = (Spinner) findViewById(R.id.status);

        final String[] status = new String[]{
                "Status Kekeluargaan",
                "Yang bersangkutan",
                "Ayah",
                "Ibu",
                "Anak",
                "Anggota keluarga lain"
        };

        final List<String> statusList = new ArrayList<>(Arrays.asList(status));

        final ArrayAdapter<String> spinner1ArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.status_spinner,statusList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinner1ArrayAdapter.setDropDownViewResource(R.layout.status_spinner);
        spinner1.setAdapter(spinner1ArrayAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item
                    PenerimaActivity.this.status = selectedItemText;
                }
                else {
                    PenerimaActivity.this.status = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Mohon pilih salah satu status kekeluargaan !", Toast.LENGTH_SHORT).show();
            }
        });

        final Spinner spinner2 = (Spinner) findViewById(R.id.hobi);

        final String[] hobi = new String[]{
                "Hobi",
                "Game",
                "Travelling",
                "Hiburan",
                "Kolektor Barang",
                "Olahraga"
        };

        final List<String> hobiList = new ArrayList<>(Arrays.asList(hobi));

        final ArrayAdapter<String> spinner2ArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.hobi_spinner,hobiList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinner2ArrayAdapter.setDropDownViewResource(R.layout.hobi_spinner);
        spinner2.setAdapter(spinner2ArrayAdapter);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item
                    PenerimaActivity.this.hobi = selectedItemText;
                }
                else {
                    Toast.makeText(getApplicationContext(), "Mohon pilih salah satu hobi !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
