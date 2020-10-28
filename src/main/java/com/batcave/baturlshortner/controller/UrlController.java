package com.batcave.baturlshortner.controller;

import com.batcave.baturlshortner.exception.ResourceNotFoundException;
import com.batcave.baturlshortner.model.Url;
import com.batcave.baturlshortner.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "https://baturls.netlify.app/")
public class UrlController {

    @Autowired
    private UrlRepository urlRepository;


    //create new url instance api
    @PostMapping("url")
    public Url createUrl(@RequestBody Url url){
        //System.out.println("Hello" + url.getMainUrl());
        url.setTimestamp();
        url.setShortUrl();
        return this.urlRepository.save(url);
    }

    //the main redirection api
    @GetMapping("{shortUrl}")
    public ResponseEntity<Url> getUrlByShortUrl(@PathVariable(value = "shortUrl") String shortUrl)
            throws ResourceNotFoundException {
        Url url = urlRepository.findByShortUrl(shortUrl);

        if (url == null) throw new ResourceNotFoundException("Url not found in Database :: " + shortUrl);

        //System.out.println("Console Check :: " + url.getMainUrl());

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url.getMainUrl()))
                .build();
        //return ResponseEntity.ok().body(url);
    }


    //get recent url instances
    @GetMapping("urls")
    public List<Url> getAllUrls(){
        return this.urlRepository.findAllByOrderByTimestampDesc();
    }


}
