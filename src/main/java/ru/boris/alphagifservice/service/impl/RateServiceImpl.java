package ru.boris.alphagifservice.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.boris.alphagifservice.client.FeignCurrencyRate;
import ru.boris.alphagifservice.model.Currency;
import ru.boris.alphagifservice.service.RateService;


import java.time.LocalDate;

@Service
public class RateServiceImpl implements RateService {

    private final FeignCurrencyRate feignCurrencyRate;

    @Value("${openexchangerates.app.id}")
    private String appId;

    @Value("${openexchangerates.base}")
    private String base;

    public RateServiceImpl(FeignCurrencyRate feignCurrencyRate) {
        this.feignCurrencyRate = feignCurrencyRate;
    }

    @Override
    public Boolean isRich(String code) {
        Currency exchangeRates = feignCurrencyRate.getLatestRates(appId, base);

        if (!exchangeRates.getRates().containsKey(code)) {
            return null;
        }

        Currency yesterdayRates = feignCurrencyRate.getHistoricalRates(appId, base, String.valueOf(LocalDate.now().minusDays(1)));

        return yesterdayRates.getRates().get(code) < exchangeRates.getRates().get(code);
    }
}
