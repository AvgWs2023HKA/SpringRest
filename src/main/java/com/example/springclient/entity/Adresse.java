package com.example.springclient.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class Adresse {
    private final String strasse;
    private final int hausummer;
    private final String postleitzahl;
    private final String stadt;
    private final String land;
    private final int leistung;
    private final String bundesland;

    public String toJson(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        }
        catch(JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }
}
