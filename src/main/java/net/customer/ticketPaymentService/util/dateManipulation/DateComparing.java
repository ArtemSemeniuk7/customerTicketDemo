package net.customer.ticketPaymentService.util.dateManipulation;

import lombok.extern.slf4j.Slf4j;
import net.customer.model.RequestTable;
import net.customer.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class DateComparing {
    @Autowired
    private RequestRepository requestRepository;
    static ReentrantLock lock = new ReentrantLock();
    public List<RequestTable> findFutureRequests(Long clientId) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date(System.currentTimeMillis());
        formatter.format(currentDate);

        lock.lock();
        List<RequestTable> futureRequestsList = StreamSupport
                .stream(requestRepository.findAll().spliterator(), false)
                .filter(RequestTable -> RequestTable.getClientId() == clientId)
                .filter(RequestTable -> RequestTable.getDate().after(currentDate))
                .sorted((RequestTable table1, RequestTable table2)
                        -> table2.getDate().compareTo(table1.getDate()))
                .collect(Collectors.toList());
        lock.unlock();

        log.info("future requests returned");

        return futureRequestsList;
    }
}
