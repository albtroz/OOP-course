package clinic;

public class Patient {
	private String first;
	private String last;
	private String ssn;
	
	public Patient(String first, String last, String ssn) {
		this.first = first;
		this.last = last;
		this.ssn = ssn;
	}
	
	public String getFirst() {
		return first;
	}
	
	public String getLast() {
		return last;
	}
	
	public String getSSN() {
		return ssn;
	}
	
	public String getAllInfo() {
		return (new StringBuilder(this.last+" "+this.first+" ("+this.ssn+")").toString());
	}
	
}
