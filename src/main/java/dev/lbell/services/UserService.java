package dev.lbell.services;


import dev.lbell.daos.UsersDao;
import dev.lbell.daos.UsersDaoImpl;
import dev.lbell.models.Credentials;
import dev.lbell.models.User;

public class UserService {
	private UsersDao uDao = new UsersDaoImpl();
	
	public User findUser(Credentials creds) {
		return uDao.getUser(creds.getUsername(), creds.getPassword());
		
	}
	
}
