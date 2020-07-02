package com.example.projectppb.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectppb.Model.User;
import com.example.projectppb.R;
import com.example.projectppb.Storage.SharedprefManager;

public class MainActivity extends AppCompatActivity {

    LinearLayout earning,expense,report,profile;
    TextView tvuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvuser = findViewById(R.id.tv_nama);
        earning =findViewById(R.id.earning);
        expense = findViewById(R.id.expense);
        report = findViewById(R.id.report);
        profile = findViewById(R.id.profile);

        User user = SharedprefManager.getInstance(this).getUser();

        tvuser.setText(user.getNama().toUpperCase());
        earning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,EarningActivity.class));
            }
        });
        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ExpenseActivity.class));
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ReportActivity.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!SharedprefManager.getInstance(this).login()) {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(i);
        }
    }
}
