package com.exomatik.irfanrz.dharmonis.Model;

/**
 * Created by IrfanRZ on 29/12/2018.
 */

public class ModelProfile {
    private String nama;
    private String alamat;
    private String kontak;
    private String image;
    private String profesi;
    private boolean isExpandable;

    public ModelProfile() {
    }

    public ModelProfile(String nama, String alamat, String kontak, String image, String profesi, boolean isExpandable) {
        this.nama = nama;
        this.alamat = alamat;
        this.kontak = kontak;
        this.image = image;
        this.profesi = profesi;
        this.isExpandable = isExpandable;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProfesi() {
        return profesi;
    }

    public void setProfesi(String profesi) {
        this.profesi = profesi;
    }
}
