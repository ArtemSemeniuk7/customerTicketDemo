package net.customer.statusRandomGenerateService.statusRandomGenerator;

import org.springframework.http.HttpStatus;
import java.util.Random;

public class StatusGenerator {
    public static HttpStatus genetateStatus() {
        HttpStatus[] randomStatus = {
                HttpStatus.TEMPORARY_REDIRECT,
                HttpStatus.NOT_FOUND,
                HttpStatus.OK};
        return randomStatus[new Random().nextInt(3)];
    }
}

