package com.elektroshock.ades.ades.Activity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elektroshock.ades.ades.Activity.KonsumenActivity;
import com.elektroshock.ades.ades.Activity.Util.Konsumen;
import com.elektroshock.ades.ades.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ListKonsumenAdapter extends RecyclerView.Adapter<ListKonsumenAdapter.MyViewHolder> {

    Context context;
    ArrayList<Konsumen> konsumenList;

    public ListKonsumenAdapter(Context context, ArrayList<Konsumen> konsumen) {
        this.context = context;
        this.konsumenList = konsumen;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.konsumen_list, parent, false);

        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ListKonsumenAdapter.MyViewHolder holder, int position) {
        final Konsumen konsumen = konsumenList.get(position);
        holder.tv.setText(konsumen.getPembeli_nama());
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout rl;
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);

            rl = (LinearLayout) itemView.findViewById(R.id.rl_konsumen);
            tv = (TextView) itemView.findViewById(R.id.text_konsumen);

        }
    }
}
