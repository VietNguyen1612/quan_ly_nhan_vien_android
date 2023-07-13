package com.example.pe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pe.R;
import com.example.pe.listener.IOnClickPhongBanListener;
import com.example.pe.models.PhongBan;

import java.util.List;

public class PhongBanAdapter extends RecyclerView.Adapter<PhongBanAdapter.PhongBanAdapterViewHolder> {
    Context context;
    private final List<PhongBan> phongBanList;
    private final IOnClickPhongBanListener iOnClickPhongBanListener;

    public PhongBanAdapter(Context context,List<PhongBan> phongBanList, IOnClickPhongBanListener iOnClickPhongBanListener) {
        this.context = context;
        this.phongBanList = phongBanList;
        this.iOnClickPhongBanListener = iOnClickPhongBanListener;
    }

    @NonNull
    @Override
    public PhongBanAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhongBanAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.item_phongban,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhongBanAdapterViewHolder holder, int position) {
        PhongBan phongBan = phongBanList.get(position);
        holder.name.setText(phongBan.getNamePB());
        holder.idPb.setText(phongBan.getIdPB());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClickPhongBanListener.onClickDeletePhongBan(phongBan);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phongBanList.size();
    }

    public static class PhongBanAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView name, idPb;
        Button deleteBtn;
        public PhongBanAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_namePB);
            idPb = itemView.findViewById(R.id.tv_pbId);
            deleteBtn = itemView.findViewById(R.id.btn_deletePhongBan);
        }
    }
}
