package net.customer.cronJob;

import lombok.extern.slf4j.Slf4j;
import net.customer.exceptionHandler.CustomExceptionHandler;
import net.customer.model.RequestTable;
import net.customer.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class CronJob {
    @Autowired
    private RequestRepository requestRepository;
    private static AtomicBoolean isRequstsNotFinite = new AtomicBoolean(true);

    @Async
    @Scheduled(fixedRate = 10000)
    public void scheduleTaskAsync() {
        log.info("Cron job task began");

        Long id = getUnfiniteStatusId();

        if (isRequstsNotFinite.get()) {
            new RestTemplateBuilder()
                    .errorHandler(new CustomExceptionHandler())
                    .build()
                    .getForEntity("http://localhost:8080/request/check/" +
                            id, HttpStatus.class);
            log.info("Cron job task ended");
        }
    }

    public Long getUnfiniteStatusId() {
        try {

            RequestTable requestTable = requestRepository.findProject();
            isRequstsNotFinite.set(true);

            return requestTable.getRequestId();

        } catch (NullPointerException e) {
            isRequstsNotFinite.set(false);
            log.info("All statuses are finite " + e);
            return 0l;
        }
    }
}
