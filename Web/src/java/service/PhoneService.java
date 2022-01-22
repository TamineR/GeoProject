/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import beans.Phone;
import beans.User;
import connexion.Connexion;
import dao.IDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maro
 */
public class PhoneService implements IDao<Phone> {

    @Override
    public boolean create(Phone o) {
		String sql = "insert into phone values(null,?,?,?,?)";
		try {
			PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ps.setString(1, o.getProvider());
			ps.setString(2, o.getNumber());
			ps.setInt(3, o.getUserId());
			ps.setString(4, o.getIsAdmin());
			if (ps.executeUpdate() == 1) {
                            return true;
                        } 
			
		}catch(SQLException e) {
			System.out.println("SQL Error.");
		}	
                return false;
    }

    @Override
    public boolean delete(Phone o) {
		String sql = "delete from phone where id = ?";
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
    public boolean update(Phone o) {
		String sql = "update phone set provider = ?, number = ?, userid = ?, isadmin = ?";
		
		try {
                        PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ps.setString(1, o.getProvider());
			ps.setString(2, o.getNumber());
			ps.setInt(3, o.getUserId());
			ps.setString(4, o.getIsAdmin());
			if (ps.executeUpdate() == 1) {
                            return true;
                        } 
		}catch(SQLException e) {
			System.out.println("SQL Error.");
		}	
        return false;
    }

    @Override
    public Phone findById(int id) {
		String sql = "select * from phone where id = ?";
		ResultSet rs = null;
		Phone u = null;
		try {
			PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				u = new Phone(rs.getInt("id"),rs.getString("provider"),
							rs.getString("number"), rs.getInt("userid"), rs.getString("isadmin"));	
			}
		}catch(SQLException e) {
			System.out.println("SQL Error.");
		}		
		return u;
    }

    @Override
    public List<Phone> findAll() {
		List<Phone> phs = new ArrayList<Phone>();
		String sql = "select * from phone";

		ResultSet rs = null;

		try {
			PreparedStatement ps = Connexion.getInstane().getConnection().prepareStatement(sql);
			rs = ps.executeQuery(sql);
			while (rs.next()) {
				phs.add(new Phone(rs.getInt("id"),rs.getString("provider"),
						rs.getString("number"), rs.getInt("userid"), rs.getString("isadmin")));
			}

		} catch (SQLException e) {
			System.out.println("Erreur SQL");
		}
		return phs;
    }


  
    
}
