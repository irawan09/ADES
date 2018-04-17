package com.elektroshock.ades.ades.Activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.elektroshock.ades.ades.Activity.Adapter.ListKonsumenAdapter;
import com.elektroshock.ades.ades.Activity.DatabaseHandler.DatabaseHandler;
import com.elektroshock.ades.ades.Activity.Util.Konsumen;
import com.elektroshock.ades.ades.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListKonsumenActivity extends AppCompatActivity {

    RecyclerView konsumenList;
    DatabaseHandler dbcenter;
    JSONArray konsumen;

    ListKonsumenAdapter konsumenAdapter;
    DividerItemDecoration dividerItemDecoration;

    String id_driver;

    String url = "http://api.hondauntukntb.com/api/pembeli?id_driver=";

    ArrayList<Konsumen> data_pembeli = new ArrayList<>();
    HashMap<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_konsumen);

        dbcenter = new DatabaseHandler(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Lihat Data Konsumen");
        toolbar.setTitleTextColor(Color.WHITE);

        SharedPreferences driver = getSharedPreferences("driver",MODE_PRIVATE);
        id_driver = driver.getString("id_driver", "");

        Load();
        showData();

    }

    private void Load () {

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, url+id_driver, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RESPONSE ", response);

                try {
                    konsumen = new JSONArray(response);

                    for(int i=0; i<konsumen.length();i++){
                        JSONObject cust = konsumen.getJSONObject(i);

                        Konsumen pembeli = new Konsumen();

                        pembeli.setPembeli_id(cust.getString("id_pembeli"));
                        pembeli.setPembeli_nama(cust.getString("nama_pembeli"));
                        pembeli.setPembeli_ttl(cust.getString("tgl_lahir_pembeli"));
                        pembeli.setPembeli_kontak(cust.getString("tlp_pembeli"));
                        pembeli.setPembeli_alamat(cust.getString("alamat_pembeli"));
                        pembeli.setPembeli_type(cust.getString("type_kendaraan"));
                        pembeli.setPembeli_warna(cust.getString("warna_kendaraan"));
                        pembeli.setPembeli_mesin(cust.getString("no_mesin_kendaraan"));

                        Log.e("Nama", pembeli.getPembeli_nama());
                        Log.e("Pembeli ID", pembeli.getPembeli_id());

                        data_pembeli.add(pembeli);
                        Log.e("data pembeli", data_pembeli.get(i).getPembeli_nama()+"");
                    }

                    saveToDb(data_pembeli);
                    showData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
        };
        queue.add(request);
    }

    public void saveToDb(ArrayList<Konsumen> pembeli) {

        dbcenter.deleteAllKonsumen();

        for (int i = 0; i < pembeli.size(); i++) {
            dbcenter.insertKonsumen(pembeli.get(i));
        }
    }

    public void showData(){
        ArrayList<Konsumen> konsumen = dbcenter.getKonsumen();

        konsumenList = (RecyclerView) findViewById(R.id.rc_konsumen);
        konsumenAdapter = new ListKonsumenAdapter(this, konsumen);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        konsumenList.setLayoutManager(layoutManager);

        dividerItemDecoration = new DividerItemDecoration(konsumenList.getContext(), LinearLayoutManager.VERTICAL);
        konsumenList.addItemDecoration(dividerItemDecoration);

        konsumenList.setItemAnimator(new DefaultItemAnimator());
        konsumenList.setAdapter(konsumenAdapter);
    }
}