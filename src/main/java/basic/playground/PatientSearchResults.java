package basic.playground;

import org.hl7.fhir.r4.model.Patient;

import java.util.List;

public class PatientSearchResults {

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    private List<Patient> patients;
    private long timeInMillis;
}
