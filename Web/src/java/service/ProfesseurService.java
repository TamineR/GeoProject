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


import beans.Professeur;
import connexion.Connexion;
import dao.IDao;

/**
 *
 * @author maro
 */
public class ProfesseurService implements IDao<Professeur> {

    @Override
    public boolean create(Professeur o) {
        String sql = "insert into professeur values (null, ?, ?, ?, ?, ?, ?,?)";
        try {
                PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
                ps.setString(1, o.getNom());
                ps.setString(2, o.getPrenom());
                ps.setString(3, o.getPhone());
                ps.setString(4, o.getEmail());
                ps.setDate(5, new Date(o.getDateEmb().getTime()));
                ps.setString(6, o.getSexe());
                ps.setString(7, o.getSpecialite());
                if (ps.executeUpdate() == 1) {
                    return true;
                } 
        } catch (SQLException e) {
                System.out.println("create : erreur sql : " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Professeur o) {
        String sql = "delete from professeur where id  = ?";
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
    public boolean update(Professeur o) {
        System.out.println("Hit");
        String sql = "update professeur set nom  = ? ,prenom = ? , phone = ? , email = ?,  dateEmb = ? , sexe = ? , specialite = ? where id  = ?";
        try {
                PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
                ps.setString(1, o.getNom());
                ps.setString(2, o.getPrenom());
                ps.setString(3, o.getPhone());
                ps.setString(4, o.getEmail());
                ps.setDate(5, new Date(o.getDateEmb().getTime()));
                ps.setString(6, o.getSexe());
                ps.setString(7, o.getSpecialite());
                ps.setInt(8, o.getId());
                if (ps.executeUpdate() == 1) {
                    return true;
                } 
        } catch (SQLException e) {
                System.out.println("update : erreur sql : " + e.getMessage());
        }
        return false;
    }

    @Override
    public Professeur findById(int id) {
        String sql = "select * from professeur where id  = ?";
        try {
            PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               return new Professeur(rs.getInt("id"), rs.getString("nom"), 
                                        rs.getString("prenom"),
                                        rs.getString("phone"),
                                        rs.getString("email"),
                                        rs.getDate("dateEmb"),
                                        rs.getString("sexe"),
                                        rs.getString("specialite"));
            }

        } catch (SQLException e) {
            System.out.println("findById " + e.getMessage());
        }
        return null;
    }
    
        
    public List<Professeur> findAllByNom(String Nom) {
        List<Professeur> profs = new ArrayList<Professeur>();

        String sql = "select * from professeur where nom  = ?";
        try {
            PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
            ps.setString(1, Nom);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                profs.add(new Professeur(rs.getInt("id"), rs.getString("nom"), 
                                rs.getString("prenom"),
                                rs.getString("phone"),
                                rs.getString("email"),
                                rs.getDate("dateEmb"),
                                rs.getString("sexe"),
                                rs.getString("specialite")));

        } catch (SQLException e) {
                System.out.println("findAll " + e.getMessage());
        }
        return profs;
    }
    
        public List<Professeur> findAllBySpecialite(String spe) {
        List<Professeur> profs = new ArrayList<Professeur>();

        String sql = "select * from professeur where specialite  = ?";
        try {
            PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
            ps.setString(1, spe);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                profs.add(new Professeur(rs.getInt("id"), rs.getString("nom"), 
                                rs.getString("prenom"),
                                rs.getString("phone"),
                                rs.getString("email"),
                                rs.getDate("dateEmb"),
                                rs.getString("sexe"),
                                rs.getString("specialite")));

        } catch (SQLException e) {
                System.out.println("findAll " + e.getMessage());
        }
        return profs;
    }
    
    public List<Professeur> findBetweenTwoDates(Professeur o1,Professeur o2) {
        List<Professeur> profs = new ArrayList<Professeur>();

        String sql = "select * from professeur where dateEmb Between ? and ?";
        try {
            PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
            ps.setDate(1, new Date(o1.getDateEmb().getTime()));
            ps.setDate(2, new Date(o2.getDateEmb().getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                profs.add(new Professeur(rs.getInt("id"), rs.getString("nom"), 
                                rs.getString("prenom"),
                                rs.getString("phone"),
                                rs.getString("email"),
                                rs.getDate("dateEmb"),
                                rs.getString("sexe"),
                                rs.getString("specialite")));

        } catch (SQLException e) {
                System.out.println("findAll " + e.getMessage());
        }
        return profs;
    }
    
    @Override
    public List<Professeur> findAll() {
        List<Professeur> profs = new ArrayList<Professeur>();

        String sql = "select * from professeur";
        try {
            PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);;
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                profs.add(new Professeur(rs.getInt("id"), rs.getString("nom"), 
                                rs.getString("prenom"),
                                rs.getString("phone"),
                                rs.getString("email"),
                                rs.getDate("dateEmb"),
                                rs.getString("sexe"),
                                rs.getString("specialite")));

        } catch (SQLException e) {
                System.out.println("findAll " + e.getMessage());
        }
        return profs;
    }
    
    public List<Professeur> findAllSpecialite(String spe) {
        List<Professeur> profs = new ArrayList<Professeur>();

        String sql = "select * from professeur where specialite = ?";
        try {
            PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);;
            ps.setString(1, spe);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                profs.add(new Professeur(rs.getInt("id"), rs.getString("nom"), 
                                rs.getString("prenom"),
                                rs.getString("phone"),
                                rs.getString("email"),
                                rs.getDate("dateEmb"),
                                rs.getString("sexe"),
                                rs.getString("specialite")));

        } catch (SQLException e) {
                System.out.println("findAll " + e.getMessage());
        }
        return profs;
    }
    
    
    public ResultSet findBySexe() {
        List<Professeur> profs = new ArrayList<Professeur>();
        String sql = "select sexe,count(*) as nbr from professeur group by sexe";
        try {
            PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
                System.out.println("findAll " + e.getMessage());
        }
        return null;
    }
    public ResultSet findBySpecialite() {
        List<Professeur> profs = new ArrayList<Professeur>();
        String sql = "select specialite,count(*) as nbr from professeur group by specialite";
        try {
            PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
                System.out.println("findAll " + e.getMessage());
        }
        return null;
    }
    
    
    
    public boolean findSpecialite(String spe) {
        String sql = "select * from professeur where specialite = ?";
        try {
            PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
            ps.setString(1, spe);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               return true;
            }
        } catch (SQLException e) {
                System.out.println("findAll " + e.getMessage());
        }
        return false;
    }
    
}
