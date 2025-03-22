package model;

public class VaccineBatch {
	private String code;
	private String technology;
	private String maker;
	private boolean certified;

	public VaccineBatch(String code, String technology, String maker) {
		this.code = code;
		this.technology = technology;
		this.maker = maker;
		this.certified = code.equals("XRNA-1") || code.equals("YRNA-2") || code.equals("MVEC-3");
	}

	public boolean isCertified() {
		return this.certified;
	}

	public String toString() {
		if (certified) {
			return String.format("Certified vaccine: %s (%s; %s)", code, technology, maker);
		} else {
			return String.format("Uncertified vaccine: %s (%s; %s)", code, technology, maker);
		}
	}

	public String getCode() {
		return code;
	}

	public String getMaker() {
		return maker;
	}

	public String getTechnology() {
		return technology;
	}
}
