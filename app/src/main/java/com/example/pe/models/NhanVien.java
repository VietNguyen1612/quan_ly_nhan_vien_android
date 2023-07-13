package com.example.pe.models;

import java.io.Serializable;

public class NhanVien implements Serializable {
    private String id;
    private String name;
    private String date;
    private String gender;
    private String salary;
    private String idPB;
    public NhanVien(String id, String name, String date, String gender, String salary, String idPB) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.gender = gender;
        this.salary = salary;
        this.idPB = idPB;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getIdPB() {
        return idPB;
    }

    public void setIdPB(String idPB) {
        this.idPB = idPB;
    }
}
