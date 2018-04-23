package com.elektroshock.ades.ades.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import  org.json.JSONObject;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.elektroshock.ades.ades.Activity.Util.Penerima;
import com.elektroshock.ades.ades.R;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends Activity {

    Button masuk;
    ProgressDialog progressDialog;

    String url = "http://api.hondauntukntb.com/api/login_driver";

    String id_driver, id_dealer, no_ktp_driver, nama_driver, alamat_driver, tlp_driver,
            tgl_lahir_driver, foto_driver;

    private EditText kontakuser, pass;
    Penerima penerima;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        kontakuser = (EditText) findViewById(R.id.kontakuser);
        pass = (EditText) findViewById(R.id.password);
        masuk = (Button) findViewById(R.id.submit1);

        penerima = new Penerima();

        preferences = getSharedPreferences("driver", Context.MODE_PRIVATE);
        boolean otoritas = preferences.getBoolean("otoritas", false);

        if (otoritas){
            Intent intent = new Intent(LoginActivity.this, DriverActivity.class);
            startActivity(intent);
            finish();
        }

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String kontak = kontakuser.getText().toString().trim();
                final String password = pass.getText().toString().trim();

                if (kontak.trim().length() > 0 && password.trim().length() > 0) {
                    login(kontak, password);
                } else {
                    Toast.makeText(getApplicationContext() ,"Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void login(final String hp, final String pass) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                progressDialog.closeOptionsMenu();
                Log.d("Message", response);

                try {
                    JSONObject driver = new JSONObject(response);

                    String status =driver.getString("status");

                    if (status.equals("success")) {
                        String data_mentah = driver.getString("data");

                        JSONObject data_driver = new JSONObject(data_mentah);
                        id_driver = data_driver.getString("id_driver");
                        id_dealer = data_driver.getString("id_dealer");
                        no_ktp_driver = data_driver.getString("no_ktp_driver");
                        nama_driver = data_driver.getString("nama_driver");
                        alamat_driver = data_driver.getString("alamat_driver");
                        tlp_driver = data_driver.getString("tlp_driver");
                        tgl_lahir_driver = data_driver.getString("tgl_lahir_driver");
                        foto_driver = data_driver.getString("foto_driver");

                        preferences = getSharedPreferences("driver",MODE_PRIVATE);
                        editor = preferences.edit();
                        editor.putString("id_driver", id_driver);
                        editor.putString("id_dealer", id_dealer);
                        editor.putString("no_ktp_driver", no_ktp_driver);
                        editor.putString("nama_driver", nama_driver);
                        editor.putString("alamat_driver", alamat_driver);
                        editor.putString("tlp_driver", tlp_driver);
                        editor.putString("tgl_lahir_driver", tgl_lahir_driver);
                        editor.putString("foto_driver", foto_driver);

                        editor.putBoolean("otoritas", true);
                        editor.commit();

                        Intent intent=new Intent(LoginActivity.this, DriverActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "No. handphone dan paswword anda tidak sesuai !", Toast.LENGTH_SHORT).show();
                    }
                    penerima.setID_DRIVER(id_driver);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                params.put("tlp_driver", hp);
                params.put("password_driver", pass);

                return params;
            }
        };
        queue.add(request);
    }
}
