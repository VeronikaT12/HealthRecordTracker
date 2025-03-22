package tests;

import model.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class StarterTests {

    @Test
    public void testEmptyRecord() {
        PatientLog log = new PatientLog("Diana", 3);
        assertEquals("Diana has no recorded vaccinations.", log.getShotSummary());
        assertEquals(0, log.getTotalShots());
        assertFalse(log.receivedCertifiedOnly());
        assertEquals("No upcoming appointments.", log.getAppointmentNote());
    }

    @Test
    public void testSingleCertifiedShot() {
        VaccineBatch vb = new VaccineBatch("XRNA-1", "RNA", "NovaPharm");
        PatientLog log = new PatientLog("Eli", 2);
        log.logShot(vb, "ImmuniCare East", "2025-01-15");

        assertTrue(vb.isCertified());
        assertTrue(log.receivedCertifiedOnly());
        assertEquals(1, log.getTotalShots());
    }

    @Test
    public void testMixedShots() {
        VaccineBatch vb1 = new VaccineBatch("YRNA-2", "RNA", "GeneBio");
        VaccineBatch vb2 = new VaccineBatch("ZNVX-5", "Inactive", "MediCore");

        PatientLog log = new PatientLog("Finn", 3);
        log.logShot(vb1, "Central Health", "2025-02-20");
        log.logShot(vb2, "Northside Clinic", "2025-03-12");

        assertFalse(log.receivedCertifiedOnly());
        assertEquals(2, log.getTotalShots());

        String summary = log.getShotSummary();
        assertTrue(summary.contains("Certified vaccine"));
        assertTrue(summary.contains("Uncertified vaccine"));
    }

    @Test
    public void testMaxShotsNotExceeded() {
        VaccineBatch vb = new VaccineBatch("XRNA-1", "RNA", "NovaPharm");
        PatientLog log = new PatientLog("Grace", 2);

        log.logShot(vb, "Clinic A", "2025-04-01");
        log.logShot(vb, "Clinic B", "2025-04-15");
        log.logShot(vb, "Clinic C", "2025-05-01"); 
        assertEquals(2, log.getTotalShots());
    }

    @Test
    public void testMultipleCertified() {
        VaccineBatch vb1 = new VaccineBatch("XRNA-1", "RNA", "NovaPharm");
        VaccineBatch vb2 = new VaccineBatch("MVEC-3", "Vector", "HelixCo");

        PatientLog log = new PatientLog("Hana", 3);
        log.logShot(vb1, "Site 1", "2025-01-01");
        log.logShot(vb2, "Site 2", "2025-02-01");

        assertTrue(log.receivedCertifiedOnly());
        assertEquals(2, log.getTotalShots());
    }

    @Test
    public void testToStringFormat() {
        VaccineBatch vb = new VaccineBatch("XRNA-1", "RNA", "NovaPharm");
        PatientLog log = new PatientLog("Ivan", 1);
        log.logShot(vb, "Downtown Clinic", "2025-06-01");

        String expected = "Total shots for Ivan: 1 [Certified vaccine: XRNA-1 (RNA; NovaPharm) at Downtown Clinic on 2025-06-01]";
        assertEquals(expected, log.getShotSummary());
    }

    @Test
    public void testAppointmentBooking() {
        PatientLog log = new PatientLog("Julia", 3);
        log.bookAppointment("Green Valley Clinic");
        assertEquals("Next vaccine appointment booked at Green Valley Clinic", log.getAppointmentNote());
    }

    @Test
    public void testCertifiedFlagForEachBatch() {
        VaccineBatch v1 = new VaccineBatch("ZZZZ-0", "Unknown", "ShadyCo");
        VaccineBatch v2 = new VaccineBatch("MVEC-3", "Vector", "HelixCo");
        assertFalse(v1.isCertified());
        assertTrue(v2.isCertified());
    }
}
