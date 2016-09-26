package pt.sasha.rest.service;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import pt.sasha.rest.dto.User;

/**
 * http://localhost:8080/java-web-resteasy/rs/users
 * @author Marco
 *
 */
@Path("/users")
public class UserService {
	
	@GET
	@Produces("application/json")
	public User getServiceInfo() {
		User user = new User();
		user.setId(1);
		user.setFirstName("Marco");
		user.setLastName("Saraiva");
		user.setUri("https://github.com/saraivamarco");
		user.setLastModified(new Date());
		
		return user;
	}
}