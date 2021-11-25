package basic.playground;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PatientInfoTest {
    private PatientSearch patientSearch;
    private PatientInfo patientInfo;

    @Before
    public void setUp() {
        patientSearch = new PatientSearch();
        patientInfo = new PatientInfo();
    }

    @Test
    public void list(){
        PatientSearchResults searchResults = patientSearch.searchBy("SMITH", true);
        List<String> info = patientInfo.list(searchResults.getPatients());
        info.forEach(System.out::println);
    }
}
