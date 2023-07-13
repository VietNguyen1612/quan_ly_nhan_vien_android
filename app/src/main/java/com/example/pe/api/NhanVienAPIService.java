package com.example.pe.api;

import com.example.pe.models.NhanVien;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NhanVienAPIService {
    @GET("nhanvien")
    Call<List<NhanVien>> getNhanVienList();

    @POST("nhanvien")
    Call<Void> createNhanVien(@Body NhanVien nhanVien);

    @PUT("nhanvien/{id}")
    Call<Void> updateNhanVien(@Path("id") String nhanVienId, @Body NhanVien nhanVien);

    @DELETE("nhanvien/{id}")
    Call<Void> deleteNhanVien(@Path("id") String nhanVienId);
}
