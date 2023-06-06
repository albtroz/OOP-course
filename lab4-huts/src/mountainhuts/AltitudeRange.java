package mountainhuts;

public class AltitudeRange {
	private String range;
	private Integer min;
	private Integer max;
	
	public AltitudeRange(String range) {
		this.range = range;
		String[] r = range.split("-");
		min = Integer.parseInt(r[0]);
        max = Integer.parseInt(r[1]);
	}
	
	public String getRange() {
		return range;
	}
	public Integer getRangeMin() {
		return min;
	}
	public Integer getRangeMax() {
		return max;
	}
}
