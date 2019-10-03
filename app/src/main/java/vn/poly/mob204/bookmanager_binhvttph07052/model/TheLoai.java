package vn.poly.mob204.bookmanager_binhvttph07052.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class TheLoai implements Serializable {
    private String maTheLoai;
    private String tenTheLoai;
    private String moTa;
    private int viTri;

    public TheLoai() {
    }

    public TheLoai(String maTheLoai, String tenTheLoai, String moTa, int viTri) {
        this.maTheLoai = maTheLoai;
        this.tenTheLoai = tenTheLoai;
        this.moTa = moTa;
        this.viTri = viTri;
    }

    public String getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getViTri() {
        return viTri;
    }

    public void setViTri(int viTri) {
        this.viTri = viTri;
    }

    @NonNull
    @Override
    public String toString() {
        return getMaTheLoai() + " | " + getTenTheLoai();
    }
}
