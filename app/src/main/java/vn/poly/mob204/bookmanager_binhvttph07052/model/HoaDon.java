package vn.poly.mob204.bookmanager_binhvttph07052.model;

import java.io.Serializable;
import java.util.Date;

public class HoaDon implements Serializable {
    private int maHoaDon;
    private Date ngayMua;

    public HoaDon() {
    }

    public HoaDon(int maHoaDon, Date ngayMua) {
        this.maHoaDon = maHoaDon;
        this.ngayMua = ngayMua;
    }

    public HoaDon(Date ngayMua) {
        this.ngayMua = ngayMua;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public Date getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(Date ngayMua) {
        this.ngayMua = ngayMua;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHoaDon=" + maHoaDon +
                ", ngayMua=" + ngayMua +
                '}';
    }
}
