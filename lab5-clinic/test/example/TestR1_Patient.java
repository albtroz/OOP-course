package example;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import clinic.*;

public class TestR1_Patient {
	// "<Last> <First> (<SSN>)"
	static Pattern patientFormat = Pattern.compile(
			"\\s*([a-zA-Z',-]+)\\s*([a-zA-Z',-]+)\\s*\\(\\s*(\\w{16})\\s*\\)\\s*");

  @Test
  public void AddPatient() throws NoSuchPatient {
    Clinic s = new Clinic();

    String ssn = "THEPID12I99F181K";
    String name = "Giova";
    String surname = "Reds";
    s.addPatient(name, surname, ssn);

    String p = s.getPatient(ssn);
    
    assertNotNull("Missing patient info",p);
    
    Matcher m = patientFormat.matcher(p);
    assertTrue("Wrong format for patient string '" + p + "'",m.matches());
    assertEquals("Wrong in patient info", name, m.group(2));
    assertEquals("Wrong in patient info", surname, m.group(1));
    assertEquals("Wrong SSN in patient info", ssn, m.group(3));

}

  @Test(expected=NoSuchPatient.class)
  public void testNotFoundEmpty() throws NoSuchPatient {
    Clinic s = new Clinic();

    String ssn = "THEPID12I99F181K";

    /*Person p =*/ s.getPatient(ssn);
  }

  @Test(expected=NoSuchPatient.class)
  public void testNotFound() throws NoSuchPatient {
    Clinic s = new Clinic();

    String cf = "THEPID12I99F181K";
    String nome = "Giova";
    String cognome = "Reds";

    s.addPatient(nome, cognome, cf);

    /*Person p =*/ s.getPatient(cf + "_");
  }


}
