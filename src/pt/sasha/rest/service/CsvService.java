package pt.sasha.rest.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import pt.sasha.bo.FileUploadBo;
import pt.sasha.rest.dto.CsvFile;

/**
 * http://localhost:8080/java-web-resteasy/rs/csv
 * @author marco
 *
 */

@Path("/csv")
public class CsvService {
	
	/**
	 * CORS - Cross Origin Resource Sharing
	 * @return
	 */
	private Response restCORSResponse(Object obj){
		return Response
	            .status(200)
	            .header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600")
	            .entity(obj)
	            .build();
	}
	
	@GET
	@Produces("application/json")
	public Response getCsvRowsService() {
		CsvFile file = new FileUploadBo().getCsvFile();	
		return restCORSResponse(file.getRows());
	}
	
	@GET
	@Path("vheaders")
	@Produces("application/json")
	public Response getCsvInitialVarsServiceInfo() {		
		List<String> csvVarHeaders = (List<String>)new FileUploadBo().getCsvVarHeaders();
		return restCORSResponse(csvVarHeaders);
	}
	

}
