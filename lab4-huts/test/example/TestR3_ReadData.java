package example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import mountainhuts.MountainHut;
import mountainhuts.Municipality;
import mountainhuts.Region;

public class TestR3_ReadData {

	static String file;
	static {
		File outFile;

		try {
			outFile = File.createTempFile("mountain_huts", ".csv");
			outFile.deleteOnExit();
			System.out.println("Extracting data file: " + outFile.getAbsolutePath());
			try (InputStream in = TestR3_ReadData.class.getResourceAsStream("mountain_huts.csv"); 
				FileOutputStream out = new FileOutputStream(outFile)) {
				byte[] b = new byte[2048];
				int n = 0;
				while ((n = in.read(b)) != -1) {
					out.write(b, 0, n);
				}
				file = outFile.getCanonicalPath();
			}
		} catch (IOException e) {
			file = null;
			System.err.println(e);
			outFile = null;
		}
	}

	private Region r;

	@Before
	public void setUp() throws IOException {
		r = Region.fromFile("Piemonte", file);
	}

	@Test
	public void testReadMunicipalities() throws IOException {
		assertNotNull("Missing region", r);

		Collection<Municipality> municipalities = r.getMunicipalities();
		assertNotNull("Missing municipalities", municipalities);
		assertEquals("Wrong number of municipalities", 94, municipalities.size());

		Map<String, Long> provinces = municipalities.stream()
				.collect(Collectors.groupingBy(Municipality::getProvince, Collectors.counting()));
		String[] provinceKeys = { "ALESSANDRIA", "CUNEO", "TORINO", "VERCELLI", "BIELLA", "VERBANO-CUSIO-OSSOLA" };
		Long[] provinceValues = { 1L, 25L, 24L, 13L, 12L, 19L };
		for (int i = 0; i < provinceKeys.length; i++) {
			assertEquals("Wrong number of " + provinceKeys[i], provinceValues[i], provinces.get(provinceKeys[i]));
		}
	}

	@Test
	public void testReadMountainHuts() throws IOException {
		assertNotNull("Missing region", r);

		Collection<MountainHut> mountainHuts = r.getMountainHuts();
		assertNotNull("Missing mountain huts", mountainHuts);
		assertEquals("Wrong number of mountain huts", 167, mountainHuts.size());

		Map<String, Long> categories = mountainHuts.stream()
				.collect(Collectors.groupingBy(MountainHut::getCategory, Collectors.counting()));
		String[] categoryKeys = { "Bivacco Fisso", "Rifugio Alpino", "Rifugio Escursionistico", "Rifugio non gestito" };
		Long[] categoryValues = { 27L, 91L, 33L, 16L };
		for (int i = 0; i < categoryKeys.length; i++) {
			assertEquals("Wrong number of " + categoryKeys[i], categoryValues[i], categories.get(categoryKeys[i]));
		}
	}

}
