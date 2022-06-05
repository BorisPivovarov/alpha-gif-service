package ru.boris.alphagifservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import ru.boris.alphagifservice.client.FeignCurrencyRate;
import ru.boris.alphagifservice.model.Currency;
import ru.boris.alphagifservice.service.impl.RateServiceImpl;

import java.time.LocalDate;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:application.properties")
public class RateServiceTest {

    @Mock
    private FeignCurrencyRate feignCurrencyRate;

    @Value("${openexchangerates.app.id}")
    private String appId;

    @Value("${openexchangerates.base}")
    private String base;

    RateService currencyService;

    @BeforeEach
    public void init() {
        currencyService = new RateServiceImpl(feignCurrencyRate);
        ReflectionTestUtils.setField(currencyService, "appId", appId);
        ReflectionTestUtils.setField(currencyService, "base", base);
    }

    @Test
    public void isRich() {
        String code = "RUB";

        Currency todayRates = Currency.builder().rates(Map.of(code, 90.0)).build();
        Currency yesterdayRates = Currency.builder().rates(Map.of(code, 30.0)).build();

        Mockito.when(feignCurrencyRate.getLatestRates(appId, base)).thenReturn(todayRates);
        Mockito.when(feignCurrencyRate.getHistoricalRates(appId, base, String.valueOf(LocalDate.now().minusDays(1)))).thenReturn(yesterdayRates);

        Assertions.assertTrue(currencyService.isRich(code));
    }

    @Test
    public void notExists() {
        Currency todayRates = Currency.builder().rates(Map.of("RUB", 33.4)).build();

        Mockito.when(feignCurrencyRate.getLatestRates(appId, base)).thenReturn(todayRates);

        Assertions.assertNull(currencyService.isRich("Not_Existing"));
        verify(feignCurrencyRate, never()).getHistoricalRates(any(), any(), any());
    }
}
