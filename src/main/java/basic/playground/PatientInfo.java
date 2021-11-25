package basic.playground;

import org.hl7.fhir.r4.model.Patient;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PatientInfo {

    Function<Patient, String> formatter = p -> "First Name: "
            .concat(String.valueOf(p.getName().get(0).getGiven().get(0)))
            .concat(" Last Name: ")
            .concat(String.valueOf(p.getName().get(0).getFamily()))
            .concat(" DOB: ")
            .concat(String.valueOf(p.getBirthDate()));

    public List<String> list(List<Patient> patients){
        return patients.stream()
                .filter(Objects::nonNull)
                .map(Patient.class::cast)
                .sorted(Comparator.comparing(p -> String.valueOf(p.getName().get(0).getGiven().get(0))))
                .map(p -> formatter.apply(p))
                .collect(Collectors.toList());
    }
}
