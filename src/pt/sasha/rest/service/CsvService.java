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
	private Response prepareCORSResponse(Object obj){
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
//		List<CsvRow> csvList = new ArrayList<CsvRow>();
//		List<Integer> values1 = new ArrayList<Integer>();
//		
//		CsvRow csv1 = new CsvRow();
//		csv1.setId(1);
//		values1.add(10); 
//		values1.add(20);
//		values1.add(30);
//		csv1.setVars(values1);
//		csv1.setDecision(1);
//		csvList.add(csv1);
//		
//		List<Integer> values2 = new ArrayList<Integer>();
//		
//		CsvRow csv2 = new CsvRow();
//		csv2.setId(2);
//		values2.add(40);
//		values2.add(50);
//		values2.add(60);
//		csv2.setVars(values2);
//		csv2.setDecision(1);
//		csvList.add(csv2);
//		
//		List<Integer> values3 = new ArrayList<Integer>();
//		CsvRow csv3 = new CsvRow();
//		csv3.setId(3);
//		values3.add(70);
//		values3.add(80);
//		values3.add(90);
//		csv3.setVars(values3);
//		csv3.setDecision(0);
//		csvList.add(csv3);
		
		CsvFile csvFile = new FileUploadBo().getCsvFile();
		
		return prepareCORSResponse(csvFile.getRows());
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
		
		return prepareCORSResponse(new User());
	}

}
