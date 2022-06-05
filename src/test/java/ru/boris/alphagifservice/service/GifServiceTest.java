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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import ru.boris.alphagifservice.client.FeignGif;
import ru.boris.alphagifservice.service.impl.GifServiceImpl;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:application.properties")
public class GifServiceTest {

    @Mock
    private RateService rateService;

    @Mock
    private FeignGif feignGif;

    @Value("${giphy.api.key}")
    private String giphyKey;

    @Value("${giphy.rich}")
    private String richTag;

    @Value("${giphy.broke}")
    private String brokeTag;

    GifService gifService;

    @BeforeEach
    public void init() {
        gifService = new GifServiceImpl(rateService, feignGif);
        ReflectionTestUtils.setField(gifService, "giphyKey", giphyKey);
        ReflectionTestUtils.setField(gifService, "richTag", richTag);
        ReflectionTestUtils.setField(gifService, "brokeTag", brokeTag);
    }

    @Test
    public void rich() {
        String code = "RUB";
        ResponseEntity<Object> gif = new ResponseEntity<>(HttpStatus.OK);

        Mockito.when(rateService.isRich(code.toUpperCase())).thenReturn(true);
        Mockito.when(feignGif.getRandomGif(giphyKey, richTag)).thenReturn(gif);

        Assertions.assertEquals(gif.getBody(), gifService.getGif(code));
        verify(feignGif).getRandomGif(giphyKey, richTag);
        verify(feignGif, never()).getRandomGif(giphyKey, brokeTag);
    }
}
