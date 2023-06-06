package example;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;

import clinic.*;

import static org.junit.Assert.*;
import org.junit.Test;



public class TestR4_LoadFile {

  @Test
  public void testLoading() throws NoSuchPatient, NoSuchDoctor, IOException{
  	String regular = "P;Giovanni;Rossi;RSSGNN33B30F316I\n" +
					"P;Giuseppe;Verdi;VRDGPP76F09B666I\n" + 
					"M;345;Mario;Bianchi;BNCMRA44C99A320Z;Surgeon";
  	
  	Clinic s = new Clinic();
  	int n = s.loadData(new StringReader(regular));
  	
  	assertEquals("Wrong number of lines",3,n);
  	
  	String p = s.getPatient("VRDGPP76F09B666I");
  	assertNotNull("Missing patient Verdi",p);
  	assertTrue("Wrong name for patient Verdi", p.contains("Verdi"));

	String d = s.getDoctor(345);
	assertNotNull("Missing doctor Bianchi",d);
	assertTrue("Wrong name for doctor Bianchi", d.contains("Bianchi"));
  }

  @Test
  public void testTrivialErrors() throws IOException, NoSuchPatient, NoSuchDoctor{
	String normale = "P;Giovanni;Rossi;RSSGNN33B30F316I\n" +
				"P;Giuseppe;Verdi;VRDGPP76F09B666I\n" + 
				"P;Giuseppe;VRDGPP76F09B444I\n" + // missing last name 
				"M;Mario;Bianchi;BNCMRA44C99A320Y;Surgeon\n" + // missing id
				"M;345;Mario;Bianchi;BNCMRA44C99A320Z;Surgeon\n";

  	Clinic s = new Clinic();
  	int n = s.loadData(new StringReader(normale));
  	assertEquals("Wrong number of lines",3,n);
  	
  	String p = s.getPatient("VRDGPP76F09B666I");
  	assertNotNull("Missing patient Verdi",p);
  	assertTrue("Wrong name for Verdi", p.contains("Verdi"));

	String d = s.getDoctor(345);
	assertNotNull("Missing doctor Bianchi",d);
	assertTrue("Wrong name for doctor Bianchi", d.contains("Bianchi"));
  }
  
  @Test
  public void testTrivialErrorsListener() throws IOException, NoSuchPatient, NoSuchDoctor{
	String normale = "P;Giovanni;Rossi;RSSGNN33B30F316I\n" +
				"P;Giuseppe;Verdi;VRDGPP76F09B666I\n" + 
				"P;Giuseppe;VRDGPP76F09B444I\n" + // missing last name 
				"M;Mario;Bianchi;BNCMRA44C99A320Y;Surgeon\n" + // missing id
				"M;345;Mario;Bianchi;BNCMRA44C99A320Z;Surgeon\n";
	LinkedList<String> offending=new LinkedList<>();

  	Clinic s = new Clinic();
  	int n = s.loadData(new StringReader(normale), offending::add);
  	assertEquals("Wrong number of lines",3,n);
  	assertEquals("Wrong number of discarded lines",2,offending.size());
  	
  	String p = s.getPatient("VRDGPP76F09B666I");
  	assertNotNull("Missing patient Verdi",p);
  	assertTrue("Wrong name for Verdi", p.contains("Verdi"));

	String d = s.getDoctor(345);
	assertNotNull("Missing doctor Bianchi",d);
	assertTrue("Wrong name for doctor Bianchi", d.contains("Bianchi"));
  }

  
  @Test
  public void testSpecialErrorsExtraBlanks() throws IOException, NoSuchPatient {
		String extrBlanks = "P;Giovanni;Rossi;RSSGNN33B30F316I\n" +
					"P;Giuseppe; Verdi ; VRDGPP76F09B666I \n" + // added spaces
					"M;345;Mario;Bianchi;BNCMRA44C99A320Z;Surgeon\n";

	  	Clinic s = new Clinic();
	  	s.loadData(new StringReader(extrBlanks));
  		String p = s.getPatient("VRDGPP76F09B666I");
  	  	assertNotNull("Missing patient Verdi",p);
  	  	assertTrue("Verdi is missing", p.contains("Verdi"));
  }

  @Test
  public void testSpecialErrorsEmptyLine() throws IOException, NoSuchDoctor {
		String normale = "P;Giovanni;Rossi;RSSGNN33B30F316I\n" +
					"P;Giuseppe; Verdi;VRDGPP76F09B666I\n" + 
					"\n" + // empty line 
					"M;345;Mario;Bianchi;BNCMRA44C99A320Z;Surgeon\n";
		LinkedList<String> offending=new LinkedList<>();

	  	Clinic s = new Clinic();
	  	int n = s.loadData(new StringReader(normale),offending::add);
	  	assertEquals("Wrong number of lines",3,n);
	  	assertEquals("Wrong discarded line","",offending.get(0));
	  	
  		String d = s.getDoctor(345);
  		assertNotNull("Missing doctor Bianchi",d);
  		assertTrue("Wrong name doctor Bianchi", d.contains("Bianchi"));
  }
}