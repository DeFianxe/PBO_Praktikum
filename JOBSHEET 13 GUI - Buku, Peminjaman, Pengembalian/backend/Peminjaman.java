/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.util.ArrayList;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;

/**
 *
 * @author Putra
 */
public class Peminjaman {
    private int idPeminjaman;
    private Anggota anggota;
    private Buku buku;
    private String tanggalPinjam;
    private String tanggalKembali;
    private boolean status; // true = dikembalikan, false = belum dikembalikan

    public Peminjaman() {}

    public Peminjaman(Anggota anggota, Buku buku, String tanggalPinjam, String tanggalKembali, boolean status) {
        this.anggota = anggota;
        this.buku = buku;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
        this.status = status;
    }

    public int getIdPeminjaman() {
        return idPeminjaman;
    }

    public void setIdPeminjaman(int idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public Anggota getAnggota() {
        return anggota;
    }

    public void setAnggota(Anggota anggota) {
        this.anggota = anggota;
    }

    public Buku getBuku() {
        return buku;
    }

    public void setBuku(Buku buku) {
        this.buku = buku;
    }

    public String getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(String tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public String getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(String tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Peminjaman getById(int id) {
        Peminjaman peminjaman = null;
        ResultSet rs = (ResultSet) DBHelper.selectQuery("SELECT * FROM peminjaman WHERE idpeminjaman = " + id);

        try {
            if (rs.next()) {
                peminjaman = new Peminjaman();
                peminjaman.setIdPeminjaman(rs.getInt("idpeminjaman"));
                peminjaman.setAnggota(new Anggota().getById(rs.getInt("idanggota")));
                peminjaman.setBuku(new Buku().getById(rs.getInt("idbuku")));
                peminjaman.setTanggalPinjam(rs.getString("tanggalpinjam"));
                peminjaman.setTanggalKembali(rs.getString("tanggalkembali"));
                peminjaman.setStatus(rs.getBoolean("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return peminjaman; 
    }

    public ArrayList<Peminjaman> getAll() {
        ArrayList<Peminjaman> listPeminjaman = new ArrayList<>();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM peminjaman");

        try {
            while (rs.next()) {
                Peminjaman peminjaman = new Peminjaman();
                peminjaman.setIdPeminjaman(rs.getInt("idpeminjaman"));
                peminjaman.setAnggota(new Anggota().getById(rs.getInt("idanggota")));
                peminjaman.setBuku(new Buku().getById(rs.getInt("idbuku")));
                peminjaman.setTanggalPinjam(rs.getString("tanggalpinjam"));
                peminjaman.setTanggalKembali(rs.getString("tanggalkembali"));
                peminjaman.setStatus(rs.getBoolean("status"));

                listPeminjaman.add(peminjaman);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPeminjaman;
    }

    public void save() {
        Peminjaman existingPeminjaman = getById(idPeminjaman);

        if (existingPeminjaman == null || existingPeminjaman.idPeminjaman == 0) {
            String query = "INSERT INTO peminjaman (idanggota, idbuku, tanggalpinjam, tanggalkembali, status) VALUES ("
                    + this.getAnggota().getIdanggota() + ", "
                    + this.getBuku().getIdbuku() + ", '"
                    + this.getTanggalPinjam() + "', '"
                    + this.getTanggalKembali() + "', "
                    + (this.getStatus() ? 1 : 0) + ")";
            this.idPeminjaman = DBHelper.insertQueryGetId(query);
        } else {
            String query = "UPDATE peminjaman SET idanggota = " + this.getAnggota().getIdanggota()
                    + ", idbuku = " + this.getBuku().getIdbuku()
                    + ", tanggalpinjam = '" + this.getTanggalPinjam()
                    + "', tanggalkembali = '" + this.getTanggalKembali()
                    + "', status = " + (this.getStatus() ? 1 : 0)
                    + " WHERE idpeminjaman = " + this.idPeminjaman;
            DBHelper.executeQuery(query);
        }
    }
    
    public ArrayList<Peminjaman> search(String keyword) {
        ArrayList<Peminjaman> listPeminjaman = new ArrayList<>();
        String sql = "SELECT * FROM peminjaman p " +
                "JOIN anggota a ON p.idanggota = a.idanggota " +
                "JOIN buku b ON p.idbuku = b.idbuku " +
                "WHERE a.nama LIKE '%" + keyword + "%' " +
                "OR b.judul LIKE '%" + keyword + "%' " +
                "OR p.tanggalpinjam LIKE '%" + keyword + "%' " +
                "OR p.tanggalkembali LIKE '%" + keyword + "%'";

        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            while (rs.next()) {
                Peminjaman peminjaman = new Peminjaman();
                peminjaman.setIdPeminjaman(rs.getInt("idpeminjaman"));
                peminjaman.setAnggota(new Anggota().getById(rs.getInt("idanggota")));
                peminjaman.setBuku(new Buku().getById(rs.getInt("idbuku")));
                peminjaman.setTanggalPinjam(rs.getString("tanggalpinjam"));
                peminjaman.setTanggalKembali(rs.getString("tanggalkembali"));
                peminjaman.setStatus(rs.getBoolean("status"));

                listPeminjaman.add(peminjaman);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPeminjaman;
    }    

    public void delete() {
        String query = "DELETE FROM peminjaman WHERE idpeminjaman = " + this.idPeminjaman;
        DBHelper.executeQuery(query);
    }
    
}
