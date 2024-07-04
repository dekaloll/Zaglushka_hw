package com.example.NewMock.Сontroller;

import com.example.NewMock.Model.RequestDTO;
import com.example.NewMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Random;

@RestController

public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO) {

        try {
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            BigDecimal maxlimit;
            String currenc;
            String RqUID = requestDTO.getRqUID();

            if (firstDigit == '8') {
                maxlimit = new BigDecimal(2000);
            } else if (firstDigit == '9') {
                maxlimit = new BigDecimal(1000);
            } else {
                maxlimit = new BigDecimal(10000);
            }

            if (firstDigit == '8') {
                currenc = "US";
            } else if (firstDigit == '9') {
                currenc = "EU";
            } else {
                currenc = "RUB";
            }

            double random = Math.random();

            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRqUID(RqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currenc);
            responseDTO.setBalance(String.valueOf(Math.round(random*maxlimit.doubleValue())));
            responseDTO.setMaxLimit(String.valueOf(maxlimit));

            log.error("******* RequestDTO *******" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("******* ResponseDTO *******" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));
            return responseDTO;


        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
