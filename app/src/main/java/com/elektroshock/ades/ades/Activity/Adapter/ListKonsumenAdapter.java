package com.elektroshock.ades.ades.Activity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.elektroshock.ades.ades.Activity.DatabaseHandler.DatabaseHandler;
import com.elektroshock.ades.ades.Activity.KonsumenActivity;
import com.elektroshock.ades.ades.Activity.Util.Konsumen;
import com.elektroshock.ades.ades.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListKonsumenAdapter extends RecyclerView.Adapter<ListKonsumenAdapter.MyViewHolder> {

    Context context;
    ArrayList<Konsumen> konsumenList;

    DatabaseHandler db;

    public ListKonsumenAdapter(Context context, ArrayList<Konsumen> konsumen) {
        this.context = context;
        this.konsumenList = konsumen;
        db = new DatabaseHandler(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.konsumen_list, parent, false);

        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListKonsumenAdapter.MyViewHolder holder, final int position) {
        final Konsumen konsumen = konsumenList.get(position);


        Glide.with(context)
                .asBitmap()
                .load(konsumen.getPembeli_map())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        String uriImage = saveImage(resource, position);
                        db.updateKonsumen(konsumen.getPembeli_id(), uriImage);
                        konsumen.setPembeli_map(uriImage);
                    }
                });

        holder.tv.setText(konsumen.getPembeli_nama());
        holder.tvuri.setText(konsumen.getPembeli_kontak());

        holder.rl.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("id", konsumen.getPembeli_id());
                bundle.putString("nama", konsumen.getPembeli_nama());
                bundle.putString("ttl", konsumen.getPembeli_ttl());
                bundle.putString("hape", konsumen.getPembeli_kontak());
                bundle.putString("alamat", konsumen.getPembeli_alamat());
                bundle.putString("type", konsumen.getPembeli_type());
                bundle.putString("warna", konsumen.getPembeli_warna());
                bundle.putString("mesin", konsumen.getPembeli_mesin());
                bundle.putString("peta", konsumen.getPembeli_map());

                Intent i = new Intent(context, KonsumenActivity.class);
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return konsumenList.size();
    }

    private String saveImage(Bitmap image, int index) {
        String savedImagePath = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String dates = sdf.format(timestamp);

        String imageFileName = index+"_"+dates + "_map" + ".jpg";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/STATICMAP");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return savedImagePath;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout rl;
        TextView tv, tvuri;

        public MyViewHolder(View itemView) {
            super(itemView);

            rl = (LinearLayout) itemView.findViewById(R.id.rl_konsumen);
            tv = (TextView) itemView.findViewById(R.id.text_konsumen);
            tvuri = (TextView) itemView.findViewById(R.id.no_hp_konsumen);
        }
    }
}
