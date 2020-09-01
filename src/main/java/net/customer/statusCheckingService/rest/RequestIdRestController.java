package net.customer.statusCheckingService.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.customer.exceptionHandler.CustomExceptionHandler;
import net.customer.statusCheckingService.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "checking the request status")
@Slf4j
@RestController
@RequestMapping("/request/check/")
public class RequestIdRestController {
    @Autowired
    Dao daoObject;

    @ApiOperation(value = "checking the request status by request id", response = HttpStatus.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HttpStatus> saveRequest(@PathVariable("id") Long requestId) {
        if (requestId == null) {
            log.info("request Id is null");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Message header", "request Id is null");
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        }

        if (daoObject.isNotContainRequestId(requestId)) {
            log.info("request Id is not present in request table");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Message header", "request Id is not present in request table");
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        }

        if (daoObject.isStatusFinite(requestId)) {
            HttpStatus httpStatus = HttpStatus.valueOf(daoObject.getStatusById(requestId));
            log.info("status of this id " +requestId+ " is finite");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Message header", "status of this id " +requestId+ "is finite");
            return new ResponseEntity<>(httpStatus, httpHeaders, httpStatus);
        }

        ResponseEntity<HttpStatus> responseEntity =
                new RestTemplateBuilder()
                        .errorHandler(new CustomExceptionHandler())
                        .build()
                        .getForEntity("http://localhost:8080/status", HttpStatus.class);

        daoObject.saveStatus(responseEntity.getBody(), requestId);

        return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getBody());
    }
    /*
    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<HttpStatus> handleHttpClientErrorException(Exception e, WebRequest request) {
        requestTable.setRequestStatus(HttpStatus.NOT_FOUND.value());
        requestRepository.save(requestTable);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND);
    } */
}
