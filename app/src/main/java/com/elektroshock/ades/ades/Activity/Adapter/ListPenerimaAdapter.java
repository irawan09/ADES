package com.elektroshock.ades.ades.Activity.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elektroshock.ades.ades.Activity.DatabaseHandler.DatabaseHandler;
import com.elektroshock.ades.ades.Activity.ListPenerimaActivity;
import com.elektroshock.ades.ades.Activity.Util.Penerima;
import com.elektroshock.ades.ades.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.sql.Blob;
import java.util.ArrayList;

public class ListPenerimaAdapter extends RecyclerView.Adapter<ListPenerimaAdapter.MyViewHolder> {

    Context context;
    ArrayList<Penerima> PenerimaList;
    DatabaseHandler dbcenter;
    ListPenerimaActivity listPenerimaActivity;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Bitmap bitmap;

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

                String  id_pembeli, id_driver, no_ktp_penerima, nama_penerima,
                        tlp_penerima, email_penerima, status_kekeluargaan, hobi_penerima,
                        instagram,twitter, youtube, facebook, rating_penerima,
                        comment_penerima, selfie, ttd;

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

                Log.e("FOTO SELFIE ", selfie);
                Log.e("TTD ", ttd);

                selfieString = ListPenerimaActivity.ConvertURLtoBitmap(selfie);
                TTDString = ListPenerimaActivity.ConvertURLtoBitmap(ttd);

                Log.e("STRING SELFIE ", ListPenerimaActivity.ConvertURLtoBitmap(selfie));
                Log.e("STRING TTD ", ListPenerimaActivity.ConvertURLtoBitmap(ttd));

                listPenerimaActivity.kirim(id_pembeli, id_driver, no_ktp_penerima, nama_penerima,
                                tlp_penerima, email_penerima, status_kekeluargaan, hobi_penerima, instagram, twitter,
                                youtube, facebook, rating_penerima, comment_penerima, selfieString, TTDString);

                //dbcenter.deleteAllPenerima();
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
}


