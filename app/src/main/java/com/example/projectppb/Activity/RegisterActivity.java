package com.example.projectppb.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectppb.Model.Registerrespon;
import com.example.projectppb.R;
import com.example.projectppb.RetrofitUtils.RetrofitClient;
import com.example.projectppb.Storage.SharedprefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etnama,etpass,etemail,etuname;
    Button btnreg;
    TextView tvlog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etnama = findViewById(R.id.et_nama);
        etemail = findViewById(R.id.et_email);
        etpass = findViewById(R.id.et_pwd);
        btnreg = findViewById(R.id.btn_sign);
        tvlog = findViewById(R.id.login);
        etuname = findViewById(R.id.et_user);

        btnreg.setOnClickListener(v -> {

            register();

        });

        tvlog.setOnClickListener(a -> startActivity(new Intent(RegisterActivity.this,LoginActivity.class)));
    }
    public void register()
    {
        String nama,email,username,password;
        nama = etnama.getText().toString().trim();
        email = etemail.getText().toString().trim();
        username = etuname.getText().toString().trim();
        password = etpass.getText().toString().trim();

        if (nama.isEmpty())
        {
            etnama.setError("Nama harus diisi");
            etnama.requestFocus();
            return;
        }
        if (nama.length()<2)
        {
            etnama.setError("Nama harus lebih dari 2 karakter");
            etnama.requestFocus();
            return;
        }
        if (email.isEmpty())
        {
            etemail.setError("Email harus diisi");
            etemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            etemail.setError("Email harus valid");
            etemail.requestFocus();
            return;
        }
        if (username.isEmpty())
        {
            etuname.setError("Username harus diisi");
            etuname.requestFocus();
            return;
        }
        if (password.isEmpty())
        {
            etpass.setError("Password harus diisi");
            etpass.requestFocus();
            return;
        }
        if (password.length()<6)
        {
            etpass.setError("Password harus lebih dari 6 karakter");
            etpass.requestFocus();
            return;
        }
        Call<Registerrespon> call = RetrofitClient.getInstance().getApi().registrasi(nama,email,username,password);
        call.enqueue(new Callback<Registerrespon>() {
            @Override
            public void onResponse(Call<Registerrespon> call, Response<Registerrespon> response) {
                    Registerrespon rg = response.body();
                if (!rg.isError()){
                    Toast.makeText(RegisterActivity.this,rg.getMessage(), Toast.LENGTH_SHORT).show();
                }

                    Toast.makeText(RegisterActivity.this,rg.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }

            @Override
            public void onFailure(Call<Registerrespon> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (  SharedprefManager.getInstance(this).login()) {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }
}
