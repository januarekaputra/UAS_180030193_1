package com.bh183.januarekaputra;

import java.sql.Time;
import java.util.Date;

public class Film {

    private int idFilm;
    private String judul;
    private Date tanggal;
    private String gambar;
    private String genre;
    private String sutradara;
    private String pemeran;
    private String sinopsis;

    public Film(int idFilm, String judul, Date tanggal, String gambar, String genre, String sutradara, String pemeran, String sinopsis) {
        this.idFilm = idFilm;
        this.judul = judul;
        this.tanggal = tanggal;
        this.gambar = gambar;
        this.genre = genre;
        this.sutradara = sutradara;
        this.pemeran = pemeran;
        this.sinopsis = sinopsis;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Date getTanggal() { return tanggal; }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) { this.gambar = gambar; }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSutradara() {
        return sutradara;
    }

    public void setSutradara(String sutradara) {
        this.sutradara = sutradara;
    }

    public String getPemeran() {
        return pemeran;
    }

    public void setPemeran(String pemeran) {
        this.pemeran = pemeran;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
}
