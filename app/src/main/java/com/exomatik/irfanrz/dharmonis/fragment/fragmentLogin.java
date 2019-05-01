package com.exomatik.irfanrz.dharmonis.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.exomatik.irfanrz.dharmonis.Activity.MenuAdmin;
import com.exomatik.irfanrz.dharmonis.R;


/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragmentLogin extends Fragment {
    private View view;
    private EditText userEmail, userPass;
    private Button userLogin;
    private Button showPassword;
    private int show = 0;
    private ProgressDialog progressDialog = null;

    public fragmentLogin() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login_user, container, false);

        userEmail = (EditText) view.findViewById(R.id.userNim);
        userPass = (EditText) view.findViewById(R.id.userPass);
        userLogin = (Button) view.findViewById(R.id.userLogin);
        showPassword = (Button) view.findViewById(R.id.show_password);

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show == 1) {
                    show = 0;
                    showPassword.setBackgroundResource(R.drawable.ic_dont_show);
                    userPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else if (show == 0) {
                    show = 1;
                    showPassword.setBackgroundResource(R.drawable.ic_show);
                    userPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Mohon Tunggu...");
                progressDialog.setTitle("Proses");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String emailId = userEmail.getText().toString();
                String password = userPass.getText().toString();

                if (emailId.equals(getResources().getString(R.string.email)) &&
                        (password.equals(getResources().getString(R.string.pass)))){
                    startActivity(new Intent(getActivity(), MenuAdmin.class));
                    getActivity().finish();
                }else {
                    Snackbar snackbar = Snackbar
                            .make(v, "Mohon maaf, email dan password anda salah", Snackbar.LENGTH_LONG);

                    snackbar.show();
                    progressDialog.dismiss();
                }
            }
        });

        return view;
    }
}
