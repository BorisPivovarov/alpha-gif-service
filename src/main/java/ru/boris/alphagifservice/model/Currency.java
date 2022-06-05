package ru.boris.alphagifservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Currency {
    String disclaimer;
    String license;
    Integer timestamp;
    String base;
    private Map<String, Double> rates;
}
