package com.exomatik.irfanrz.dharmonis.Recycler;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.exomatik.irfanrz.dharmonis.Model.ModelVideo;
import com.exomatik.irfanrz.dharmonis.R;

import java.util.List;

/**
 * Created by IrfanRZ on 17/09/2018.
 */

public class RecyclerVideoUrl extends RecyclerView.Adapter<RecyclerVideoUrl.bidangViewHolder> {
    private List<ModelVideo> dataList;
    private Context context;

    public RecyclerVideoUrl(List<ModelVideo> dataList) {
        this.dataList = dataList;
    }

    @Override
    public bidangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_video_url, parent, false);
        this.context = parent.getContext();
        return new bidangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(bidangViewHolder holder, int position) {

        holder.textNama.setText(dataList.get(position).getTitelVideo());
        holder.textUrl.setText(dataList.get(position).getVideoUrl());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class bidangViewHolder extends RecyclerView.ViewHolder{
        private TextView textNama, textUrl;


        public bidangViewHolder(View itemView) {
            super(itemView);

            textNama = (TextView) itemView.findViewById(R.id.nama);
            textUrl = (TextView) itemView.findViewById(R.id.url);

        }
    }
}