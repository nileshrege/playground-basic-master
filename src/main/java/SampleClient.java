import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import ca.uhn.fhir.util.BundleUtil;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class SampleClient {

    public static void main(String[] theArgs) {

        // Create a FHIR client
        FhirContext fhirContext = FhirContext.forR4();
        IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
        client.registerInterceptor(new LoggingInterceptor(false));

        // Search for Patient resources
        Bundle bundle = client
                .search()
                .forResource("Patient")
                .where(Patient.FAMILY.matches().value("SMITH"))
                .returnBundle(Bundle.class)
                .execute();

        List<IBaseResource> patients = new ArrayList<>();
        patients.addAll(BundleUtil.toListOfResources(fhirContext, bundle));

        Function<Patient, String> formatter = p -> "First Name: "
                .concat(String.valueOf(p.getName().get(0).getGiven().get(0)))
                .concat(" Last Name: ")
                .concat(String.valueOf(p.getName().get(0).getFamily()))
                .concat(" DOB: ")
                .concat(String.valueOf(p.getBirthDate()));

        patients.stream()
                .filter(obj -> obj instanceof Patient)
                .map(Patient.class::cast)
                .sorted(Comparator.comparing(p -> String.valueOf(p.getName().get(0).getGiven().get(0))))
                .forEach(p -> System.out.println(formatter.apply(p)));

    }

}
