package basic.playground;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.AdditionalRequestHeadersInterceptor;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import ca.uhn.fhir.util.BundleUtil;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PatientSearch {

    public PatientSearchResults searchBy(String lastName, boolean isCacheEnabled) {
        // Create a FHIR client
        FhirContext fhirContext = FhirContext.forR4();
        IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");

        // register interceptors
        client.registerInterceptor(new LoggingInterceptor(false));

        PatientSearchInterceptor searchInterceptor = new PatientSearchInterceptor();
        client.registerInterceptor(searchInterceptor);

        if (!isCacheEnabled) {
            AdditionalRequestHeadersInterceptor interceptor = new AdditionalRequestHeadersInterceptor();
            interceptor.addHeaderValue("Cache-Control", "no-cache");
            client.registerInterceptor(interceptor);
        }

        // Search for Patient resources
        Bundle bundle = client
                .search()
                .forResource("Patient")
                .where(Patient.FAMILY.matches().value(lastName))
                .returnBundle(Bundle.class)
                .execute();

        List<IBaseResource> results = new ArrayList<>(BundleUtil.toListOfResources(fhirContext, bundle));
        List<Patient> patients = results.stream()
                .filter(obj -> obj instanceof Patient)
                .map(Patient.class::cast).collect(Collectors.toList());

        PatientSearchResults searchResults = new PatientSearchResults();
        searchResults.setPatients(patients);
        searchResults.setTimeInMillis(searchInterceptor.getTimeInMillis());
        return searchResults;
    }
}
