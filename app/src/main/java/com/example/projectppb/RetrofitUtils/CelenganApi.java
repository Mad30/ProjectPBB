package com.example.projectppb.RetrofitUtils;

import com.example.projectppb.Model.Jenis;
import com.example.projectppb.Model.Loginresponse;
import com.example.projectppb.Model.Pendapatan;
import com.example.projectppb.Model.PendapatanResponse;
import com.example.projectppb.Model.Pengeluaran;
import com.example.projectppb.Model.PengeluaranResponse;
import com.example.projectppb.Model.Registerrespon;
import com.example.projectppb.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CelenganApi {

    @GET("jenis")
    Call<List<Jenis>>getJenis();

    @GET("pendapatan")
    Call<List<Pendapatan>>getPendapatan(@Query("id_user") int id_user);

    @GET("pengeluaran")
    Call<List<Pengeluaran>>getPengeluaran(@Query("id_user") int id_user);

    @GET("user")
    Call<List<User>>getUser(@Query("id") int id);

    @FormUrlEncoded
    @POST("register")
    Call<Registerrespon>registrasi(
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("pendapatan")
    Call<PendapatanResponse>tambahPendapatan(
            @Field("tanggal") String tanggal,
            @Field("jumlah") int jumlah,
            @Field("id_user") int id_user
    );

    @FormUrlEncoded
    @POST("pengeluaran")
    Call<PengeluaranResponse>tambahPengeluaran(
            @Field("tanggal") String tanggal,
            @Field("jenis_pengeluaran") String jenis_pengeluaran,
            @Field("jumlah") int jumlah,
            @Field("id_user") int id_user
    );
    @FormUrlEncoded
    @POST("login")
    Call<Loginresponse>login(
            @Field("username") String username,
            @Field("password") String password
    );
}
