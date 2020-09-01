package net.customer.statusCheckingService.dao;

import lombok.extern.slf4j.Slf4j;
import net.customer.model.RequestTable;
import net.customer.repository.RequestRepository;
import net.customer.ticketPaymentService.rest.RequestRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Repository
public class Dao {
    @Autowired
    private RequestRepository requestRepository;
    private static ReentrantLock lock = new ReentrantLock();
    
    public Boolean isStatusFinite(Long requestId) {
        RequestTable requestTable = requestRepository.findOne(requestId);

        int requestStatus = requestTable.getRequestStatus();
            if(requestStatus == 0 || requestStatus == 307) {
                return false;
            }
        return true;
    }

    public void saveStatus(HttpStatus httpStatus, Long requestId) {
        RequestTable requestTable = requestRepository.findOne(requestId);

        lock.lock();

        if (httpStatus.value() == 307)
            RequestRestController.threadLocal.set("in progress");
        else
            RequestRestController.threadLocal.set("finished");

        requestTable.setRequestStatus(httpStatus.value());
        requestTable.setExecutionStatus(RequestRestController.threadLocal.get());
        requestRepository.save(requestTable);

        log.info("request " + requestId + " is finished");

        lock.unlock();
    }

    public Integer getStatusById(Long requestId) {
        RequestTable requestTable = requestRepository.
                findOne(requestId);

        return requestTable.getRequestStatus();
    }

    public Boolean isNotContainRequestId(Long requestId) {
        RequestTable requestTable = requestRepository.findOne(requestId);

        if (requestTable == null)
            return true;

        return false;
    }
}
