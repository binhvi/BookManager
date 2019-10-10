package vn.poly.mob204.bookmanager_binhvttph07052.model;

import java.io.Serializable;

public class HoaDonChiTiet implements Serializable {
    private int maHDCT;
    private HoaDon hoaDon;
    private Sach sach;
    private int soLuongMua;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(int maHDCT, HoaDon hoaDon, Sach sach, int soLuongMua) {
        this.maHDCT = maHDCT;
        this.hoaDon = hoaDon;
        this.sach = sach;
        this.soLuongMua = soLuongMua;
    }

    public HoaDonChiTiet(HoaDon hoaDon, Sach sach, int soLuongMua) {
        this.hoaDon = hoaDon;
        this.sach = sach;
        this.soLuongMua = soLuongMua;
    }

    public HoaDonChiTiet(Sach sach, int soLuongMua) {
        this.sach = sach;
        this.soLuongMua = soLuongMua;
    }

    public int getMaHDCT() {
        return maHDCT;
    }

    public void setMaHDCT(int maHDCT) {
        this.maHDCT = maHDCT;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public Sach getSach() {
        return sach;
    }

    public void setSach(Sach sach) {
        this.sach = sach;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    @Override
    public String toString() {
        return "HoaDonChiTiet{" +
                "maHDCT=" + maHDCT +
                ", hoaDon=" + hoaDon +
                ", sach=" + sach +
                ", soLuongMua=" + soLuongMua +
                '}';
    }
}
