package com.example.pe.api;

public class NhanVienAPI {
    public static NhanVienAPIService nhanVienService = APIClient.retrofit.create(NhanVienAPIService.class);
}
