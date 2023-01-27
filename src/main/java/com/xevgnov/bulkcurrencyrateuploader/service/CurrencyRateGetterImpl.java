package com.xevgnov.bulkcurrencyrateuploader.service;

import com.xevgnov.bulkcurrencyrateuploader.dto.CurrencyRateDto;
import com.xevgnov.bulkcurrencyrateuploader.exception.CurrencyRateGettingException;
import com.xevgnov.bulkcurrencyrateuploader.exception.NbpApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class CurrencyRateGetterImpl implements CurrencyRateGetter {

    private final WebClient webClient;

    public CurrencyRateGetterImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Retryable(
            include = {NbpApiException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000))
    @Override
    public CurrencyRateDto getCurrencyRate(String date, String currency) throws NbpApiException {

        String urlSuffix = String.format("exchangerates/rates/a/%s/%s?format=json",
                currency, date);
        log.info("URL suffix: {}", urlSuffix);

        CurrencyRateDto response = null;

        while (response == null) {
            try {
                response = getRateByDateAndCurrency(date, currency);
            } catch (Exception e) {
                if (e.getCause() instanceof CurrencyRateGettingException) {
                    CurrencyRateGettingException currencyRateGettingException = (CurrencyRateGettingException) e.getCause();
                    if (currencyRateGettingException.getMessage().contains("404")) {
                        date = getNextDate(date);
                        continue;
                    }
                }
                throw new NbpApiException("Failed to get data from", e);
            }
        }

        return response;
    }

    private CurrencyRateDto getRateByDateAndCurrency(String date, String currency) {
        String urlSuffix = String.format("exchangerates/rates/a/%s/%s?format=json",
                currency, date);
        log.info("URL suffix: {}", urlSuffix);

        CurrencyRateDto response = webClient
                .get()
                .uri(urlSuffix)
                .retrieve()
                .onStatus(HttpStatus::isError, errorResponse -> {
                    log.error("API returns unsuccessful response: {}", errorResponse.bodyToMono(ErrorAPIResponse.class));
                    return errorResponse.bodyToMono(String.class).map(CurrencyRateGettingException::new);
                })
                .bodyToMono(CurrencyRateDto.class)
                .block();

        log.info("response is: {}", response);
        return response;
    }

    private String getNextDate(String originalDate) {
        LocalDate date = LocalDate.parse(originalDate);
        date = date.plusDays(1);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nextDate = date.format(formatters);
        log.info("Nex date is {}", nextDate);
        return nextDate;
    }

}
