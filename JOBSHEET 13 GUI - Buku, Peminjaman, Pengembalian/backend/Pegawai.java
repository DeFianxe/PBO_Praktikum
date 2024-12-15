/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Putra
 */
public class Pegawai {
    private int idpegawai;
    private String nama;
    private String alamat;
    private String notelp;
    private String jabatan;
    private String username;
    private String password;
    
    public Pegawai() {
        
    }

    public Pegawai(String nama, String alamat, String notelp, String jabatan, String username, String password) {
        this.nama = nama;
        this.alamat = alamat;
        this.notelp = notelp;
        this.jabatan = jabatan;
        this.username = username;
        this.password = password;
    }
    
       

    public int getIdpegawai() {
        return idpegawai;
    }

    public void setIdpegawai(int idpegawai) {
        this.idpegawai = idpegawai;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return nama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    
    public Pegawai getById(int id) {
        Pegawai ang = new Pegawai();
        ResultSet rs = (ResultSet) DBHelper.selectQuery("SELECT * FROM pegawai " + "WHERE idpegawai = '" + id + "'");

        try {
            while (rs.next()) {
                ang = new Pegawai();
                ang.setIdpegawai(rs.getInt("idpegawai"));
                ang.setNama(rs.getString("nama"));
            }
        } catch (Exception e) {
            e.printStackTrace();
    }
        return ang;
    }
    
    public ArrayList<Pegawai> getAll() {
        ArrayList<Pegawai> ListPegawai = new ArrayList<>();
        ResultSet rs = (ResultSet) DBHelper.selectQuery("SELECT * FROM pegawai");
    
        try {
            while (rs.next()) {
                Pegawai peg = new Pegawai();
                peg.setIdpegawai(rs.getInt("idpegawai"));
                peg.setNama(rs.getString("nama"));
                ListPegawai.add(peg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListPegawai;
    }
    
    public ArrayList<Pegawai> search(String keyword) {
        ArrayList<Pegawai> ListPegawai = new ArrayList<>();
        String sql = "SELECT * FROM pegawai WHERE " +
                "nama LIKE '%" + keyword + "%' ";
        ResultSet rs = (ResultSet) DBHelper.selectQuery(sql);

        try {
            while (rs.next()) {
                Pegawai peg = new Pegawai();
                peg.setIdpegawai(rs.getInt("idpegawai"));
                peg.setNama(rs.getString("nama"));
                peg.setAlamat(rs.getString("alamat"));
                
                ListPegawai.add(peg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListPegawai;
    }
    
    public void save() {
        if (getById(idpegawai).getIdpegawai() == 0) {
            String SQL = "INSERT INTO Pegawai (nama, alamat, notelp, jabatan, username, password) VALUES('" +
                    this.nama + "', '" +
                    this.alamat + "', '" +
                    this.notelp + "', '" +
                    this.jabatan + "', '" +
                    this.username + "', '" +
                    this.password + "')";
            this.idpegawai = DBHelper.insertQueryGetId(SQL);
        } else {
            String SQL = "UPDATE pegawai SET " +
                    "nama = '" + this.nama + "', " +
                    "WHERE idpegawai = " + this.idpegawai + "";
            DBHelper.executeQuery(SQL);
        }
    }
    
    public void delete() {
        String SQL = "DELETE FROM pegawai WHERE idpegawai = '" + this.idpegawai + "'";
        DBHelper.executeQuery(SQL);
    }
}
