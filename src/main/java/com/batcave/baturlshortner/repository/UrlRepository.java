package com.batcave.baturlshortner.repository;

import com.batcave.baturlshortner.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlRepository extends JpaRepository<com.batcave.baturlshortner.model.Url, Long> {

    //findByShortUrl query for the main redirection api
    Url findByShortUrl(String shortUrl);

    //query to get all url instances in latest order
    public List<Url> findAllByOrderByTimestampDesc();

}
