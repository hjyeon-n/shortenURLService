package kr.co.shortenurlservice.application;

import kr.co.shortenurlservice.domain.LackOfShortenUrlKeyException;
import kr.co.shortenurlservice.domain.NotFoundShortenUrlException;
import kr.co.shortenurlservice.domain.ShortenUrl;
import kr.co.shortenurlservice.presentation.ShortenUrlCreateRequestDto;
import kr.co.shortenurlservice.presentation.ShortenUrlCreateResponseDto;
import kr.co.shortenurlservice.presentation.ShortenUrlInformationDto;
import org.springframework.stereotype.Service;

@Service
public class SimpleShortenUrlService {

    private ShortenUrlRepository shortenUrlRepostitory;

    public SimpleShortenUrlService() {
    }

    public SimpleShortenUrlService(ShortenUrlRepository shortenUrlRepostitory) {
        this.shortenUrlRepostitory = shortenUrlRepostitory;
    }

    public ShortenUrlCreateResponseDto generateShortenUrl(ShortenUrlCreateRequestDto requestDto) {
        String originalUrl = requestDto.getOriginalUrl();
        String shortenUrlKey = "";

        shortenUrlKey = getUniqueShortenUrlKey();

        ShortenUrl shortenUrl = new ShortenUrl(originalUrl, shortenUrlKey);
        shortenUrlRepostitory.saveShortenUrl(shortenUrl);

        ShortenUrlCreateResponseDto responseDto = new ShortenUrlCreateResponseDto(shortenUrl);
        return responseDto;
    }

    private String getUniqueShortenUrlKey() {
        final int MAX_RETRY_COUNT = 5;
        int count = 0;

        while (count++ < MAX_RETRY_COUNT) {
            String shortenUrlKey = ShortenUrl.generateShortenUrlKey();
            ShortenUrl shortenUrl = shortenUrlRepostitory.findShortenUrlByShortenUrlKey(shortenUrlKey);

            // 조회 시 값이 나오지 않음 -> 중복이 발셍되지 않음
            if (shortenUrl == null) {
                return shortenUrlKey;
            }
        }

        throw new LackOfShortenUrlKeyException();
    }


    public ShortenUrlInformationDto getShortenUrlInfoByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepostitory.findShortenUrlByShortenUrlKey(shortenUrlKey);

        if (shortenUrl == null) {
            throw new NotFoundShortenUrlException();
        }

        ShortenUrlInformationDto informationDto = new ShortenUrlInformationDto(shortenUrl);

        return informationDto;
    }

    public String getOriginalUrlByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepostitory.findShortenUrlByShortenUrlKey(shortenUrlKey);

        if (shortenUrl == null) {
            throw new NotFoundShortenUrlException();
        }

        shortenUrl.increaseRedirectCount();
        shortenUrlRepostitory.saveShortenUrl(shortenUrl);

        String originalUrl = shortenUrl.getOriginalUrl();

        return originalUrl;
    }
}
