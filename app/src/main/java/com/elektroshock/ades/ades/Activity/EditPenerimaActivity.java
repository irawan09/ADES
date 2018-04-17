package com.elektroshock.ades.ades.Activity;

import android.content.Intent;
import android.database.Cursor;
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

import com.elektroshock.ades.ades.Activity.DatabaseHandler.DatabaseHandler;
import com.elektroshock.ades.ades.Activity.Util.Penerima;
import com.elektroshock.ades.ades.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditPenerimaActivity extends AppCompatActivity {

    DatabaseHandler dbcenter;
    Penerima penerima;
    protected Cursor cursor;
    DatabaseHandler dbHelper;
    EditText nama, ktp, kontak, email, instagram, twitter, youtube, facebook;
    protected String id, status, hobi, nama_penerima, no_ktp, hp, emails, instagrams, twitters, youtubes, facebooks;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_penerima);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Edit Data Penerima");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

    }
}
