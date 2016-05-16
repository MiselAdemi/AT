package session;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.Form;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import beans.User;
import sqlConn.ConnectionDB;

@LocalBean
@Path("/users")
@Stateless
public class RestService implements RestRemote {
	
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test(@Context HttpServletRequest request) {
		//request.getSession().setAttribute("user", "pera");
		System.out.println("JAX-RS test");
		try {
			System.out.println(new File(".").getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "OK test3";
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean login(User user) {
		//System.out.println(user.getUsername());
		ConnectionDB conn = new ConnectionDB();
		
		System.out.println("################" + user);
		
		List<User> users = conn.getAllUsers();
		System.out.println("Pokupio podatke.");
		
		for(User u:users)
		{
			
			if(u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword()))
			{
				
				System.out.println("Ulogovan!");
				
				return true;
				
			}
			
		}
		
		return false;
	}
	
}
