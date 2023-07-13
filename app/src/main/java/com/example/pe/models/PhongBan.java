package com.example.pe.models;

public class PhongBan {
    private String idPB;
    private String namePB;

    public PhongBan(String idPB, String namePB) {
        this.idPB = idPB;
        this.namePB = namePB;
    }

    public String getIdPB() {
        return idPB;
    }

    public void setIdPB(String idPB) {
        this.idPB = idPB;
    }

    public String getNamePB() {
        return namePB;
    }

    public void setNamePB(String namePB) {
        this.namePB = namePB;
    }
}
