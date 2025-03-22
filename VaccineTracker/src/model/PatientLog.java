package model;


public class PatientLog {
	private String patientName;
	private VaccineBatch[] shots;
	private String[] clinics;
	private String[] shotDates;
	private int count;
	private final int MAX;
	private String appointmentNote;

	public PatientLog(String name, int maxShots) {
		this.patientName = name;
		this.MAX = maxShots;
		this.shots = new VaccineBatch[MAX];
		this.clinics = new String[MAX];
		this.shotDates = new String[MAX];
		this.count = 0;
		this.appointmentNote = null;
	}

	public void logShot(VaccineBatch vb, String clinic, String date) {
		if (count < MAX) {
			shots[count] = vb;
			clinics[count] = clinic;
			shotDates[count] = date;
			count++;
		}
	}

	public void bookAppointment(String clinicName) {
		this.appointmentNote = String.format("Next vaccine appointment booked at %s", clinicName);
	}

	public String getAppointmentNote() {
		return (appointmentNote == null) ? "No upcoming appointments." : appointmentNote;
	}

	public String getShotSummary() {
		if (count == 0) {
			return String.format("%s has no recorded vaccinations.", patientName);
		}

		String res = String.format("Total shots for %s: %d [", patientName, count);
		for (int i = 0; i < count; i++) {
			res += String.format("%s at %s on %s", shots[i], clinics[i], shotDates[i]);
			if (i < count - 1) res += "; ";
		}
		res += "]";
		return res;
	}

	public int getTotalShots() {
		return count;
	}

	public boolean receivedCertifiedOnly() {
		for (int i = 0; i < count; i++) {
			if (!shots[i].isCertified()) {
				return false;
			}
		}
		return count > 0;
	}

	public String getPatientName() {
		return patientName;
	}
}

