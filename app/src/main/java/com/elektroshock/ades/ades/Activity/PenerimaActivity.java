package com.elektroshock.ades.ades.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.elektroshock.ades.ades.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PenerimaActivity extends AppCompatActivity {

    Button penerima;
    TextView nama, hp;
    String kel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penerima);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Data Penerima");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        nama = (TextView) findViewById(R.id.nama_penerima);
        hp = (TextView) findViewById(R.id.no_hp);

        penerima = (Button) findViewById(R.id.next);

        penerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(PenerimaActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });

        final Spinner spinner = (Spinner) findViewById(R.id.status);

        final String[] status = new String[]{
                "Status Kekeluargaan",
                "Ayah",
                "Ibu",
                "Anak",
                "Anggota keluarga lain"
        };

        final List<String> statusList = new ArrayList<>(Arrays.asList(status));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
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
        spinnerArrayAdapter.setDropDownViewResource(R.layout.status_spinner);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item
                    kel = selectedItemText;
                    Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_penerima, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.simpan){
            Toast.makeText(PenerimaActivity.this, "Simpan Selected", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.hapus) {
            Toast.makeText(PenerimaActivity.this, "Hapus Selected", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.ambil) {
            Toast.makeText(PenerimaActivity.this, "Ambil Selected", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.kirim){
            Toast.makeText(PenerimaActivity.this, "Kirim Selected", Toast.LENGTH_LONG).show();
        }

        return true;
    }
}
