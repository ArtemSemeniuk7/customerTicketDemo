package net.customer.statusRandomGenerateService.rest;

import io.swagger.annotations.Api;
import net.customer.statusRandomGenerateService.statusRandomGenerator.StatusGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "return random status")
@RestController
@RequestMapping("/status")
public class StatusRestController {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HttpStatus> getRandomStatus() {
        HttpStatus httpStatus = StatusGenerator.genetateStatus();
        return new ResponseEntity<>(httpStatus, HttpStatus.OK);
    }
}
