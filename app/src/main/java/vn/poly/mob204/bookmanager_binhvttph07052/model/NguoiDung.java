package vn.poly.mob204.bookmanager_binhvttph07052.model;

import java.io.Serializable;

public class NguoiDung implements Serializable {
    private String userName;
    private String password;
    private String phone;
    private String hoTen;

    public NguoiDung() {
    }

    public NguoiDung(String userName, String password, String phone, String hoTen) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.hoTen = hoTen;
    }

    public NguoiDung(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    @Override
    public String toString() {
        return "NguoiDung{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", hoTen='" + hoTen + '\'' +
                '}';
    }
}
