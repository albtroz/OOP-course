package clinic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {
	Map<String, Patient> patients = new HashMap<>();
	Map<Integer, Doctor> doctors = new HashMap<>();
	Map<Patient, Doctor> assignments = new HashMap<>();
	
	/**
	 * Add a new clinic patient.
	 * 
	 * @param first first name of the patient
	 * @param last last name of the patient
	 * @param ssn SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {
		if (!patients.containsKey(ssn)) {
			Patient p = new Patient(first, last, ssn);
			patients.put(ssn, p);
		}
	}


	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
		if (!patients.containsKey(ssn)) {
			throw new NoSuchPatient();
		} 
		return patients.get(ssn).getAllInfo();
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		if (!doctors.containsKey(docID)) {
			Doctor d = new Doctor(first, last, ssn, docID, specialization);
			doctors.put(docID, d);
			addPatient(first, last, ssn);
		}
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		if (!doctors.containsKey(docID)) {
			throw new NoSuchDoctor();
		} 
		return doctors.get(docID).getAllInfo();
	}
	
	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn SSN of the patient
	 * @param docID ID of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		if (!patients.containsKey(ssn)) {
			throw new NoSuchPatient();
		}
		if (!doctors.containsKey(docID)) {
			throw new NoSuchDoctor();
		}
		Patient p = patients.get(ssn);
		Doctor d = doctors.get(docID);
		assignments.put(p, d);
	}

	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		if (!patients.containsKey(ssn)) {
			throw new NoSuchPatient();
		}
		Patient p = patients.get(ssn);
		if (!assignments.containsKey(p)) {
			throw new NoSuchDoctor();
		}
		return assignments.get(p).getID();
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		if (!doctors.containsKey(id)) {
			throw new NoSuchDoctor();
		}
		
		return assignments.entrySet()
							.stream()
							.filter(e->e.getValue().getID() == id)
							.map(e->e.getKey().getSSN())
							.collect(Collectors.toList());
	}
	
	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and speciality.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param reader reader linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader) throws IOException {
		return loadData(reader, errorMessage -> { });
	}


	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and speciality.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method calls the
	 * {@link ErrorListener#offending} method passing the line itself,
	 * ignores the row, and skip to the next one.<br>
	 * 
	 * @param reader reader linked to the file to be read
	 * @param listener listener used for wrong line notifications
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader, ErrorListener listener) throws IOException {
		int i = 0;
		// Scanner s = new Scanner(reader);
		BufferedReader br = null;
		Pattern p = Pattern.compile("(^P\\s*;\\s*\\w+\\s*;\\s*\\w+\\s*;\\s*\\w+)|(^M\\s*;\\s*\\d+\\s*;\\s*\\w+\\s*;\\s*\\w+\\s*;\\s*\\w+\\s*;\\s*\\w+)");
        try {
            br = new BufferedReader(reader);
            String row;
			while((row = br.readLine()) != null) {
				Matcher m = p.matcher(row);
				if (m.find()) {
					i++;
					if (m.group(2) == null) {
						String[] fields = m.group(1).split("\\s*;\\s*");
						addPatient(fields[1], fields[2], fields[3]);
					} else {
						String[] fields = m.group(2).split("\\s*;\\s*");
						addDoctor(fields[2], fields[3], fields[4], Integer.parseInt(fields[1]), fields[5]);
					}
				} else {
					listener.offending(row);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            if (reader != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return i;
	}
	
	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' ids
	 */
	public Collection<Integer> idleDoctors(){
		return doctors
				.values()
				.stream()
				.sorted(Comparator.comparing(Doctor::getLast).thenComparing(Doctor::getFirst))
				.filter(doctor->!assignments.containsValue(doctor))
				.map(Doctor::getID)
				.collect(Collectors.toList());
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * @return  the collection of doctors' ids
	 */
	public Collection<Integer> busyDoctors(){
		double avg = doctorWithPatients()
									.values()
									.stream()
									.collect(Collectors.averagingDouble(x -> x));
		return doctorWithPatients()
							.entrySet()
							.stream()
							.filter(e->e.getValue() > avg)
							.map(e->e.getKey().getID())
							.collect(Collectors.toList());
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients(){
		return doctorWithPatients()
					.entrySet()
					.stream()
					.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					.map(e->String.format("%3d", e.getValue())+" : "+e.getKey().getID()+" "+e.getKey().getLast()+"" +e.getKey().getFirst())
					.collect(Collectors.toList());
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's)  speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	public Collection<String> countPatientsPerSpecialization(){
		return assignments
					.entrySet()
					.stream()
					.collect(Collectors.groupingBy(x->x.getValue().getSpecialization(), Collectors.counting()))
					.entrySet()
					.stream()
					.sorted(Comparator.comparing(Map.Entry<String, Long>::getValue).reversed().thenComparing(Map.Entry<String, Long>::getKey))
					.map(e->String.format("%3d", e.getValue())+" - "+e.getKey())
					.collect(Collectors.toList());
	}
	
	public Map<Doctor, Long> doctorWithPatients() {
		Map<Doctor, Long> res =  assignments
									.entrySet()
									.stream()
									.collect(Collectors.groupingBy(x->x.getValue(), Collectors.counting()));
		doctors.values().stream().forEach(doctor->res.putIfAbsent(doctor, 0L));
		return res;
	}
	
}
