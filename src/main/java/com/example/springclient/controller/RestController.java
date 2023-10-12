package com.example.springclient.controller;

import static org.springframework.http.ResponseEntity.badRequest;
import com.example.springclient.entity.Adresse;
import com.example.springclient.service.ConsumerService;
import com.example.springclient.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Controller Klasse.
 */
@Slf4j
@Controller
@RequestMapping("/rest/solarapi")
@RequiredArgsConstructor
public class RestController {
    private final ProducerService producerService;
    private final KafkaConnectionCheck checker;
    private final ConsumerService consumerService;

    /**
     * Methode, die Adresse aus Anfrage entgegennimmt.
     * @param adresse Adresse, die an den Message Broker gesendet werden soll.
     * @return Http Status.
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<String> post(@RequestBody Adresse adresse){
        log.debug("post: Adresse");
        if(adresse == null || !checker.isKafkaServerReachable()){
            log.error("post: Adresse ungültig oder Server nicht erreichbar!");
            return badRequest().build();
        }
        producerService.sendeNachricht(adresse.toJson());

        try{
            Thread.sleep(30000);
        }catch(InterruptedException e){
            log.error(e.getMessage());
        }

        if(consumerService.getEmpfangeneNachricht() == null){
            log.error("Timeout: Verarbeitung zu lange gedauert!");
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("Timeout beim Warten auf die Antwort");
        }

        return ResponseEntity.status(HttpStatus.OK).body(consumerService.getEmpfangeneNachricht());
    }
}
