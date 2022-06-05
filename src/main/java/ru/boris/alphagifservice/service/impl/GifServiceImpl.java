package ru.boris.alphagifservice.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.boris.alphagifservice.client.FeignGif;
import ru.boris.alphagifservice.service.GifService;
import ru.boris.alphagifservice.service.RateService;


@Service
public class GifServiceImpl implements GifService {

    private final RateService rateService;

    private final FeignGif feignGif;

    @Value("${giphy.api.key}")
    private String giphyKey;

    @Value("${giphy.rich}")
    private String richTag;

    @Value("${giphy.broke}")
    private String brokeTag;


    public GifServiceImpl(RateService rateService, FeignGif feignGif) {
        this.rateService = rateService;
        this.feignGif = feignGif;
    }

    @Override
    public Object getGif(String code) {

        Boolean rateServiceRich = rateService.isRich(code.toUpperCase());

        if (rateServiceRich == null) {
            return null;
        }

        return rateServiceRich ? feignGif.getRandomGif(giphyKey, richTag).getBody()
                : feignGif.getRandomGif(giphyKey, brokeTag).getBody();
    }
}
