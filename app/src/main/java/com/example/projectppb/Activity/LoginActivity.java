package com.example.projectppb.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.projectppb.Model.Loginresponse;
import com.example.projectppb.Model.User;
import com.example.projectppb.R;
import com.example.projectppb.RetrofitUtils.RetrofitClient;
import com.example.projectppb.Storage.SharedprefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Toolbar tb;
    EditText et_uname, et_pass;
    TextView tv_reg;
    Button btnlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        et_uname = findViewById(R.id.uname);
        et_pass = findViewById(R.id.pass);
        tv_reg = findViewById(R.id.register);
        btnlog = findViewById(R.id.btn_login);

        tv_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        String username, password;
        username = et_uname.getText().toString().trim();
        password = et_pass.getText().toString().trim();

        if (username.isEmpty()) {
            et_uname.setError("Username harus diisi");
            et_uname.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            et_pass.setError("Password harus diisi");
            et_pass.requestFocus();
            return;
        }
        Call<Loginresponse> call = RetrofitClient.getInstance().getApi().login(username, password);
        call.enqueue(new Callback<Loginresponse>() {
            @Override
            public void onResponse(Call<Loginresponse> call, Response<Loginresponse> response) {
                Loginresponse lr = response.body();

                if (!lr.isError()) {
                    Toast.makeText(LoginActivity.this, lr.getMessage(), Toast.LENGTH_LONG).show();
                }
                SharedprefManager.getInstance(LoginActivity.this).SaveUser(lr.getUser());
               Intent i= new Intent(LoginActivity.this,MainActivity.class);
               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
              startActivity(i);

            }

            @Override
            public void onFailure(Call<Loginresponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
