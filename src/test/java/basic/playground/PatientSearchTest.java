package basic.playground;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PatientSearchTest {

    private File file;
    private PatientSearch patientSearch;

    @Before
    public void setUp() {
        String path = "patients.txt";
        file = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource(path)).getFile());
        patientSearch = new PatientSearch();
    }

    @Test
    public void searchInLoop() {
        for(int i=0; i<3; i++) {
            search(i < 2);
        }

    }

    private void search(boolean isCacheEnabled){
        List<Long> responseTimeList = new ArrayList<>(20);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String lastName = line.substring(line.lastIndexOf(' ') + 1);
                PatientSearchResults searchResults = patientSearch.searchBy(lastName, isCacheEnabled);

                Assert.assertNotNull(searchResults.getPatients());
                responseTimeList.add(searchResults.getTimeInMillis());

            }
            OptionalDouble optionalDouble = responseTimeList.stream()
                    .mapToLong(Long::longValue)
                    .average();

            optionalDouble.ifPresent(averageResponseTime -> System.out.println("averageResponseTime "+averageResponseTime));
            scanner.close();
        } catch (FileNotFoundException e) {
            Assert.fail("File not found");
        }
    }
}
