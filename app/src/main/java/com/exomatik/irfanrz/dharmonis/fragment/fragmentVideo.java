package com.exomatik.irfanrz.dharmonis.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.exomatik.irfanrz.dharmonis.Model.ModelVideo;
import com.exomatik.irfanrz.dharmonis.R;
import com.exomatik.irfanrz.dharmonis.Recycler.RecyclerVideo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Vector;


/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragmentVideo extends Fragment  {
    private View view;
    private RecyclerView recyclerVideo;
//    private GridView gridView;
    ArrayList<ModelVideo> listVideo = new ArrayList<ModelVideo>();

    public fragmentVideo() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video, container, false);

        recyclerVideo = (RecyclerView) view.findViewById(R.id.rc_video);

        getDataVideo();

        return view;
    }

    private void getDataVideo(){
        listVideo = new ArrayList<ModelVideo>();

        recyclerVideo.setHasFixedSize(true);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("video");
        db.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ModelVideo user = snapshot.getValue(ModelVideo.class);
                    listVideo.add(new ModelVideo(user.getVideoUrl(), user.getTitelVideo()));

                }
            }
//            RecyclerVideo adapterAgenda = new RecyclerVideo(listVideo, getActivity(), getContext());
//            RecyclerView.LayoutManager layoutAgenda = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//            recyclerVideo.setLayoutManager(layoutAgenda);
//            recyclerVideo.setNestedScrollingEnabled(false);
//            recyclerVideo.setAdapter(adapterAgenda);

            //to use RecycleView, you need a layout manager. default is LinearLayoutManager
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerVideo.setLayoutManager(linearLayoutManager);
            RecyclerVideo adapter=new RecyclerVideo(getContext(), listVideo);
            recyclerVideo.setAdapter(adapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getActivity(), databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    };
}
