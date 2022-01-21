package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.User;
import beans.phone;
import connexion.Connexion;
import dao.IDao;

public class PhoneService implements IDao<phone>{

	@Override
	public void create(phone o) {
		String sql = "insert into phone values(null,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstance().getCn().prepareStatement(sql);
			ps.setString(1, o.getProvider());
			ps.setString(2, o.getNumber());
			ps.setInt(3, o.getUserId());
			ps.setString(4, o.getIsAdmin());
			ps.executeUpdate();
			
		}catch(SQLException e) {
			System.out.println("SQL Error.");
		}			
	}

	@Override
	public void update(phone o) {
		String sql = "update phone set provider = ?, number = ?, userid = ?, isadmin = ?";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstance().getCn().prepareStatement(sql);
			ps.setString(1, o.getProvider());
			ps.setString(2, o.getNumber());
			ps.setInt(3, o.getUserId());
			ps.setString(4, o.getIsAdmin());
			ps.executeUpdate();;
		}catch(SQLException e) {
			System.out.println("SQL Error.");
		}	
	}

	@Override
	public void delete(phone o) {
		String sql = "delete from phone where id = ?";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstance().getCn().prepareStatement(sql);
			ps.setInt(1, o.getId());
			ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println("SQL Error.");
		}		
	}

	@Override
	public phone findById(int id) {
		String sql = "select * from phone where id = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		phone u = null;
		try {
			ps = Connexion.getInstance().getCn().prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				u = new phone(rs.getInt("id"),rs.getString("provider"),
							rs.getString("number"), rs.getInt("userid"), rs.getString("isadmin"));	
			}
		}catch(SQLException e) {
			System.out.println("SQL Error.");
		}		
		return u;
	}

	@Override
	public List<phone> findAll() {
		List<phone> phs = new ArrayList<phone>();
		String sql = "select * from phone";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Connexion.getInstance().getCn().prepareStatement(sql);
			rs = ps.executeQuery(sql);
			while (rs.next()) {
				phs.add(new phone(rs.getInt("id"),rs.getString("provider"),
						rs.getString("number"), rs.getInt("userid"), rs.getString("isadmin")));
			}

		} catch (SQLException e) {
			System.out.println("Erreur SQL");
		}
		return phs;
	}

}
