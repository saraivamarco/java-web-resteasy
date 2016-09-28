package pt.sasha.rest.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class CsvRow implements Serializable, Comparable<CsvRow>  {
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id")
	private int id;
	
	@XmlElement(name = "vars")
	private List<Integer> vars;
	
	@XmlElement(name = "decision")
	private int decision;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public List<Integer> getVars() {
		return vars;
	}

	public void setVars(List<Integer> vars) {
		this.vars = vars;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getDecision() {
		return decision;
	}

	public void setDecision(int decision) {
		this.decision = decision;
	}

	@Override
	public int compareTo(CsvRow o) {
		if ( this.getId() > o.getId() )
            return 1;
        else if ( this.getId() < o.getId() )
            return -1;
        else {
             if ( this.getId() > o.getId() )
                 return 1;
             else
                 return -1;
        }
	}

}
