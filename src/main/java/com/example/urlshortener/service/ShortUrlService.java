package com.example.urlshortener.service;

import com.example.urlshortener.exception.CodeAlreadyExistsException;
import com.example.urlshortener.exception.ShortUrlNotFoundException;
import com.example.urlshortener.model.ShortUrl;
import com.example.urlshortener.repository.ShortUrlRepository;
import com.example.urlshortener.util.RandomStringGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortUrlService{

    private final RandomStringGenerator randomStringGenerator;
    private final ShortUrlRepository shortUrlRepository;

    public ShortUrlService(RandomStringGenerator randomStringGenerator,
                           ShortUrlRepository shortUrlRepository) {
        this.randomStringGenerator = randomStringGenerator;
        this.shortUrlRepository = shortUrlRepository;
    }

    public ShortUrl createShortUrl(ShortUrl shortUrl){
        if(shortUrl.getCode().isEmpty() || shortUrl.getCode() == null){
            shortUrl.setCode(generateCode());
        }else if(shortUrlRepository.findAllByCode(shortUrl.getCode()).isPresent()){
            throw new CodeAlreadyExistsException("Code already exists");
        }
        shortUrl.setCode(shortUrl.getCode().toUpperCase());
        return shortUrlRepository.save(shortUrl);
    }

    public List<ShortUrl> getAllShortUrls() {
        return shortUrlRepository.findAll();
    }

    public ShortUrl getUrlByCode(String code) {
        return shortUrlRepository.findAllByCode(code.toUpperCase()).orElseThrow(
                ()->new ShortUrlNotFoundException("Url not found !")
        );
    }

    private String generateCode(){
        String code;
        do {
            code = this.randomStringGenerator.generateRandomString();
        }while(shortUrlRepository.findAllByCode(code).isPresent());
        return code;
    }
}
