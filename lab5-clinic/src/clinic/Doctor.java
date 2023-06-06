package clinic;

public class Doctor {
	private String first;
	private String last;
	private String ssn;
	private int docID;
	private String specialization;
	
	public Doctor(String first, String last, String ssn, int docID, String specialization) {
		this.first = first;
		this.last = last;
		this.ssn = ssn;
		this.docID = docID;
		this.specialization = specialization;
	}
	
	public String getFirst() {
		return first;
	}
	
	public String getLast() {
		return last;
	}
	
	public int getID() {
		return docID;
	}
	
	public String getSpecialization() {
		return specialization;
	}
	
	public String getAllInfo() {
		return (new StringBuilder(last+" "+first+" ("+ssn+") ["+docID+"]: "+specialization).toString());
	}
}
