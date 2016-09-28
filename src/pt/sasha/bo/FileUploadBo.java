package pt.sasha.bo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
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
	
	
	public List<String> getCsvVarHeaders(){	
		List<String> vars = new ArrayList<String>();		
		if(FileUploadServlet.FILE_TEMP_PATH==null){
			vars.add("Var1");
			return vars;
		}		
		CSVParser parser = parseCsvFile();	
		List<CSVRecord> csvRecords = null;
		try {
			csvRecords = parser.getRecords();
			parser.close();
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}		
		for ( int row=0; row<1; row++ ){
			CSVRecord record = csvRecords.get(row);
			for (int i=1; i<record.size()-1;i++){
				vars.add(new String(record.get(i)));
			}
		}		
		return vars;		
	}
	
	private CSVParser parseCsvFile(){
		CSVParser parser = null;
		CsvFile csvFile = new CsvFile();
		// If there's no csv data, fill table with dummy data
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
			try {
				parser = CSVParser.parse(csvData,Charset.defaultCharset(), CSVFormat.RFC4180);
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}		
		return parser;
	}
	
	public CsvFile getCsvFile() {			
		if(FileUploadServlet.FILE_TEMP_PATH==null)	
			return getDummyFile();
		
		CsvFile csvFile = new CsvFile();
		CSVParser parser = parseCsvFile();
	
		List<CSVRecord> csvRecords = null;
		try {
			csvRecords = parser.getRecords();
			parser.close();			
			new File(FileUploadServlet.FILE_TEMP_PATH).delete();
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				LOGGER.severe("Thread sleep not working");
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
		
		CsvRow csvRow = new CsvRow();
		List<CsvRow> csvRows = new ArrayList<CsvRow>();
		List<Integer> vars = new ArrayList<Integer>();
		
		for ( int row=1; row<csvRecords.size(); row++ ){
			CSVRecord record = csvRecords.get(row);
			csvRow = new CsvRow();
			vars = new ArrayList<Integer>();
			for (int i=0; i<record.size();i++){
				if(i==0){
					csvRow.setId(Integer.valueOf(record.get(i)));
				}else if(i==record.size()-1){
					csvRow.setDecision(Integer.valueOf(record.get(i)));
				}else if(!(i==0 && i==record.size()-1)){
					vars.add(Integer.valueOf(record.get(i)));
				}
			}
			csvRow.setVars(vars);
			csvRows.add(csvRow);
		}
		csvFile.setRows(csvRows);		
		
		return filterCsvFile(csvFile);
	}
	
	private static Integer FMIN;
	private static Integer FMAX;
	
	/**
	 * Entry point for filtering the CSV file
	 * @param csvFile
	 * @return
	 */
	private CsvFile filterCsvFile(CsvFile csvFile){
		CsvFile finalCsv = new CsvFile();
		List<CsvRow> finalCsvRowList = new ArrayList();
		
		List<CsvRow> rows = csvFile.getRows();
		CsvRow csvRow = null;
		
		int varSize = rows.get(0).getVars().size();
		
		for (int columnIndex=0; columnIndex<varSize; columnIndex++){
			setColumnMinMaxValueWithDecisionOne(rows, columnIndex);
			
			for (int i=0; i<rows.size(); i++){
				csvRow = rows.get(i);				
				if(csvRow.getDecision()==0){
					
					Integer var = rows.get(i).getVars().get(columnIndex);
					
					if(var<FMIN || var>FMAX){
						//ignore
//						LOGGER.info("Removed(ignored) row from final Csv File.");
					}else{
						//add to my list of rows if id does not exist already
						if(finalCsvRowList.isEmpty()){
							finalCsvRowList.add(csvRow);	
						}else{
							boolean addToList = false;
							for(CsvRow evalRow : finalCsvRowList){
								if(evalRow.getId()!=csvRow.getId()){
									//continue to fetch
									addToList = true;
								}else{
									addToList = false;
									break;
								}
							}
							if(addToList){
								finalCsvRowList.add(csvRow);
							}
						}
					}
				}else{
					if(finalCsvRowList.isEmpty()){
						finalCsvRowList.add(csvRow);	
					}else{
						boolean addToList = false;
						for(CsvRow evalRow : finalCsvRowList){
							if(evalRow.getId()!=csvRow.getId()){
								//continue to fetch
								addToList = true;
							}else{
								addToList = false;
								break;
							}
						}
						if(addToList){
							finalCsvRowList.add(csvRow);
						}
					}
				}
			
			}
		}
		
		Collections.sort(finalCsvRowList);
		finalCsv.setRows(finalCsvRowList);
				
		return finalCsv;
	}
	
	/**
	 * 1.st step would be to sort out the MIN and MAX value with decision == 1 within the column
	 * that we are iterating right now.
	 * @param rows
	 * @param columnIndex
	 */
	private void setColumnMinMaxValueWithDecisionOne(List<CsvRow> rows, int columnIndex){		
		CsvRow csvRow = null;
		List colVars = new ArrayList();
		
		for(CsvRow row : rows){
			if(row.getDecision()==1){
				colVars.add(row.getVars().get(columnIndex));
			}
		}
		Collections.sort(colVars);
		
		FMIN = (Integer)colVars.get(0);		
		int max = colVars.size()-1;		
		FMAX = (Integer)colVars.get(max);
	}
	
	
	private CsvFile getDummyFile(){
		CsvFile csvFile = new CsvFile();		
		CsvRow row = new CsvRow();
		List vars = new ArrayList();
		vars.add(Integer.valueOf(0));
		row.setVars(vars);
		row.setDecision(0);
		List rows = new ArrayList();
		rows.add(row);
		
		csvFile.setRows(rows);
		
		return csvFile;
	}

}
