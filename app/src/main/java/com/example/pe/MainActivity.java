package com.example.pe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pe.adapters.NhanVienAdapter;
import com.example.pe.api.NhanVienAPI;
import com.example.pe.models.NhanVien;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button nhanVienBtn;
    private Button phongBanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nhanVienBtn = findViewById(R.id.btn_nhanvien);
        phongBanBtn = findViewById(R.id.btn_phongban);

        nhanVienBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NhanVienActivity.class);
                startActivity(intent);
            }
        });

        phongBanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PhongBanActivity.class);
                startActivity(intent);
            }
        });
    }
}