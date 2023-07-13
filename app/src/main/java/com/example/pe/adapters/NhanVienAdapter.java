package com.example.pe.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pe.AddNhanVienActivity;
import com.example.pe.R;
import com.example.pe.listener.IOnClickNhanVienListener;
import com.example.pe.models.NhanVien;
import com.example.pe.models.PhongBan;

import java.util.List;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.NhanVienAdapterViewHolder> {
    Context context;
    private final List<PhongBan> phongBanList;
    private final List<NhanVien> nhanVienList;
    private final IOnClickNhanVienListener iOnClickNhanVienListener;

    public NhanVienAdapter(Context context, List<PhongBan> phongBanList, List<NhanVien> nhanVienList,IOnClickNhanVienListener iOnClickNhanVienListener) {
        this.context = context;
        this.phongBanList = phongBanList;
        this.nhanVienList = nhanVienList;
        this.iOnClickNhanVienListener = iOnClickNhanVienListener;
    }

    @NonNull
    @Override
    public NhanVienAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NhanVienAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.item_nhanvien,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienAdapterViewHolder holder, int position) {
        NhanVien nhanVien = nhanVienList.get(position);
        String phongBanName = "";
        for (PhongBan phongBan: phongBanList) {
            if(nhanVien.getIdPB().equals(phongBan.getIdPB())){
                phongBanName = phongBan.getNamePB();
                break;
            }
        }

        holder.name.setText("Tên: "+nhanVien.getName());
        holder.date.setText("Ngày sinh: "+nhanVien.getDate());
        holder.gender.setText("Giới tính: "+nhanVien.getGender());
        holder.phongban.setText("Phòng ban: "+phongBanName);
        holder.salary.setText("Lương: "+nhanVien.getSalary());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("nhanvien_object", nhanVien);
                Intent intent = new Intent(context, AddNhanVienActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClickNhanVienListener.onClickDeleteNhanVien(nhanVien);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nhanVienList.size();
    }

    public static class NhanVienAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView name, date, gender, salary, phongban;
        View view;
        Button deleteBtn;
        public NhanVienAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.layout_item);
            name = itemView.findViewById(R.id.tv_name);
            date = itemView.findViewById(R.id.tv_date);
            gender = itemView.findViewById(R.id.tv_gender);
            salary = itemView.findViewById(R.id.tv_salary);
            phongban = itemView.findViewById(R.id.tv_phongban);
            deleteBtn = itemView.findViewById(R.id.btn_deleteNhanVien);
        }
    }
}
