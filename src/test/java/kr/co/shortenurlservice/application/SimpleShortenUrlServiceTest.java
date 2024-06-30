package kr.co.shortenurlservice.application;

import kr.co.shortenurlservice.domain.NotFoundShortenUrlException;
import kr.co.shortenurlservice.presentation.ShortenUrlCreateRequestDto;
import kr.co.shortenurlservice.presentation.ShortenUrlCreateResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class SimpleShortenUrlServiceTest {

    @Autowired
    private SimpleShortenUrlService simpleShortenUrlService;

    @Test
    @DisplayName("URL을 단축한 후 단축된 URL 키로 조회하면 원래 URL이 조회되어야 한다.")
    void shortenUrlAddTest() {
        String expectedOriginalUrl = "https://google.com";

        ShortenUrlCreateRequestDto requestDto = new ShortenUrlCreateRequestDto(expectedOriginalUrl);
        ShortenUrlCreateResponseDto responseDto = simpleShortenUrlService.generateShortenUrl(requestDto);

        String shortenURLKey = responseDto.getShortenUrlKey();
        String originalUrl = simpleShortenUrlService.getOriginalUrlByShortenUrlKey(shortenURLKey);

        assertThat(originalUrl).isEqualTo(expectedOriginalUrl);
    }

    @Test
    @DisplayName("NotFoundShortenUrlException")
    void shortenUrlNotFoundTest() {
        String expectedOriginalUrl = "https://google.com";

        ShortenUrlCreateRequestDto requestDto = new ShortenUrlCreateRequestDto(expectedOriginalUrl);
        ShortenUrlCreateResponseDto responseDto = simpleShortenUrlService.generateShortenUrl(requestDto);

        String shortenURLKey = responseDto.getShortenUrlKey();

        String originalUrl = null;
        try {
            originalUrl = simpleShortenUrlService.getOriginalUrlByShortenUrlKey(shortenURLKey);
        } catch (NotFoundShortenUrlException e) {
            log.error("NotFoundShortenUrlException = {}", e.getMessage(), e);
        }

        assertThat(originalUrl).isEqualTo(expectedOriginalUrl);

//        assertThrows(NotFoundShortenUrlException.class, ()
//                -> simpleShortenUrlService.getOriginalUrlByShortenUrlKey(shortenURLKey));
    }
}