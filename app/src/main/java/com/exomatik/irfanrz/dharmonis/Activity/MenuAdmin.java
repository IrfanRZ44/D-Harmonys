package com.exomatik.irfanrz.dharmonis.Activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.exomatik.irfanrz.dharmonis.CustomDialog.CustomDialogAddProfile;
import com.exomatik.irfanrz.dharmonis.CustomDialog.CustomDialogAddVideo;
import com.exomatik.irfanrz.dharmonis.Model.ModelProfile;
import com.exomatik.irfanrz.dharmonis.Model.ModelVideo;
import com.exomatik.irfanrz.dharmonis.R;
import com.exomatik.irfanrz.dharmonis.Recycler.ItemClickSupport;
import com.exomatik.irfanrz.dharmonis.Recycler.RecyclerProfileData;
import com.exomatik.irfanrz.dharmonis.Recycler.RecyclerVideo;
import com.exomatik.irfanrz.dharmonis.Recycler.RecyclerVideoUrl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuAdmin extends AppCompatActivity implements ItemClickSupport.OnItemClickListener {
    private ImageView back;
    private RelativeLayout rlAddVideo, rlAddProfile;
    private RecyclerView rcVideo, rcProfile;
    private ArrayList<ModelVideo> listVideo = new ArrayList<ModelVideo>();
    private ArrayList<ModelProfile> listProfile = new ArrayList<ModelProfile>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        back = (ImageView) findViewById(R.id.back);
        rlAddVideo = (RelativeLayout) findViewById(R.id.rl_add_video);
        rlAddProfile = (RelativeLayout) findViewById(R.id.rl_add_profile);
        rcVideo = (RecyclerView) findViewById(R.id.rc_video);
        rcProfile = (RecyclerView) findViewById(R.id.rc_profile);

        getDataVideo();
        getDataProfile();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rlAddVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogAddVideo.activity = MenuAdmin.this;
                CustomDialogAddVideo.dataVideo = null;
                DialogFragment newFragment = CustomDialogAddVideo
                        .newInstance();

                newFragment.setCancelable(true);

                newFragment.show(MenuAdmin.this.getFragmentManager(), "dialog");
            }
        });

        rlAddProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogAddProfile.dataProfile = null;
                CustomDialogAddProfile.activity = MenuAdmin.this;
                DialogFragment newFragment = CustomDialogAddProfile
                        .newInstance();

                newFragment.setCancelable(true);

                newFragment.show(MenuAdmin.this.getFragmentManager(), "dialog");
            }
        });
    }

    private void getDataVideo(){
        listVideo = new ArrayList<ModelVideo>();
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
            RecyclerVideoUrl adapterAgenda = new RecyclerVideoUrl(listVideo);
            RecyclerView.LayoutManager layoutAgenda = new LinearLayoutManager(MenuAdmin.this, LinearLayoutManager.VERTICAL, false);
            rcVideo.setLayoutManager(layoutAgenda);
            rcVideo.setNestedScrollingEnabled(false);
            rcVideo.setAdapter(adapterAgenda);

            ItemClickSupport.addTo(rcVideo)
                    .setOnItemClickListener(MenuAdmin.this);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(MenuAdmin.this, databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    };

    private void getDataProfile(){
        listProfile = new ArrayList<ModelProfile>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("profile");
        db.addListenerForSingleValueEvent(valueEventListener2);
    }

    ValueEventListener valueEventListener2 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ModelProfile user = snapshot.getValue(ModelProfile.class);
                    listProfile.add(new ModelProfile(user.getNama(), user.getAlamat(), user.getKontak(), user.getImage()
                            , user.getProfesi(), user.isExpandable()));
                }
            }
            RecyclerProfileData adapterAgenda = new RecyclerProfileData(listProfile);
            RecyclerView.LayoutManager layoutAgenda = new LinearLayoutManager(MenuAdmin.this, LinearLayoutManager.VERTICAL, false);
            rcProfile.setLayoutManager(layoutAgenda);
            rcProfile.setNestedScrollingEnabled(false);
            rcProfile.setAdapter(adapterAgenda);

            ItemClickSupport.addTo(rcProfile)
                    .setOnItemClickListener(MenuAdmin.this);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(MenuAdmin.this, databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MenuAdmin.this, MainActivity.class));
        finish();
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        if (recyclerView == rcVideo){
            CustomDialogAddVideo.activity = MenuAdmin.this;
            CustomDialogAddVideo.dataVideo = new ModelVideo(listVideo.get(position).getVideoUrl(), listVideo.get(position).getTitelVideo());
            DialogFragment newFragment = CustomDialogAddVideo
                    .newInstance();

            newFragment.setCancelable(true);

            newFragment.show(MenuAdmin.this.getFragmentManager(), "dialog");
        }
        else if (recyclerView == rcProfile){
            CustomDialogAddProfile.activity = MenuAdmin.this;
            CustomDialogAddProfile.dataProfile = new ModelProfile(listProfile.get(position).getNama(),
                    listProfile.get(position).getAlamat(), listProfile.get(position).getKontak(),
                    listProfile.get(position).getImage(), listProfile.get(position).getProfesi(),
                    listProfile.get(position).isExpandable());
            DialogFragment newFragment = CustomDialogAddProfile
                    .newInstance();

            newFragment.setCancelable(true);

            newFragment.show(MenuAdmin.this.getFragmentManager(), "dialog");
        }
    }
}

