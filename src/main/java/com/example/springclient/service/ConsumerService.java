package com.example.springclient.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Getter
public class ConsumerService {

    private String empfangeneNachricht;

    @KafkaListener(topics = "solarapi", groupId = "group")
    public void nachrichtEmpfangen(String nachricht){
        log.debug("Ergebnis: {}", nachricht);
        empfangeneNachricht = nachricht;
    }
}
