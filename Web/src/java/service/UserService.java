/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import beans.User;
import connexion.Connexion;
import dao.IDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import connexion.Connexion;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author maro
 */
public class UserService implements IDao<User> {

    public boolean create(User o) {
        String sql = "insert into user values(null,?,?,?,?,?)";
        try {
            PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
            ps.setString(1, o.getFirsname());
            ps.setString(2, o.getLastname());
            ps.setInt(3, o.getAge());
            ps.setString(4, o.getSex());
            ps.setString(5, o.getAddr());
            if (ps.executeUpdate() == 1) {
                    return true;
            } 

        }catch(SQLException e) {
                System.out.println("SQL Error.");
        }
        return false;
    }

    @Override
    public boolean delete(User o) {
        String sql = "delete from user where id = ?";
        try {
                PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
                ps.setInt(1, o.getId());
                if (ps.executeUpdate() == 1) {
                    return true;
                } 
        }catch(SQLException e) {
                System.out.println("SQL Error.");
        }
        return false;
    }

    @Override
    public boolean update(User o) {
        System.out.println("Are we really Here Update");
        System.out.println(o);
        String sql = "update user set firsname = ?, lastname = ?, age = ?, sex = ?, addr = ? where id = ?";
        try {
                PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
                ps.setString(1, o.getFirsname());
                ps.setString(2, o.getLastname());
                ps.setInt(3, o.getAge());
                ps.setString(4, o.getSex());
                ps.setString(5, o.getAddr());
                ps.setInt(6, o.getId());
                if (ps.executeUpdate() == 1) {
                    return true;
                } 
        }catch(SQLException e) {
                System.out.println("SQL Error." + e);
        }
        System.out.println("Are we really Here false");
        return false;
    }

    @Override
    public User findById(int id) {
        String sql = "select * from user where id = ?";
        ResultSet rs = null;
        User u = null;
        try {
                PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
                        u = new User(rs.getInt("id"),rs.getString("firsname"),
                                                rs.getString("lastname"), rs.getInt("age"), rs.getString("sex"),rs.getString("addr"));	
                }
        }catch(SQLException e) {
                System.out.println("SQL Error.");
        }		
        return u;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<User>();
        String sql = "select * from user";

        ResultSet rs = null;

        try {
                PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
                rs = ps.executeQuery(sql);
                while (rs.next()) {
                        users.add(new User(rs.getInt("id"),rs.getString("firsname"),
                                        rs.getString("lastname"), rs.getInt("age"), rs.getString("sex"),rs.getString("addr")));
                }

        } catch (SQLException e) {
                System.out.println("Erreur SQL");
        }
        return users;
    }
    
    public List<User> findAllByNom(String Nom) {
        List<User> profs = new ArrayList<User>();

        String sql = "select * from user where firsname  = ?";
        try {
            PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
            ps.setString(1, Nom);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                profs.add(new User(rs.getInt("id"), rs.getString("firstname"), 
                                rs.getString("lastname"),
                                Integer.parseInt(rs.getString("age")),
                                rs.getString("sex"),
                                rs.getString("addr")
                ));

        } catch (SQLException e) {
                System.out.println("findAllByNom " + e.getMessage());
        }
        return profs;
    }
    
    
    public ResultSet findUserBySex() {
        String sql = "select sex, count(*) as nbrS  from user GROUP BY sex";
        List<User> specs = new ArrayList<User>();
        try {
            PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();      
            return rs;
        } catch (SQLException e) {
            System.out.println("findAll " + e.getMessage());
        }
        return null;
    }
}
