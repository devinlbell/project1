package dev.lbell.daos;

import java.util.List;

import dev.lbell.models.User;


public interface UsersDao {
	public User getUser(String username, String password);
	public boolean addUser(User newUser);
	public List<User> getUsers();
}
