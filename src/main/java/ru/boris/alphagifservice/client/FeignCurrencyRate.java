package ru.boris.alphagifservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.boris.alphagifservice.model.Currency;

@FeignClient(name = "FeignCurrencyRate", url = "${openexchangerates.url}")
public interface FeignCurrencyRate {

    @GetMapping("/latest.json")
    Currency getLatestRates(
            @RequestParam("app_id") String appId,
            @RequestParam("base") String base
    );

    @GetMapping("/historical/{date}.json")
    Currency getHistoricalRates(
            @RequestParam("app_id") String appId,
            @RequestParam("base") String base,
            @PathVariable String date
    );
}
