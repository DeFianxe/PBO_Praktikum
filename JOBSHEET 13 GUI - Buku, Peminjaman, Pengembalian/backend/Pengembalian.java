/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 * @author Putra
 */
public class Pengembalian {
    private int idPengembalian;
    private Peminjaman peminjaman;
    private String tanggalPengembalian;

    public Pengembalian() {}

    public Pengembalian(Peminjaman peminjaman, String tanggalPengembalian) {
        this.peminjaman = peminjaman;
        this.tanggalPengembalian = tanggalPengembalian;
    }

    public int getIdPengembalian() {
        return idPengembalian;
    }

    public void setIdPengembalian(int idPengembalian) {
        this.idPengembalian = idPengembalian;
    }

    public Peminjaman getPeminjaman() {
        return peminjaman;
    }

    public void setPeminjaman(Peminjaman peminjaman) {
        this.peminjaman = peminjaman;
    }

    public String getTanggalPengembalian() {
        return tanggalPengembalian;
    }

    public void setTanggalPengembalian(String tanggalPengembalian) {
        this.tanggalPengembalian = tanggalPengembalian;
    }

    public void save() {
        String query = "INSERT INTO pengembalian (idpeminjaman, tanggalpengembalian) VALUES ("
                + this.getPeminjaman().getIdPeminjaman() + ", '"
                + this.getTanggalPengembalian() + "')";
        DBHelper.insertQueryGetId(query);

        DBHelper.executeQuery("UPDATE peminjaman SET status = 1 WHERE idpeminjaman = " + this.getPeminjaman().getIdPeminjaman());
    }

    public Pengembalian getById(int id) {
        Pengembalian pengembalian = null;
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM pengembalian WHERE idpengembalian = " + id);

        try {
            while (rs.next()) {
                pengembalian = new Pengembalian();
                pengembalian.setIdPengembalian(rs.getInt("idpengembalian"));
                pengembalian.setPeminjaman(new Peminjaman().getById(rs.getInt("idpeminjaman")));
                pengembalian.setTanggalPengembalian(rs.getString("tanggalpengembalian"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pengembalian;
    }

    public ArrayList<Pengembalian> getAll() {
        ArrayList<Pengembalian> list = new ArrayList<>();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM pengembalian");

        try {
            while (rs.next()) {
                Pengembalian pengembalian = new Pengembalian();
                pengembalian.setIdPengembalian(rs.getInt("idpengembalian"));
                pengembalian.setPeminjaman(new Peminjaman().getById(rs.getInt("idpeminjaman")));
                pengembalian.setTanggalPengembalian(rs.getString("tanggalpengembalian"));

                list.add(pengembalian);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean getStatus() {
        return this.getPeminjaman().getStatus();
    }
}
