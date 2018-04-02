package com.elektroshock.ades.ades.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.elektroshock.ades.ades.Activity.Util.DatabaseHandler;
import com.elektroshock.ades.ades.R;

public class EditPenerimaActivity extends AppCompatActivity {

    protected Cursor cursor;
    DatabaseHandler dbHelper;
    EditText nama, ktp, kontak;
    String id;
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
        }

        update = (Button) findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("UPDATE PENERIMA set NAMA='"+
                        nama.getText().toString() +"', KTP='" +
                        ktp.getText().toString()+"', KONTAK='"+
                        kontak.getText().toString() +"' WHERE ID='" +
                        id+"'");

                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
                ListPenerimaActivity.ma.RefreshList();

                Intent intent=new Intent(EditPenerimaActivity.this, ListPenerimaActivity.class);
                startActivity(intent);
            }
        });
    }
}
