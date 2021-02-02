package dev.lbell.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dev.lbell.models.Credentials;
import dev.lbell.models.User;
import dev.lbell.services.UserService;


@Path("/users")
public class UsersController {
	private UserService uServs =  new UserService();
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginUser(Credentials creds) {
		System.out.println("Hello post servlet");
		System.out.println("username: " + creds.getUsername());
		List<User> users = new ArrayList<>();
		users.add(new User(1, "jdean", "password", "jimmy", "employee"));
		users.add(new User(2, "slimJ", "passw0rd", "slimJim", "manager"));
		users.add(new User(3, "jRappa", "p4ssw0rd", "TommyHoffa", "employee"));
		User user = uServs.findUser(creds);
		if(user != null) {
			System.out.println("We found that user");
			return Response.status(200).entity(user).build();
		} else {
			System.out.println("Sorry for you!");
			return Response.status(401).build();
		}
		
		//return Response.status(200).build();
	}
	
	
}
