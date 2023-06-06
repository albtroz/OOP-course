package example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import mountainhuts.Region;

public class TestR4_Queries {

	private Region r;

	@Before
	public void setUp() {
		r = Region.fromFile("Piemonte", TestR3_ReadData.file);
	}
	
	@Test
	public void testCountMunicipalitiesPerProvince() {
		assertNotNull("Missing region", r);

		Map<String, Long> res = r.countMunicipalitiesPerProvince();
		
		assertNotNull("Missing count of municipalities per province", res);

		String[] provinceKeys = { "ALESSANDRIA", "CUNEO", "TORINO", "VERCELLI", "BIELLA", "VERBANO-CUSIO-OSSOLA" };
		Long[] provinceValues = { 1L, 25L, 24L, 13L, 12L, 19L };
		for (int i = 0; i < provinceKeys.length; i++) {
			assertEquals("Wrong number of municipalities per " + provinceKeys[i], provinceValues[i], res.get(provinceKeys[i]));
		}
	}
	
	@Test
	public void testCountMountainHutsPerMunicipalityPerProvince() {
		assertNotNull("Missing region", r);

		Map<String, Map<String, Long>> res = r.countMountainHutsPerMunicipalityPerProvince();

		assertNotNull("Missing count of mountain huts per municipality per province", res);
		
		Map<String, Long> resTo = res.get("TORINO");
		assertNotNull("Missing count of mountain huts in province of Torino", resTo);
		assertEquals("Wrong number of municipalities in province of Torino", 24, resTo.size());
		assertNotNull("Missing count of mountain huts in municipality of Bussoleno", resTo.get("BUSSOLENO"));
		assertEquals("Wrong number of mountain huts in municipality of Bussoleno", Long.valueOf(2), resTo.get("BUSSOLENO"));
		
		Map<String, Long> resAl = res.get("ALESSANDRIA");
		assertNotNull("Missing count of mountain huts in province of Alessandria", resAl);
		assertEquals("Wrong number of municipalities in province of Alessandria", 1, resAl.size());
		assertNotNull("Missing count of mountain huts in municipality of Bosio", resAl.get("BOSIO"));
		assertEquals("Wrong number of mountain huts in municipality of Bosio", Long.valueOf(1), resAl.get("BOSIO"));
	}
	
	@Test
	public void testCountMountainHutsPerAltitudeRange() {
		assertNotNull("Missing region", r);

		r.setAltitudeRanges("0-1000", "1001-1500", "1501-2000");
		Map<String, Long> res = r.countMountainHutsPerAltitudeRange();
		
		assertNotNull("Missing count of mountain huts per altitude range", res);
				
		String[] resKeys = { "0-1000", "1001-1500", "1501-2000", "0-INF" };
		Long[] resValues = { 22L, 36L, 52L, 57L };
		for (int i = 0; i < resKeys.length; i++) {
			assertEquals("Wrong number of mountain huts in altitude range " + resKeys[i], resValues[i], res.get(resKeys[i]));
		}
	}
	
	@Test
	public void testTotalBedsNumberPerProvince() {
		assertNotNull("Missing region", r);

		Map<String, Integer> res = r.totalBedsNumberPerProvince();

		assertNotNull("Missing total beds number per province", res);
		
		String[] resKeys = { "ALESSANDRIA", "CUNEO", "TORINO", "VERCELLI", "BIELLA", "VERBANO-CUSIO-OSSOLA" };
		Integer[] resValues = { 10, 1046, 953, 534, 237, 852 };
		for (int i = 0; i < resKeys.length; i++) {
			assertEquals("Wrong number of beds number in province " + resKeys[i], resValues[i], res.get(resKeys[i]));
		}
	}
	
	@Test
	public void testMaximumBedsNumberPerAltitudeRange() {
		assertNotNull("Missing region", r);

		r.setAltitudeRanges("0-1000", "1001-1500", "1501-2000");
		Map<String, Optional<Integer>> res = r.maximumBedsNumberPerAltitudeRange();
		
		assertNotNull("Missing maximum beds number per altitude range", res);
		
		String[] resKeys = { "0-1000", "1001-1500", "1501-2000", "0-INF" };
		Integer[] resValues = { 27, 89, 95, 96 };
		for (int i = 0; i < resKeys.length; i++) {
			assertEquals("Wrong number of maximum beds number in altitude range " + resKeys[i], resValues[i], res.get(resKeys[i]).get());
		}
	}
	
	@Test
	public void testMunicipalityNamesPerCountOfMountainHuts() {
		assertNotNull("Missing region", r);

		Map<Long, List<String>> res = r.municipalityNamesPerCountOfMountainHuts();
		
		assertNotNull("Missing set of municipality names per count of mountain huts", res);
		
		Long[] resKeys = { 1L, 2L, 3L, 4L, 5L, 10L, 11L };
		int[] resValues = { 61, 15, 11, 3, 2, 1, 1 };
		for (int i = 0; i < resKeys.length; i++) {
			assertEquals("Wrong number of municipalities per count " + resKeys[i], resValues[i], res.get(resKeys[i]).size());
		}
		
		assertEquals("Wrong first municipality per count 1", "ANDORNO MICCA", res.get(1L).get(0));
		assertEquals("Wrong first municipality per count 2", "ARGENTERA", res.get(2L).get(0));
		assertEquals("Wrong first municipality per count 3", "ACCEGLIO", res.get(3L).get(0));
		assertEquals("Wrong first municipality per count 4", "BOGNANCO", res.get(4L).get(0));
		assertEquals("Wrong first municipality per count 5", "ENTRACQUE", res.get(5L).get(0));
	}

}
