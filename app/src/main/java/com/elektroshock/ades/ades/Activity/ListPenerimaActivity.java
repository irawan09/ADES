package com.elektroshock.ades.ades.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.elektroshock.ades.ades.Activity.Util.DatabaseHandler;
import com.elektroshock.ades.ades.R;

public class ListPenerimaActivity extends AppCompatActivity {

    protected String[] daftar;
    ListView ListView01;
    protected Cursor cursor;
    DatabaseHandler dbcenter;
    public static ListPenerimaActivity ma;

    protected String selfie, ttd, penerima_nama, penerima_ktp, penerima_kontak, penerima_status, penerima_rating, penerima_komentar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_penerima);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Lihat Data Penerima");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        //get data from sharedpreferences
        SharedPreferences pref = getSharedPreferences("Selfie",MODE_PRIVATE);
        selfie=pref.getString("selfie", "");

        SharedPreferences gambar = getSharedPreferences("Signature",MODE_PRIVATE);
        ttd =gambar.getString("ttd", "");

        SharedPreferences preferences = getSharedPreferences("Penerima",MODE_PRIVATE);

        penerima_nama = preferences.getString("penerima_nama","");
        penerima_ktp = preferences.getString("penerima_ktp","");
        penerima_kontak = preferences.getString("penerima_kontak","");
        penerima_status = preferences.getString("penerima_status","");
        penerima_rating = preferences.getString("rating","");
        penerima_komentar = preferences.getString("komentar","");

        dbcenter = new DatabaseHandler(this);
        SQLiteDatabase db = dbcenter.getWritableDatabase();
        db.execSQL("INSERT INTO PENERIMA (NAMA, KTP, KONTAK, STATUS, RATING, KOMENTAR, SELFIE, TTD) VALUES('" +
                penerima_nama+"','"+
                penerima_ktp+"','" +
                penerima_kontak+"','"+
                penerima_status+"','" +
                penerima_rating+"','" +
                penerima_komentar+"','" +
                selfie+"','" +
                ttd+ "')");

        ma=this;
        dbcenter = new DatabaseHandler(this);
        RefreshList();
    }

    public void RefreshList(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM PENERIMA",null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1).toString();
        }

        ListView01 = (ListView)findViewById(R.id.listView1);
        ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        ListView01.setSelected(true);
        ListView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();
                final CharSequence[] dialogitem = {"Kirim Data", "Edit Data", "Hapus Data"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ListPenerimaActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item){
                            case 0 :
                                /*
                                Intent i = new Intent(getApplicationContext(), LihatBiodata.class);
                                i.putExtra("nama", selection);
                                startActivity(i);
                                break; */
                            case 1 :
                                Intent in = new Intent(getApplicationContext(), EditPenerimaActivity.class);
                                in.putExtra("nama", selection);
                                startActivity(in);
                                break;
                            case 2 :
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("DELETE from PENERIMA where NAMA = '"+selection+"'");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }});
        ((ArrayAdapter)ListView01.getAdapter()).notifyDataSetInvalidated();
    }
}