package com.example.urlshortener.request.converter;

import com.example.urlshortener.model.ShortUrl;
import com.example.urlshortener.request.ShortUrlRequest;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlRequestConverter {

    public ShortUrl convertToEntity(ShortUrlRequest shortUrlRequest){
        return ShortUrl.builder()
                .url(shortUrlRequest.getUrl())
                .code(shortUrlRequest.getCode()==null ? "":shortUrlRequest.getCode())
                .build();
    }

}
