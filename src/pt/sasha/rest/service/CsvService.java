package pt.sasha.rest.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import pt.sasha.bo.FileUploadBo;
import pt.sasha.rest.dto.CsvFile;
import pt.sasha.rest.dto.User;

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
	public Response getServiceInfo() {
		
		CsvFile csvFile = new FileUploadBo().getCsvFile();
		
//		return Response.status(200)
//	            .entity(csvFile)
//	            .build();
		
		
		return restCORSResponse(csvFile.getRows());
	}
	
	@POST
	@Produces("application/json")
	public Response getCsvFile(String path) {
		
		File csvData = new File(path);
		 CSVParser parser = null;
		try {
			parser = CSVParser.parse(csvData,Charset.defaultCharset(), CSVFormat.RFC4180);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		 for (CSVRecord csvRecord : parser) {
		     System.out.println(csvRecord);
		 }
		
		return restCORSResponse(new User());
	}

}
