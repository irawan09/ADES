package com.elektroshock.ades.ades.Activity.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.elektroshock.ades.ades.Activity.DatabaseHandler.DatabaseHandler;
import com.elektroshock.ades.ades.Activity.ListPenerimaActivity;
import com.elektroshock.ades.ades.Activity.Util.Penerima;
import com.elektroshock.ades.ades.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListPenerimaAdapter extends RecyclerView.Adapter<ListPenerimaAdapter.MyViewHolder> {

    Context context;
    ArrayList<Penerima> PenerimaList;
    DatabaseHandler dbcenter;
    String url = "http://api.hondauntukntb.com/api/penerima";

    String selfieString, TTDString;

    public ListPenerimaAdapter(Context context, ArrayList<Penerima> penerima){
        this.context = context;
        this.PenerimaList = penerima;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.penerima_list, parent, false);

        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPenerimaAdapter.MyViewHolder holder, int position) {
        final Penerima person = PenerimaList.get(position);
        holder.tv.setText(person.getNAMA_PENERIMA());
        holder.linearLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                String  id, id_pembeli, id_driver, no_ktp_penerima, nama_penerima,
                        tlp_penerima, email_penerima, status_kekeluargaan, hobi_penerima,
                        instagram,twitter, youtube, facebook, rating_penerima,
                        comment_penerima, selfie, ttd;

                id = person.getID();
                id_pembeli = person.getID_PEMBELI();
                id_driver = person.getID_DRIVER();
                no_ktp_penerima = person.getKTP_PENERIMA();
                nama_penerima = person.getNAMA_PENERIMA();
                tlp_penerima = person.getTLP_PENERIMA();
                email_penerima = person.getEMAIL_PENERIMA();
                status_kekeluargaan = person.getSTATUS_KEKELUARGAAN();
                hobi_penerima = person.getHOBI();
                instagram = person.getINSTAGRAM();
                twitter = person.getTWITTER();
                youtube = person.getYOUTUBE();
                facebook = person.getFACEBOOK();
                rating_penerima = person.getRATING();
                comment_penerima = person.getCOMMENT();
                selfie = person.getSELFIE();
                ttd = person.getTTD();

                Log.e("ID ", id);
                Log.e("TTD ", ttd);

                selfieString = ListPenerimaActivity.ConvertURLtoBitmap(selfie);
                TTDString = ListPenerimaActivity.ConvertURLtoBitmap(ttd);

                Log.e("STRING SELFIE ", ListPenerimaActivity.ConvertURLtoBitmap(selfie));
                Log.e("STRING TTD ", TTDString);
                Log.e("ID DRIVER ", id_driver);
                Log.e("STATUS KEKELUARGAAN ", status_kekeluargaan);

                kirim(id, id_pembeli, id_driver, no_ktp_penerima, nama_penerima, tlp_penerima,
                        email_penerima, status_kekeluargaan, hobi_penerima, instagram, twitter,
                        youtube, facebook, rating_penerima, comment_penerima, selfieString, TTDString);

            }
        });

    }

    @Override
    public int getItemCount() {
        return PenerimaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.rl_penerima);
            tv = (TextView) itemView.findViewById(R.id.text_penerima);
        }
    }

    public void kirim(final String id, final String id_pembeli, final String id_driver, final String no_ktp_penerima, final String nama_penerima,
                      final String tlp_penerima, final String email_penerima, final String status_kekeluargaan, final String hobi_penerima,
                      final String instagram, final String twitter, final String youtube, final String facebook, final String rating_penerima,
                      final String comment_penerima, final String selfieString, final String ttdString) {

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            //    progressSpinner.hide();
                Log.e("Message", response);

                try{
                    JSONObject driver = new JSONObject(response);

                    String status =driver.getString("status");
                    if (status.equals("success")) {
                        dbcenter = new DatabaseHandler(context);
                        dbcenter.deletePenerima(id);

                        Toast.makeText(context, "Data terkirim", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(context, "Data gagal terkirim", Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Response : ", error.toString());
                Toast.makeText(context,"Koneksi anda tidak stabil", Toast.LENGTH_SHORT).show();
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

}


