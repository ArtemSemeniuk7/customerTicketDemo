package net.customer.ticketPaymentService.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.customer.model.RequestTable;
import net.customer.repository.RequestRepository;
import net.customer.ticketPaymentService.util.dateManipulation.DateComparing;
import net.customer.ticketPaymentService.util.idRandomGenerator.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Api(value = "request for tickets payment")
@Slf4j
@RestController
@RequestMapping("/request")
public class RequestRestController {
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private DateComparing dateComparing;
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private static ReentrantLock lock = new ReentrantLock();

    @ApiOperation(value = "save request object and get request id", response = Long.class)
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Long> saveRequest(@RequestBody @Valid RequestTable requestTable) {
        if (requestTable == null) {
            HttpHeaders httpHeaders =  new HttpHeaders();
            httpHeaders.set("Message header", "RequestObject is not valid");
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        }

        threadLocal.set("started");
        requestTable.setExecutionStatus(threadLocal.get());
        requestTable.setRequestId(IdGenerator.generateUniqueId().get());
        lock.lock();
        this.requestRepository.save(requestTable);
        lock.unlock();

        log.info(requestTable.getRequestId() + " is saved ");

        return new ResponseEntity<>(requestTable.getRequestId(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "get future request list for concrete client", response = List.class)
    @RequestMapping(value = "/get-future-data/{clientId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<RequestTable>> getFutureRequests(@PathVariable Long clientId) {

          List<RequestTable> futureRequestsList = dateComparing.findFutureRequests(clientId);

          if (futureRequestsList.isEmpty()) {
              HttpHeaders httpHeaders =  new HttpHeaders();
              httpHeaders.set("Message header", "There is no such client");
              return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
          }

        log.info(" list is returned ");

        return new ResponseEntity<>(futureRequestsList, HttpStatus.OK);
    }
}
