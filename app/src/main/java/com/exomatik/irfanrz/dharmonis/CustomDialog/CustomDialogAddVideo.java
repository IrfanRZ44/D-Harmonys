package com.exomatik.irfanrz.dharmonis.CustomDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.irfanrz.dharmonis.Activity.MenuAdmin;
import com.exomatik.irfanrz.dharmonis.Model.ModelVideo;
import com.exomatik.irfanrz.dharmonis.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by IrfanRZ on 03/11/2018.
 */

public class CustomDialogAddVideo extends DialogFragment {
    private RelativeLayout btnTambahVideo, btnDeleteVideo;
    private EditText etNamaVideo, etUrlVideo;
    private ProgressDialog progressDialog;
    private TextView textUpload;
    public static Activity activity;
    public static ModelVideo dataVideo;

    public static CustomDialogAddVideo newInstance() {
        return new CustomDialogAddVideo();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_add_video, container, false);

        btnTambahVideo = (RelativeLayout) dialogView.findViewById(R.id.rl_tambah_news);
        btnDeleteVideo = (RelativeLayout) dialogView.findViewById(R.id.rl_delete_video);
        etNamaVideo = (EditText) dialogView.findViewById(R.id.et_nama_news);
        etUrlVideo = (EditText) dialogView.findViewById(R.id.et_desc_news);
        textUpload = (TextView) dialogView.findViewById(R.id.btn_add_news);

        if (dataVideo == null){
            etNamaVideo.setText("");
            etUrlVideo.setText("");
            textUpload.setText("Tambah Video");
            btnDeleteVideo.setVisibility(View.GONE);
        }else {
            etNamaVideo.setText(dataVideo.getTitelVideo());
            etUrlVideo.setText(dataVideo.getVideoUrl());
            textUpload.setText("Edit Video");
        }

        btnTambahVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahBerita(v);
            }
        });
        btnDeleteVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteClick(v);
            }
        });

        return dialogView;
    }

    private void tambahBerita(View v) {
        String nama = etNamaVideo.getText().toString();
        String desc = etUrlVideo.getText().toString();

        if ((nama.isEmpty()) || (desc.isEmpty())) {
            Snackbar.make(v, "Mohon, untuk melengkapi data dengan valid", Snackbar.LENGTH_LONG).show();
        } else {
            //creating and showing progress dialog
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMax(100);
            progressDialog.setMessage("Uploading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            progressDialog.setCancelable(false);

            uploadVideo(v, nama, desc);
        }
    }

    public void onDeleteClick(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Are you sure you want to delete?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Deleting", Toast.LENGTH_SHORT).show();
                DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().child("video")
                        .child("id_" + dataVideo.getVideoUrl());
                db_node.removeValue();
                startActivity(new Intent(getActivity(), MenuAdmin.class));
                getActivity().finish();

                progressDialog.dismiss();
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    public void uploadVideo(View view, final String nama, final String url) {
        if (dataVideo == null){

        }else {
            DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().child("video")
                    .child("id_" + dataVideo.getVideoUrl());
            db_node.removeValue();
        }

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        ModelVideo data = new ModelVideo(url, nama);
        database.child("video")
                .child("id_" + url)
                .setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Berhasil Tambah Video", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), MenuAdmin.class));
                            getActivity().finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
