package com.example.pe.api;

public class PhongBanAPI {
    public static PhongBanAPIService phongBanService = APIClient.retrofit.create(PhongBanAPIService.class);
}
