package com.example.projectppb.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectppb.Model.Jenis;
import com.example.projectppb.Model.PengeluaranResponse;
import com.example.projectppb.Model.User;
import com.example.projectppb.R;
import com.example.projectppb.RetrofitUtils.RetrofitClient;
import com.example.projectppb.Storage.SharedprefManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseActivity extends AppCompatActivity {

    EditText ettgl,etnom,etid;
    Spinner sp;
    DatePickerDialog.OnDateSetListener setListener;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        ettgl = findViewById(R.id.ettgl);
        etnom = findViewById(R.id.etjml);
        sp = findViewById(R.id.spjen);
        btn = findViewById(R.id.btn_exp);
        etid = findViewById(R.id.etid);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        User user = SharedprefManager.getInstance(this).getUser();
        String id = String.valueOf(user.getId());
        etid.setText(id);

        ettgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ExpenseActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
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
                ettgl.setText(date);
            }
        };

        showJenis();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahPengeluaran();
            }
        });
    }

    private void showJenis() {
        Call<List<Jenis>> call = RetrofitClient.
                getInstance()
                .getApi()
                .getJenis();

        call.enqueue(new Callback<List<Jenis>>() {
            @Override
            public void onResponse(Call<List<Jenis>> call, Response<List<Jenis>> response) {
                if (!response.isSuccessful())
                {
                    Toast.makeText(ExpenseActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
                List<Jenis> jenis = response.body();
                ArrayList<String> jen_peng = new ArrayList<>();
                for (int i = 0; i<jenis.size();i++)
                {
                  jen_peng.add(jenis.get(i).getJenis());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ExpenseActivity.this,
                        android.R.layout.simple_spinner_item, jen_peng);
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                sp.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Jenis>> call, Throwable t) {
                Toast.makeText(ExpenseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void tambahPengeluaran() {
        String tanggal,jenis_pengeluaran;
        int jumlah,id_user;
        String jml =etnom.getText().toString().trim();

        tanggal = ettgl.getText().toString().trim();
        jenis_pengeluaran = sp.getSelectedItem().toString();


        if (tanggal.isEmpty())
        {
            ettgl.setError("Tanggal harus diisi");
            ettgl.requestFocus();
            return;
        }
        if (jml.isEmpty())
        {
            etnom.setError("Nominal harus diisi");
            etnom.requestFocus();
            return;
        }
        jumlah = Integer.parseInt(jml);
        id_user =Integer.parseInt(etid.getText().toString().trim());

        Call <PengeluaranResponse> call = RetrofitClient.getInstance()
                .getApi()
                .tambahPengeluaran(tanggal,jenis_pengeluaran,jumlah,id_user);
        call.enqueue(new Callback<PengeluaranResponse>() {
            @Override
            public void onResponse(Call<PengeluaranResponse> call, Response<PengeluaranResponse> response) {
                PengeluaranResponse pr = response.body();

                if(!pr.isError())
                {
                    Toast.makeText(ExpenseActivity.this, pr.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(ExpenseActivity.this, pr.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ExpenseActivity.this,MainActivity.class));
            }

            @Override
            public void onFailure(Call<PengeluaranResponse> call, Throwable t) {
                Toast.makeText(ExpenseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
