package com.elektroshock.ades.ades.Activity.Util;

public class Konsumen {
    String pembeli_id;
    String pembeli_nama;
    String pembeli_ttl;
    String pembeli_kontak;
    String pembeli_alamat;
    String pembeli_type;
    String pembeli_warna;
    String pembeli_mesin;

    public Konsumen(String pembeli_id, String pembeli_nama, String pembeli_ttl, String pembeli_kontak, String pembeli_alamat, String pembeli_type, String pembeli_warna, String pembeli_mesin) {
        this.pembeli_id = pembeli_id;
        this.pembeli_nama = pembeli_nama;
        this.pembeli_ttl = pembeli_ttl;
        this.pembeli_kontak = pembeli_kontak;
        this.pembeli_alamat = pembeli_alamat;
        this.pembeli_type = pembeli_type;
        this.pembeli_warna = pembeli_warna;
        this.pembeli_mesin = pembeli_mesin;
    }

    public Konsumen() {

    }

    public String getPembeli_id() {
        return pembeli_id;
    }

    public void setPembeli_id(String pembeli_id) {
        this.pembeli_id = pembeli_id;
    }

    public String getPembeli_nama() {
        return pembeli_nama;
    }

    public void setPembeli_nama(String pembeli_nama) {
        this.pembeli_nama = pembeli_nama;
    }

    public String getPembeli_ttl() {
        return pembeli_ttl;
    }

    public void setPembeli_ttl(String pembeli_ttl) {
        this.pembeli_ttl = pembeli_ttl;
    }

    public String getPembeli_kontak() {
        return pembeli_kontak;
    }

    public void setPembeli_kontak(String pembeli_kontak) {
        this.pembeli_kontak = pembeli_kontak;
    }

    public String getPembeli_alamat() {
        return pembeli_alamat;
    }

    public void setPembeli_alamat(String pembeli_alamat) {
        this.pembeli_alamat = pembeli_alamat;
    }

    public String getPembeli_type() {
        return pembeli_type;
    }

    public void setPembeli_type(String pembeli_type) {
        this.pembeli_type = pembeli_type;
    }

    public String getPembeli_warna() {
        return pembeli_warna;
    }

    public void setPembeli_warna(String pembeli_warna) {
        this.pembeli_warna = pembeli_warna;
    }

    public String getPembeli_mesin() {
        return pembeli_mesin;
    }

    public void setPembeli_mesin(String pembeli_mesin) {
        this.pembeli_mesin = pembeli_mesin;
    }
}
