/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import beans.Specialite;
import connexion.Connexion;
import dao.IDao;
/**
 *
 * @author maro
 */
public class SpecialiteService implements IDao<Specialite>{

    @Override
    public boolean create(Specialite o) {
        String sql = "insert into specialite values (null,?, ?)";
        try {
                PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
                ps.setString(1, o.getCode());
                ps.setString(2, o.getLibelle());
                if (ps.executeUpdate() == 1) {
                    return true;
                } 
        } catch (SQLException e) {
                System.out.println("create : erreur sql : " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Specialite o) {
        String sql = "delete from specialite where id  = ?";
        try {
                PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
                ps.setInt(1, o.getId());
                if (ps.executeUpdate() == 1) {
                    return true;
                } 
        } catch (SQLException e) {
                System.out.println("delete : erreur sql : " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Specialite o) {
        String sql = "update specialite set code  = ? ,libelle = ? where id  = ?";
         try {
                PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
                ps.setString(1, o.getCode());
                ps.setString(2, o.getLibelle());
                ps.setInt(3, o.getId());
                if (ps.executeUpdate() == 1) {
                    return true;
                } 
        } catch (SQLException e) {
                System.out.println("update : erreur sql : " + e.getMessage());
        }
        return false;
    }

    @Override
    public Specialite findById(int id) {
        String sql = "select * from specialite where id  = ?";
        try {
            PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               return new Specialite(rs.getInt("id"), rs.getString("code"), 
                                        rs.getString("libelle"));
            }

        } catch (SQLException e) {
            System.out.println("findById " + e.getMessage());
        }
        return null;
    }
    
    public boolean findByLible(String lib) {
        String sql = "select * from specialite where libelle  = ?";
        try {
            PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
            ps.setString(1, lib);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               return true;
            }

        } catch (SQLException e) {
            System.out.println("findById " + e.getMessage());
        }
        return false;
    }
    
    public List<Specialite> findByLibelle(String Lib) {
        String sql = "select * from specialite where libelle = ?";
        List<Specialite> specs = new ArrayList<Specialite>();
        try {
            PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
            ps.setString(1, Lib);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                specs.add(new Specialite(rs.getInt("id"), rs.getString("code"), 
                                        rs.getString("libelle")));
        } catch (SQLException e) {
            System.out.println("findById " + e.getMessage());
        }
        return specs;
    }
    
    

    @Override
    public List<Specialite> findAll() {
        List<Specialite> specs = new ArrayList<Specialite>();

        String sql = "select * from specialite";
        try {
            PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);;
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                specs.add(new Specialite(rs.getInt("id"), rs.getString("code"), 
                                        rs.getString("libelle")));
        } catch (SQLException e) {
                System.out.println("findAll " + e.getMessage());
        }
        return specs;
    }
    
}
