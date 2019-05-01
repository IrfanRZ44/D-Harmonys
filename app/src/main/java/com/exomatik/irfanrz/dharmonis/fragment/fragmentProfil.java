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

import com.exomatik.irfanrz.dharmonis.Activity.MenuAdmin;
import com.exomatik.irfanrz.dharmonis.Model.ModelProfile;
import com.exomatik.irfanrz.dharmonis.R;
import com.exomatik.irfanrz.dharmonis.Recycler.ItemClickSupport;
import com.exomatik.irfanrz.dharmonis.Recycler.MyAdapter;
import com.exomatik.irfanrz.dharmonis.Recycler.RecyclerProfileData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragmentProfil extends Fragment  {
    private View view;
    RecyclerView rcProfile;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelProfile> listProfile = new ArrayList<ModelProfile>();

    public fragmentProfil() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profil, container, false);

        rcProfile = (RecyclerView) view.findViewById(R.id.rc_profil);
        rcProfile.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rcProfile.setLayoutManager(layoutManager);

        getDataProfile();

        return view;
    }

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
            setData();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getActivity(), databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    };

    private void setData() {
        MyAdapter adapter = new MyAdapter(listProfile);
        rcProfile.setAdapter(adapter);
    }
}
