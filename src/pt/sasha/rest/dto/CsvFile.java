package pt.sasha.rest.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "csv")
public class CsvFile implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "rows")
	private List<CsvRow> rows;

	public List<CsvRow> getRows() {
		return rows;
	}

	public void setRows(List<CsvRow> rows) {
		this.rows = rows;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
		

}
