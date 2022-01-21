package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Machine;
import beans.Produit;
import beans.User;
import connexion.Connexion;
import dao.IDao;

public class UserService implements IDao<User>{

	@Override
	public void create(User o) {
		String sql = "insert into user values(null,?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstance().getCn().prepareStatement(sql);
			ps.setString(1, o.getFirsname());
			ps.setString(2, o.getLastname());
			ps.setInt(3, o.getAge());
			ps.setString(4, o.getSex());
			ps.setString(5, o.getAddr());
			ps.executeUpdate();
			
		}catch(SQLException e) {
			System.out.println("SQL Error.");
		}
	}

	@Override
	public void update(User o) {
		String sql = "update user set firsname = ?, lastname = ?, age = ?, sex = ?, addr = ? where id = ?";
		PreparedStatement ps = null;
		try {
			ps = Connexion.getInstance().getCn().prepareStatement(sql);
			ps.setString(1, o.getFirsname());
			ps.setString(2, o.getLastname());
			ps.setInt(3, o.getAge());
			ps.setString(4, o.getSex());
			ps.setString(5, o.getAddr());
			ps.setInt(6, o.getId());
			ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println("SQL Error." + e);
		}		
		
	}

	@Override
	public void delete(User o) {
		String sql = "delete from user where id = ?";
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
	public User findById(int id) {
		String sql = "select * from user where id = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		User u = null;
		try {
			ps = Connexion.getInstance().getCn().prepareStatement(sql);
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

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = Connexion.getInstance().getCn().prepareStatement(sql);
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
	
	
	public ResultSet findUserBySex() {
        String sql = "select sex, count(*) as nbrS  from user GROUP BY sex";
        try {
            PreparedStatement ps = Connexion.getInstance().getCn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();      
            return rs;
        } catch (SQLException e) {
            System.out.println("findAll " + e.getMessage());
        }
        return null;
    }
	
	public ResultSet findUserByAge() {
        String sql = "select age, count(*) as nbrA  from user GROUP BY age";
        try {
            PreparedStatement ps = Connexion.getInstance().getCn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();      
            return rs;
        } catch (SQLException e) {
            System.out.println("findAll " + e.getMessage());
        }
        return null;
    }
	
}
