package dev.lbell.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dev.lbell.models.Comps;
import dev.lbell.services.CompsService;

@Path("comps")
public class CompsController {
	private CompsService cs = new CompsService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comps> getUserComps(@QueryParam("status")String status, @QueryParam("id")int userId) {
		List<Comps> comps = new ArrayList<>();
		System.out.println("getting comps");
		System.out.println("id: " + userId);
		if(status != null) {
			if(userId > 0) {
				if(status.equals("PENDING")) {
					comps = cs.findUserPendingComps(userId);
					return comps;
				} else if(!status.equals("PENDING")) {
					comps = cs.findUserResolvedComps(userId);
					return comps;
				}
			} else {
				if(status.equals("PENDING")) {
					System.out.println("finding pending");
					comps = cs.findPendingComps();
					return comps;
				} else if(!status.equals("PENDING")) {
					comps = cs.findResolvedComps();
					return comps;
				}
			}
		} else {
			return cs.findResolvedComps();
		}
		return null;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addComp(Comps comp) {
		if(cs.createComp(comp)) {
			return Response.status(201).entity(comp).build();
		} else {
			return Response.status(400).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCompById(@PathParam("id")int id) {
		Comps comp = cs.findCompById(id);
		if(comp!= null) {
			return Response.status(200).entity(comp).build();
		} else {
			return Response.status(404).build();
		}
	}
	
	@POST
	@Path("/deny")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response denyComps(List<Comps> comps) {
		if(cs.addressComps(comps, "DENIED")) {
			return Response.status(200).entity(comps).build();
		} else {
			return Response.status(400).build();
		}

	}
	
	@POST
	@Path("/approve")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response approveComps(List<Comps> comps) {
		System.out.println("comps " + comps);
		if(cs.addressComps(comps, "APPROVED")) {
			System.out.println("successful approval of comps");
			return Response.status(200).entity(comps).build();
		} else {
			System.out.println("FAILED approval of comps");
			return Response.status(400).build();
		}
	}
}
