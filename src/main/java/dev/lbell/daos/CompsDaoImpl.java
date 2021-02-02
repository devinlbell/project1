package dev.lbell.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dev.lbell.models.Comps;
import dev.lbell.models.User;
import dev.lbell.util.ConnectionUtil;

public class CompsDaoImpl implements CompsDao {

	@Override
	public List<Comps> getUserComps(int userId) {
		String sql = "SELECT * FROM COMPS WHERE USER_ID = ?";
		List<Comps> comps = new ArrayList<>();
		try(Connection connection = ConnectionUtil.getConnection();
				PreparedStatement pStatement = connection.prepareStatement(sql);) {
			pStatement.setInt(1, userId);
			ResultSet rs = pStatement.executeQuery();
			//log.info(interaction);
			while(rs.next()) {
				Comps comp = new Comps(rs.getInt("COMP_ID"), rs.getInt("AMOUNT"), rs.getInt("USER_ID"), rs.getInt("MANAGER_ID"), rs.getString("DESCRIPTION"), rs.getString("STATUS"));
				comps.add(comp);
			} 
			return comps;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Comps> getAllComps() {
		String sql = "SELECT * FROM COMPS";
		List<Comps> comps = new ArrayList<>();
		try(Connection connection = ConnectionUtil.getConnection();
				Statement statement = connection.createStatement();) {
			ResultSet rs = statement.executeQuery(sql);
			//log.info(interaction);
			while(rs.next()) {
				Comps comp = new Comps(rs.getInt("COMP_ID"), rs.getInt("AMOUNT"), rs.getInt("USER_ID"), rs.getInt("MANAGER_ID"), rs.getString("DESCRIPTION"), rs.getString("STATUS"));
				comps.add(comp);
			} 
			return comps;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addComp(Comps comp) {
		String sql = "INSERT INTO COMPS (AMOUNT, DESCRIPTION, STATUS, USER_ID) VALUES (?, ?, ?, ?)";
		try(Connection connection = ConnectionUtil.getConnection();
				PreparedStatement pStatement = connection.prepareStatement(sql);) {
			if(comp!=null) {
				pStatement.setInt(1, comp.getAmount());
				pStatement.setString(2, comp.getDescription());
				pStatement.setString(3, comp.getStatus());
				pStatement.setInt(4, comp.getUserId());
				ResultSet rs = pStatement.executeQuery();
				return true;
			}
			return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean changeComp(Comps comp) {
		String sql = "UPDATE COMPS SET STATUS = ?, MANAGER_ID = ? WHERE COMP_ID = ?";
		System.out.println("currently updating" + comp);
		try(Connection connection = ConnectionUtil.getConnection();
				PreparedStatement pStatement = connection.prepareStatement(sql);) {
			if(comp!=null && comp.getId() >= 1 && comp.getManagerId() >= 1) {
				pStatement.setString(1, comp.getStatus());
				pStatement.setInt(2, comp.getManagerId());
				pStatement.setInt(3, comp.getId());
				ResultSet rs = pStatement.executeQuery();
				
				return true;
			}
			return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Comps getCompById(int id) {
		String sql = "SELECT * FROM COMPS WHERE COMP_ID = ?";
		try(Connection connection = ConnectionUtil.getConnection();
				PreparedStatement pStatement = connection.prepareStatement(sql);) {
			pStatement.setInt(1, id);
			ResultSet rs = pStatement.executeQuery();
			//log.info(interaction);
			if(rs.next()) {
				Comps comp = new Comps(rs.getInt("COMP_ID"), rs.getInt("AMOUNT"), rs.getInt("USER_ID"), rs.getInt("MANAGER_ID"), rs.getString("DESCRIPTION"), rs.getString("STATUS"));
				return comp;
			} 
			return null;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
