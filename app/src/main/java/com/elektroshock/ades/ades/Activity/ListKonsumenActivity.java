package com.elektroshock.ades.ades.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.elektroshock.ades.ades.Activity.Util.DatabaseHandler;
import com.elektroshock.ades.ades.R;

public class ListKonsumenActivity extends AppCompatActivity {

    protected String[] daftar;
    ListView ListView02;
    protected Cursor cursor;
    DatabaseHandler dbcenter;
    public static ListKonsumenActivity ma;

    protected String nama, ttl, kontak, alamat, type, warna, no_mesin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_konsumen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Lihat Data Konsumen");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);



/*
        dbcenter = new DatabaseHandler(this);
        SQLiteDatabase db = dbcenter.getWritableDatabase();
        db.execSQL("INSERT INTO KONSUMEN(NAMA, TTL, KONTAK, ALAMAT, TYPE, WARNA, NO_MESIN) VALUES('" +
                nama+"','"+
                ttl+"','" +
                kontak+"','"+
                alamat+"','" +
                type+"','" +
                warna+"','" +
                no_mesin+ "')");

        ma=this;
        dbcenter = new DatabaseHandler(this);
        RefreshList();      */

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
            Intent intent=new Intent(ListKonsumenActivity.this, KonsumenActivity.class);
            startActivity(intent);

        }
        return true;
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

        ListView02 = (ListView)findViewById(R.id.listView2);
        ListView02.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        ListView02.setSelected(true);
        ListView02.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();
                final CharSequence[] dialogitem = {"Tampilkan Data", "Hapus Data"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ListKonsumenActivity.this);
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

                                Intent intent=new Intent(ListKonsumenActivity.this, KonsumenActivity.class);
                                startActivity(intent);
                            case 1 :
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("DELETE from PENERIMA where NAMA = '"+selection+"'");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }});
        ((ArrayAdapter)ListView02.getAdapter()).notifyDataSetInvalidated();
    }
}
