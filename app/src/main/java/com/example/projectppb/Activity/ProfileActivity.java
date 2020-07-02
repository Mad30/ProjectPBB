package com.example.projectppb.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectppb.Model.User;
import com.example.projectppb.R;
import com.example.projectppb.RetrofitUtils.RetrofitClient;
import com.example.projectppb.Storage.SharedprefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    EditText nama,username,email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nama = findViewById(R.id.etnama);
        username = findViewById(R.id.etuser);
        email = findViewById(R.id.etemail);
       showUser();
    }

    private void showUser() {
        User user = SharedprefManager.getInstance(this).getUser();
        Call<List<User>> call = RetrofitClient.getInstance()
                .getApi()
                .getUser(user.getId());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful())
                {
                    Toast.makeText(ProfileActivity.this, "Id tidak ada", Toast.LENGTH_SHORT).show();
                }
                List<User> users = response.body();
                for (User user : users)
                {
                    nama.setText(user.getNama());
                    email.setText(user.getEmail());
                    username.setText(user.getUsername());

                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
