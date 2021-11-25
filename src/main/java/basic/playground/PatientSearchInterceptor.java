package basic.playground;

import ca.uhn.fhir.rest.client.api.IClientInterceptor;
import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;

import java.io.IOException;

public class PatientSearchInterceptor implements IClientInterceptor {

    private long timeInMillis;

    @Override
    public void interceptRequest(IHttpRequest iHttpRequest) {

    }

    @Override
    public void interceptResponse(IHttpResponse iHttpResponse) throws IOException {
        timeInMillis = iHttpResponse.getRequestStopWatch().getMillis();
    }

    public long getTimeInMillis(){
        return timeInMillis;
    }
}
