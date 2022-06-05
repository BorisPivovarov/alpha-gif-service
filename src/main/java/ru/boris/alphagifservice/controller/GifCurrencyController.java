package ru.boris.alphagifservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.boris.alphagifservice.service.GifService;


@RestController
@RequestMapping(value = "/api/currency")
public class GifCurrencyController {

    private final GifService gifService;

    public GifCurrencyController(GifService gifService) {
        this.gifService = gifService;
    }

    @GetMapping("/gif/{code}")
    public ResponseEntity<Object> getChangeGif(@PathVariable String code) {
        Object gifResponse = gifService.getGif(code);

        if (gifResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Воу, таких валют мы не видели = " + code);
        }

        return ResponseEntity.ok(gifResponse);
    }
}
