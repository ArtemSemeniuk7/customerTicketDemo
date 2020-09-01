package net.customer.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import java.io.IOException;

@Slf4j
public class CustomExceptionHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {
        return (
                httpResponse.getStatusCode() == HttpStatus.NOT_FOUND
                        || httpResponse.getStatusCode() == HttpStatus.BAD_REQUEST);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {
        if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            log.info("Exception 404 handled");
            return;
        } else if (httpResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
            log.info("Exception 400 handled");
            return;
        }
    }
}
