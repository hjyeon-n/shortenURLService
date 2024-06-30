package kr.co.shortenurlservice.domain;

import lombok.Getter;

import java.util.Random;

@Getter
public class ShortenUrl {
    private String originalUrl;
    private String shortenedUrlKey;
    private Long redirectCount;

    public ShortenUrl(String originalUrl, String shortenedUrlKey) {
        this.originalUrl = originalUrl;
        this.shortenedUrlKey = shortenedUrlKey;
        this.redirectCount = 0L;
    }

    public static String generateShortenUrlKey() {
        String base56Characters = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
        Random random = new Random();
        StringBuilder shortenUrlKey = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int base56ChactersIndex = random.nextInt(0, base56Characters.length());
            char base56Character = base56Characters.charAt(base56ChactersIndex);
            shortenUrlKey.append(base56Character);
        }

        return shortenUrlKey.toString();
    }

    public void increaseRedirectCount() {
        this.redirectCount++;
    }
}
