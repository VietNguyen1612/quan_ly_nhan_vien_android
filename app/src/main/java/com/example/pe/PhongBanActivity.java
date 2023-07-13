package com.example.pe;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pe.adapters.PhongBanAdapter;
import com.example.pe.api.NhanVienAPI;
import com.example.pe.api.PhongBanAPI;
import com.example.pe.listener.IOnClickPhongBanListener;
import com.example.pe.models.NhanVien;
import com.example.pe.models.PhongBan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhongBanActivity extends AppCompatActivity {
    private List<PhongBan> phongBanList;
    private List<NhanVien> nhanVienList;
    private RecyclerView recyclerView;
    private Button btn;
    private EditText phongBanName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phongban);
        btn = findViewById(R.id.btn_addPhongBan);
        phongBanName = findViewById(R.id.edt_phongBanName);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPhongBan();
            }
        });
        recyclerView = findViewById(R.id.rcv_phongban);
        getNhanVienList();
    }

    private void displayPhongBanList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        PhongBanAdapter phongBanAdapter = new PhongBanAdapter(this, phongBanList, new IOnClickPhongBanListener() {
            @Override
            public void onClickDeletePhongBan(PhongBan phongBan) {
                onClickDeletePhongBanDialog(phongBan);
            }
        });
        recyclerView.setAdapter(phongBanAdapter);
    }

    private void onClickDeletePhongBanDialog(PhongBan phongBan){
        new AlertDialog.Builder(PhongBanActivity.this)
                .setTitle("Xác nhận xóa")
                .setMessage("Sau khi xóa sẽ không khôi phục được")
                .setPositiveButton("Đồng ý", ((dialogInterface, i) -> {
                    deletePhongBan(phongBan);
                }))
                .setNegativeButton("Hủy bỏ",null)
                .show();
    }

    private void deletePhongBan(PhongBan phongBan){
        for (NhanVien nhanVien: nhanVienList) {
            if(nhanVien.getIdPB().equals(phongBan.getIdPB())){
                Toast.makeText(PhongBanActivity.this, "Phòng ban đang có nhân viên, không thể xóa", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Call<Void> call = PhongBanAPI.phongBanService.deletePhongBan(phongBan.getIdPB());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PhongBanActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    getNhanVienList();
                } else {
                    Toast.makeText(PhongBanActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

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
                    Toast.makeText(PhongBanActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
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
                    displayPhongBanList();
                } else {
                    Log.d("error",response.toString());
                    Toast.makeText(PhongBanActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PhongBan>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void addPhongBan(){
        if(phongBanName.getText().toString()==null || phongBanName.getText().toString().isEmpty()){
            Toast.makeText(this, "Nhập tên phòng ban", Toast.LENGTH_SHORT).show();
            return;
        }
        int maxId = 0;
        for (PhongBan phongBanObj: phongBanList) {
            int id = Integer.parseInt(phongBanObj.getIdPB());
            if(id>maxId){
                maxId = id;
            }
        }
        String newId =String.valueOf(maxId + 1);
        PhongBan phongBanResquest = new PhongBan(newId,phongBanName.getText().toString());
        Call<Void> call = PhongBanAPI.phongBanService.createPhongBan(phongBanResquest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PhongBanActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    phongBanName.setText("");
                    getNhanVienList();
                } else {
                    Toast.makeText(PhongBanActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
