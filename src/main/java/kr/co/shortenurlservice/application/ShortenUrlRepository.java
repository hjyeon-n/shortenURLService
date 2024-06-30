package kr.co.shortenurlservice.application;

import kr.co.shortenurlservice.domain.ShortenUrl;

public interface ShortenUrlRepository {
    void saveShortenUrl(ShortenUrl shortenUrl);
    ShortenUrl findShortenUrlByShortenUrlKey(String shortenUrlKey);
}
