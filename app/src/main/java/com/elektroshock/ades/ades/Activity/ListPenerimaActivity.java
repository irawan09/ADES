package com.elektroshock.ades.ades.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.elektroshock.ades.ades.Activity.Adapter.ListPenerimaAdapter;
import com.elektroshock.ades.ades.Activity.DatabaseHandler.DatabaseHandler;

import com.elektroshock.ades.ades.Activity.Util.Penerima;
import com.elektroshock.ades.ades.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class ListPenerimaActivity extends AppCompatActivity {

    RecyclerView PenerimaList;
    ListPenerimaAdapter PenerimaAdapter;
    DividerItemDecoration dividerItemDecoration;

    DatabaseHandler dbcenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_penerima);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Lihat Data Penerima");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        dbcenter = new DatabaseHandler(this);
        showData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_hapus, menu);
        //getMenuInflater().inflate(R.menu.menu_pelanggan, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.hapus){
            dbcenter.deleteAllPenerima();
        }
        return true;
    }

    public static String ConvertURLtoBitmap(String imageString){
        Bitmap bitmap;
        String imageBase64 = "";
        File SelfieFile = new  File(imageString);

        if(SelfieFile.exists()){
            bitmap = BitmapFactory.decodeFile(SelfieFile.getAbsolutePath());
            ByteArrayOutputStream stream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            byte[] image=stream.toByteArray();
            imageBase64 =Base64.encodeToString(image, 0);
        }
        return imageBase64;
    }

    public void showData(){
        ArrayList<Penerima> penerima = dbcenter.getPenerima();

        PenerimaList = (RecyclerView) findViewById(R.id.rc_penerima);
        PenerimaAdapter = new ListPenerimaAdapter(this, penerima);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        PenerimaList.setLayoutManager(layoutManager);

        dividerItemDecoration = new DividerItemDecoration(PenerimaList.getContext(), LinearLayoutManager.VERTICAL);
        PenerimaList.addItemDecoration(dividerItemDecoration);

        PenerimaList.setItemAnimator(new DefaultItemAnimator());
        PenerimaList.setAdapter(PenerimaAdapter);
    }
}