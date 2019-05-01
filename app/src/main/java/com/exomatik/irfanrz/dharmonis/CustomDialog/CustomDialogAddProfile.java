package com.exomatik.irfanrz.dharmonis.CustomDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.exomatik.irfanrz.dharmonis.Model.ModelProfile;
import com.exomatik.irfanrz.dharmonis.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by IrfanRZ on 03/11/2018.
 */

public class CustomDialogAddProfile extends DialogFragment {
    private RelativeLayout btnTambahVideo, btnDeleteVideo, btnFoto;
    private EditText etNama, etAlamat, etKontak, etProfesi;
    private ProgressDialog progressDialog;
    private TextView textUpload;
    public static Activity activity;
    public static ModelProfile dataProfile;
    private static int PICK_IMAGE = 100;
    private Uri imageUri;
    private CircleImageView image;
    private StorageReference mStorageRef;

    public static CustomDialogAddProfile newInstance() {
        return new CustomDialogAddProfile();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_add_profile, container, false);

        btnTambahVideo = (RelativeLayout) dialogView.findViewById(R.id.rl_tambah);
        btnDeleteVideo = (RelativeLayout) dialogView.findViewById(R.id.rl_delete);
        btnFoto = (RelativeLayout) dialogView.findViewById(R.id.rl_foto);
        etNama = (EditText) dialogView.findViewById(R.id.et_nama);
        etAlamat = (EditText) dialogView.findViewById(R.id.et_alamat);
        etKontak = (EditText) dialogView.findViewById(R.id.et_kontak);
        etProfesi = (EditText) dialogView.findViewById(R.id.et_profesi);
        textUpload = (TextView) dialogView.findViewById(R.id.btn_add);
        image = (CircleImageView) dialogView.findViewById(R.id.img_foto);

        if (dataProfile == null){
            etNama.setText("");
            etKontak.setText("");
            etAlamat.setText("");
            etProfesi.setText("");
            textUpload.setText("Tambah");
            btnDeleteVideo.setVisibility(View.GONE);
        }else {
            etNama.setText(dataProfile.getNama());
            etKontak.setText(dataProfile.getKontak());
            etAlamat.setText(dataProfile.getAlamat());
            etProfesi.setText(dataProfile.getProfesi());
            textUpload.setText("Edit");
        }
        image.setVisibility(View.GONE);
        btnFoto.setVisibility(View.VISIBLE);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foto();
            }
        });
        btnTambahVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahProfile(v);
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

    private void foto() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mohon Tunggu...");
        progressDialog.setTitle("Proses");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private void tambahProfile(View v) {
        String nama = etNama.getText().toString();
        String alamat = etAlamat.getText().toString();
        String kontak = etKontak.getText().toString();
        String profesi = etProfesi.getText().toString();

        if ((nama.isEmpty()) || (alamat.isEmpty()) || (kontak.isEmpty()) || (profesi.isEmpty())) {
            Snackbar.make(v, "Mohon, untuk melengkapi data dengan valid", Snackbar.LENGTH_LONG).show();
        } else {
            //creating and showing progress dialog
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMax(100);
            progressDialog.setMessage("Uploading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            progressDialog.setCancelable(false);

            uploadVideo(v, nama, alamat, kontak, profesi);
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
                DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().child("profile")
                        .child("id_" + dataProfile.getNama());
                db_node.removeValue();
                startActivity(new Intent(getActivity(), MenuAdmin.class));
                getActivity().finish();

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

    public void uploadVideo(View view, final String nama, final String alamat, final String kontak, final String profesi) {
        if (dataProfile == null){

        }else {
            DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().child("profile")
                    .child("id_" + dataProfile.getNama());
            db_node.removeValue();
        }

        //accessing the firebase storage
        StorageReference storageReference = mStorageRef.child("storage/" + imageUri.getLastPathSegment());
        UploadTask uploadTask = storageReference.putFile(imageUri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUri = taskSnapshot.getDownloadUrl();
                String uri = downloadUri.toString();

                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                ModelProfile data = new ModelProfile(nama, alamat, kontak, uri, profesi, true);
                database.child("profile")
                        .child("id_" + nama)
                        .setValue(data)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "Berhasil Tambah", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getActivity(), MenuAdmin.class));
                                    getActivity().finish();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                Toast.makeText(getActivity(), "Succes Uploading Foto", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "errror " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            image.setVisibility(View.VISIBLE);
            btnFoto.setVisibility(View.GONE);
            Picasso.with(getActivity()).load(imageUri).into(image);
        }
        progressDialog.dismiss();
    }
}
