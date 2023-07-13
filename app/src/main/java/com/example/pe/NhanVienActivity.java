package com.example.pe;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pe.adapters.NhanVienAdapter;
import com.example.pe.api.NhanVienAPI;
import com.example.pe.api.PhongBanAPI;
import com.example.pe.listener.IOnClickNhanVienListener;
import com.example.pe.models.NhanVien;
import com.example.pe.models.PhongBan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NhanVienActivity extends AppCompatActivity {
    private List<NhanVien> nhanVienList;
    private List<PhongBan> phongBanList;
    private RecyclerView recyclerView;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhanvien);
        btn = findViewById(R.id.btn_goToAddNhanVien);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NhanVienActivity.this, AddNhanVienActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.rcv_nhanvien);
        getNhanVienList();
    }
    private void displayNhanVienList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        NhanVienAdapter nhanVienAdapter = new NhanVienAdapter(this, phongBanList, nhanVienList, new IOnClickNhanVienListener() {
            @Override
            public void onClickDeleteNhanVien(NhanVien nhanVien) {
                onClickDeleteNhanVienDialog(nhanVien);
            }
        });
        recyclerView.setAdapter(nhanVienAdapter);
    }

    private void onClickDeleteNhanVienDialog(NhanVien nhanVien){
        new AlertDialog.Builder(NhanVienActivity.this)
                .setTitle("Xác nhận xóa")
                .setMessage("Sau khi xóa sẽ không khôi phục được")
                .setPositiveButton("Đồng ý", ((dialogInterface, i) -> {
                    deleteNhanVien(nhanVien);
                }))
                .setNegativeButton("Hủy bỏ",null)
                .show();
    }


    private void deleteNhanVien(NhanVien nhanVien){
        Call<Void> call = NhanVienAPI.nhanVienService.deleteNhanVien(nhanVien.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(NhanVienActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    getNhanVienList();
                } else {
                    Toast.makeText(NhanVienActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getNhanVienList(){
        Call<List<NhanVien>> call = NhanVienAPI.nhanVienService.getNhanVienList();
        call.enqueue(new Callback<List<NhanVien>>() {
            @Override
            public void onResponse(Call<List<NhanVien>> call, Response<List<NhanVien>> response) {
                if(response.isSuccessful()){
                    nhanVienList = response.body();
                    getPhongBanList();
                } else {
                    Log.d("error",response.toString());
                    Toast.makeText(NhanVienActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NhanVien>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getPhongBanList(){
        Call<List<PhongBan>> call = PhongBanAPI.phongBanService.getPhongBanList();
        call.enqueue(new Callback<List<PhongBan>>() {
            @Override
            public void onResponse(Call<List<PhongBan>> call, Response<List<PhongBan>> response) {
                if(response.isSuccessful()){
                    phongBanList = response.body();
                    displayNhanVienList();
                } else {
                    Log.d("error",response.toString());
                    Toast.makeText(NhanVienActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PhongBan>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNhanVienList();
    }
}
