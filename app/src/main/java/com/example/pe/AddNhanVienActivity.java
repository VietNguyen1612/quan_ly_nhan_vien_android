package com.example.pe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pe.api.NhanVienAPI;
import com.example.pe.api.PhongBanAPI;
import com.example.pe.models.NhanVien;
import com.example.pe.models.PhongBan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNhanVienActivity extends AppCompatActivity {
    private boolean isUpdate;
    private EditText name, date, gender, salary, phongban;
    private Button btn;
    private List<NhanVien> nhanVienList;
    private List<PhongBan> phongBanList;
    private NhanVien nhanVien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nhanvien);
        mappingView();
        getDataIntent();
        getNhanVienList();
    }

    private void mappingView(){
        name = findViewById(R.id.edt_name);
        date = findViewById(R.id.edt_date);
        gender = findViewById(R.id.edt_gender);
        salary = findViewById(R.id.edt_salary);
        phongban = findViewById(R.id.edt_phongban);
        btn = findViewById(R.id.btn_addNhanVien);
    }

    private void getDataIntent(){
        Bundle bundleReceived = getIntent().getExtras();
        if(bundleReceived != null){
            isUpdate = true;
            nhanVien = (NhanVien) bundleReceived.get("nhanvien_object");
        }
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
                    Toast.makeText(AddNhanVienActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
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
                    displayNhanVienInfo();
                } else {
                    Log.d("error",response.toString());
                    Toast.makeText(AddNhanVienActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PhongBan>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void displayNhanVienInfo(){
        if(isUpdate){
            btn.setText("Chỉnh sửa");
            name.setText(nhanVien.getName());
            date.setText(nhanVien.getDate());
            gender.setText(nhanVien.getGender());
            salary.setText(nhanVien.getSalary());
            for (PhongBan phongBan: phongBanList) {
                if(nhanVien.getIdPB().equals(phongBan.getIdPB())){
                    phongban.setText(phongBan.getNamePB());
                    break;
                }
            }
        } else {
            btn.setText("Thêm");
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrUpdateNhanVien();
            }
        });
    }

    private void addOrUpdateNhanVien(){
        boolean checkPhongBan = false;
        String phongBanId = "";
        String strName = name.getText().toString().trim();
        String strDate = date.getText().toString().trim();
        String strGender = gender.getText().toString().trim();
        String strSalary = salary.getText().toString().trim();
        String strPhongBan = phongban.getText().toString();

        if(strName==null || strName.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
            return;
        }
        if(strDate==null || strDate.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập ngày sinh", Toast.LENGTH_SHORT).show();
            return;
        }
        if(strGender==null || strGender.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập giới tính", Toast.LENGTH_SHORT).show();
            return;
        }
        if(strSalary==null || strSalary.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập lương", Toast.LENGTH_SHORT).show();
            return;
        }
        if(strPhongBan==null || strPhongBan.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập phòng ban", Toast.LENGTH_SHORT).show();
            return;
        }
        for (PhongBan phongBan: phongBanList) {
            if(strPhongBan.equals(phongBan.getNamePB())){
                checkPhongBan = true;
                phongBanId = phongBan.getIdPB();
                break;
            }
        }
        if(!checkPhongBan){
            Toast.makeText(this, "Phòng ban không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        if(isUpdate){
            NhanVien nhanVienRequest = new NhanVien(nhanVien.getId(),strName,strDate,strGender,strSalary,phongBanId);
            Call<Void> call = NhanVienAPI.nhanVienService.updateNhanVien(nhanVien.getId(), nhanVienRequest);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(AddNhanVienActivity.this, "Chỉnh thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddNhanVienActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            int maxId = 0;
            for (NhanVien nhanVienObj: nhanVienList) {
                int id = Integer.parseInt(nhanVienObj.getId());
                if(id>maxId){
                    maxId = id;
                }
            }
            String newId =String.valueOf(maxId + 1);
            NhanVien nhanVienRequest = new NhanVien(newId,strName,strDate,strGender,strSalary,phongBanId);
            Call<Void> call = NhanVienAPI.nhanVienService.createNhanVien(nhanVienRequest);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(AddNhanVienActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        date.setText("");
                        gender.setText("");
                        salary.setText("");
                        phongban.setText("");
                    } else {
                        Toast.makeText(AddNhanVienActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
}
