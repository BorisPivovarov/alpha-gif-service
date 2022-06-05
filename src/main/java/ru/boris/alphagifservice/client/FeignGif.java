package ru.boris.alphagifservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "FeignGif", url = "${giphy.url}")
public interface FeignGif {
    @GetMapping("/random")
    ResponseEntity<Object> getRandomGif(
            @RequestParam("api_key") String apiKey,
            @RequestParam("tag") String tag
    );
}
