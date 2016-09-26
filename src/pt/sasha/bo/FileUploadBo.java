package pt.sasha.bo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import pt.sasha.controller.FileUploadServlet;
import pt.sasha.rest.dto.CsvFile;
import pt.sasha.rest.dto.CsvRow;

public class FileUploadBo {
	private final static Logger LOGGER = 
            Logger.getLogger(FileUploadBo.class.getCanonicalName());
	
	public CsvFile getCsvFile(){
		CsvFile csvFile = new CsvFile();
		
		if(FileUploadServlet.FILE_TEMP_PATH==null){			
			CsvRow row = new CsvRow();
			List vars = new ArrayList();
			vars.add(Integer.valueOf(0));
			row.setVars(vars);
			row.setDecision(0);
			List rows = new ArrayList();
			rows.add(row);
			
			csvFile.setRows(rows);
		} 
		else{
			//Parse uploaded file
			File csvData = new File(FileUploadServlet.FILE_TEMP_PATH);
			 CSVParser parser = null;
			try {
				parser = CSVParser.parse(csvData,Charset.defaultCharset(), CSVFormat.RFC4180);
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			List<CSVRecord> csvRecords = null;
			try {
				csvRecords = parser.getRecords();
			} catch (IOException e) {
				LOGGER.severe(e.getMessage());
				e.printStackTrace();
			}
			
			System.out.println("Size of csvrecords is "+csvRecords.size());
			CsvRow csvRow = new CsvRow();
			List<CsvRow> csvRows = new ArrayList<CsvRow>();
			List<Integer> vars = new ArrayList<Integer>();
			
			for ( int row=1; row<csvRecords.size(); row++ ){
				System.out.println(csvRecords.get(row));
				CSVRecord record = csvRecords.get(row);
				csvRow = new CsvRow();
				vars = new ArrayList<Integer>();
				System.out.println("Record size is "+record.size());
				for (int i=0; i<record.size();i++){
					if(i==0){
						csvRow.setId(Integer.valueOf(record.get(i)));
					}else if(i==record.size()-1){
						csvRow.setDecision(Integer.valueOf(record.get(i)));
					}else if(!(i==0 && i==record.size()-1)){
						vars.add(Integer.valueOf(record.get(i)));
					}
					System.out.println(record.get(i));
				}
				csvRow.setVars(vars);
				csvRows.add(csvRow);
			}
			csvFile.setRows(csvRows);
		}
		
		return csvFile;
	}

}
