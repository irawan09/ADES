package com.elektroshock.ades.ades.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.elektroshock.ades.ades.Activity.Adapter.ListKonsumenAdapter;
import com.elektroshock.ades.ades.Activity.Adapter.ListPenerimaAdapter;
import com.elektroshock.ades.ades.Activity.DatabaseHandler.DatabaseHandler;
import com.elektroshock.ades.ades.Activity.Util.Konsumen;
import com.elektroshock.ades.ades.Activity.Util.Penerima;
import com.elektroshock.ades.ades.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListPenerimaActivity extends AppCompatActivity {

    RecyclerView PenerimaList;
    ListPenerimaAdapter PenerimaAdapter;
    DividerItemDecoration dividerItemDecoration;

    protected Cursor cursor;
    DatabaseHandler dbcenter;
    ProgressDialog progressSpinner;
    Penerima penerima;

    String url = "http://api.hondauntukntb.com/api/penerima";

    protected int penerima_id;
    protected String id_driver, id_pembeli,ttd, selfie, penerima_nama, penerima_ktp, penerima_kontak, penerima_email,
            penerima_status, penerima_hobi, penerima_instagram, penerima_twitter, penerima_youtube,
            penerima_facebook, penerima_rating, penerima_komentar;

    byte[] signature, selfshot;

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
        inflater.inflate(R.menu.menu_penerima, menu);
        //getMenuInflater().inflate(R.menu.menu_pelanggan, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.detail){
            Intent intent=new Intent(ListPenerimaActivity.this, DetailDataPenerimaActivity.class);
            startActivity(intent);

        }  else if (item.getItemId() == R.id.list) {
            Intent intent=new Intent(ListPenerimaActivity.this, ListPenerimaActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public void kirim(final String id_pembeli, final String id_driver,final String no_ktp_penerima,final String nama_penerima,
                      final String tlp_penerima,final String email_penerima,final String status_kekeluargaan,final String hobi_penerima,
                      final String instagram,final String twitter,final String youtube,final String facebook,final String rating_penerima,
                      final String comment_penerima,final String selfieString,final String ttdString) {

        progressSpinner.setMessage("Loading...");
        progressSpinner.setProgressStyle(progressSpinner.STYLE_SPINNER);
        progressSpinner.setIndeterminate(true);
        progressSpinner.show();

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressSpinner.hide();
                Log.d("Message", response);

                Toast.makeText(getApplicationContext(), "Data terkirim", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Response : ", error.toString());
                Toast.makeText(getApplicationContext(),"Koneksi anda tidak stabil", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                String creds = String.format("%s:%s","admin","123");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }

            @Override
            public Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id_pembeli", id_pembeli);
                params.put("id_driver", id_driver);
                params.put("no_ktp_penerima", no_ktp_penerima);
                params.put("nama_penerima", nama_penerima);
                params.put("tlp_penerima", tlp_penerima);
                params.put("email_penerima", email_penerima);
                params.put("status_kekeluargaan", status_kekeluargaan);
                params.put("hobi_penerima", hobi_penerima);
                params.put("instagram", instagram);
                params.put("twitter", twitter);
                params.put("youtube", youtube);
                params.put("facebook", facebook);
                params.put("rating_penerima", rating_penerima);
                params.put("comment_penerima", comment_penerima);
                params.put("foto_selfie", selfieString);
                params.put("ttd_penerima", ttdString);

                return params;
            }
        };

        queue.add(request);

    }

    public void saveToDb(Penerima penerima) {

        dbcenter.deleteAllPenerima();
        dbcenter.insertPenerima(penerima);

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