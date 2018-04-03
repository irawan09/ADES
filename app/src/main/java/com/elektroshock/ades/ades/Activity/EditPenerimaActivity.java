package com.elektroshock.ades.ades.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.elektroshock.ades.ades.Activity.Util.DatabaseHandler;
import com.elektroshock.ades.ades.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditPenerimaActivity extends AppCompatActivity {

    protected Cursor cursor;
    DatabaseHandler dbHelper;
    EditText nama, ktp, kontak, email, instagram, twitter, youtube, facebook;
    String id, status, hobi;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_penerima);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Edit Data Penerima");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        dbHelper = new DatabaseHandler(this);
        nama = (EditText) findViewById(R.id.ubah_nama);
        ktp = (EditText) findViewById(R.id.ubah_ktp);
        kontak = (EditText) findViewById(R.id.ubah_kontak);
        email = (EditText) findViewById(R.id.ubah_email);
        instagram = (EditText) findViewById(R.id.ubah_instagram);
        twitter = (EditText) findViewById(R.id.ubah_twitter);
        youtube = (EditText) findViewById(R.id.ubah_youtube);
        facebook = (EditText) findViewById(R.id.ubah_facebook);

        final Spinner spinner1 = (Spinner) findViewById(R.id.ubah_status);

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
                    EditPenerimaActivity.this.status = selectedItemText;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Mohon pilih salah satu status kekeluargaan !", Toast.LENGTH_SHORT).show();
            }
        });

        final Spinner spinner2 = (Spinner) findViewById(R.id.ubah_hobi);

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
                    EditPenerimaActivity.this.hobi = selectedItemText;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Mohon pilih salah satu hobi !", Toast.LENGTH_SHORT).show();
            }
        });

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM PENERIMA WHERE NAMA = '" +
                getIntent().getStringExtra("nama") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            id = cursor.getString(0).toString();
            nama.setText(cursor.getString(1).toString());
            ktp.setText(cursor.getString(2).toString());
            kontak.setText(cursor.getString(3).toString());
            email.setText(cursor.getString(4).toString());
            instagram.setText(cursor.getString(7).toString());
            twitter.setText(cursor.getString(8).toString());
            youtube.setText(cursor.getString(9).toString());
            facebook.setText(cursor.getString(10).toString());
        }

        update = (Button) findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("UPDATE PENERIMA set NAMA='"+nama.getText().toString()+
                        "', KTP='" +ktp.getText().toString()+
                        "', KONTAK='"+kontak.getText().toString()+
                        "', EMAIL='"+email.getText().toString()+
                        "', STATUS='"+status+
                        "', HOBI='"+hobi+
                        "', INSTAGRAM='"+instagram.getText().toString()+
                        "', TWITTER='"+twitter.getText().toString()+
                        "', YOUTUBE='"+youtube.getText().toString()+
                        "', FACEBOOK='"+facebook.getText().toString()+
                        "' WHERE ID='"+id+"'");

                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
                ListPenerimaActivity.ma.RefreshList();

                Intent intent=new Intent(EditPenerimaActivity.this, ListPenerimaActivity.class);
                startActivity(intent);
            }
        });
    }
}
