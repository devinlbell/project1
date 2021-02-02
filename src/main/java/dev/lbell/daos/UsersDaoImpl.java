package dev.lbell.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import dev.lbell.models.User;
import dev.lbell.util.ConnectionUtil;

public class UsersDaoImpl implements UsersDao {

	public User getUser(String username, String password) {
		String sql = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
		
		
		try(Connection connection = ConnectionUtil.getConnection();
				PreparedStatement pStatement = connection.prepareStatement(sql);) {
			pStatement.setString(1, username);
			pStatement.setString(2, password);
			ResultSet rs = pStatement.executeQuery();
			//log.info(interaction);
			if(rs.next()) {
				User user = new User(rs.getInt("USER_ID"), username, password, rs.getString("NAME"), rs.getString("EMPLOYEE_ROLE"));
				return user;
			} 
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	


	public boolean addUser(User newUser) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

}
