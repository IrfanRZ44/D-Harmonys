package com.exomatik.irfanrz.dharmonis.Recycler;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exomatik.irfanrz.dharmonis.Model.ModelProfile;
import com.exomatik.irfanrz.dharmonis.Model.ModelVideo;
import com.exomatik.irfanrz.dharmonis.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by IrfanRZ on 17/09/2018.
 */

public class RecyclerProfileData extends RecyclerView.Adapter<RecyclerProfileData.bidangViewHolder> {
    private ArrayList<ModelProfile> dataList;
    private Context context;

    public RecyclerProfileData(ArrayList<ModelProfile> dataList) {
        this.dataList = dataList;
    }

    @Override
    public bidangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_profile_data, parent, false);
        this.context = parent.getContext();
        return new bidangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(bidangViewHolder holder, int position) {

        holder.textNama.setText(dataList.get(position).getNama());
        holder.textUrl.setText(dataList.get(position).getKontak());
        holder.textAlamat.setText(dataList.get(position).getAlamat());

        Uri imageUri = Uri.parse(dataList.get(position).getImage());
        Picasso.with(context).load(imageUri).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class bidangViewHolder extends RecyclerView.ViewHolder{
        private TextView textNama, textUrl, textAlamat;
        private CircleImageView image;

        public bidangViewHolder(View itemView) {
            super(itemView);

            textNama = (TextView) itemView.findViewById(R.id.nama);
            textUrl = (TextView) itemView.findViewById(R.id.url);
            textAlamat = (TextView) itemView.findViewById(R.id.alamat);
            image = (CircleImageView) itemView.findViewById(R.id.img_profile);

        }
    }
}