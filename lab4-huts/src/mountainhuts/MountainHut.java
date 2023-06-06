package mountainhuts;

import java.util.Optional;

/**
 * Represents a mountain hut
 * 
 * It includes a name, optional altitude, category,
 * number of beds and location municipality.
 *  
 *
 */
public class MountainHut {
	private String name;
	private Optional<Integer> altitude;
	private String category;
	private Integer bedsNumber;
	private Municipality municipality;
	
	public MountainHut(String name, Integer altitude, String category, Integer bedsNumber, Municipality municipality) {
		this.name = name;
		this.altitude = Optional.ofNullable(altitude);
		this.category = category;
		this.bedsNumber = bedsNumber;
		this.municipality = municipality;
	}

	public String getName() {
		return name;
	}

	public Optional<Integer> getAltitude() {
		return altitude;
	}

	public String getCategory() {
		return category;
	}

	public Integer getBedsNumber() {
		return bedsNumber;
	}

	public Municipality getMunicipality() {
		return municipality;
	}
}
