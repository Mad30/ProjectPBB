package com.example.projectppb.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectppb.Model.Pendapatan;
import com.example.projectppb.Model.PendapatanResponse;
import com.example.projectppb.Model.User;
import com.example.projectppb.R;
import com.example.projectppb.RetrofitUtils.RetrofitClient;
import com.example.projectppb.Storage.SharedprefManager;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarningActivity extends AppCompatActivity {

    EditText tgl,jml,id;
    DatePickerDialog.OnDateSetListener setListener;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning);

        tgl = findViewById(R.id.tanggal);
        jml = findViewById(R.id.jumlah);
        btn = findViewById(R.id.btn_save);
        id = findViewById(R.id.id_user);

        User user = SharedprefManager.getInstance(this).getUser();
        id.setText(String.valueOf(user.getId()));

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EarningActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth) {
                month1 = month1 + 1;
                String date = year1 + "-" + month1 + "-" + dayOfMonth;
                tgl.setText(date);
            }
        };


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahPendapatan();
            }
        });

    }

    private void tambahPendapatan() {
        String tanggal,iduser,jml1;
        int id_user,jumlah;

        tanggal = tgl.getText().toString().trim();
        jml1 = jml.getText().toString().trim();
        iduser = id.getText().toString().trim();

        if (tanggal.isEmpty())
        {
            tgl.setError("Tanggal harus diisi");
            tgl.requestFocus();
            return;
        }
        if (jml1.isEmpty())
        {
            jml.setError("Nominal harus diisi");
            jml.requestFocus();
            return;
        }
        id_user = Integer.parseInt(iduser);
        jumlah = Integer.parseInt(jml1);

        Call<PendapatanResponse> call = RetrofitClient.getInstance()
                .getApi()
                .tambahPendapatan(tanggal,jumlah,id_user);

        call.enqueue(new Callback<PendapatanResponse>() {
            @Override
            public void onResponse(Call<PendapatanResponse> call, Response<PendapatanResponse> response) {
                PendapatanResponse pr = response.body();
                if (!pr.isError()){
                    Toast.makeText(EarningActivity.this, pr.getMessage(), Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(EarningActivity.this, pr.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EarningActivity.this,MainActivity.class));
            }

            @Override
            public void onFailure(Call<PendapatanResponse> call, Throwable t) {
                Toast.makeText(EarningActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
