package com.xevgnov.bulkcurrencyrateuploader;

import com.xevgnov.bulkcurrencyrateuploader.domain.CurrencyRateHandler;
import com.xevgnov.bulkcurrencyrateuploader.exception.NbpApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@Slf4j
@SpringBootApplication
public class BulkCurrencyRateUploaderApplication implements CommandLineRunner {

    private final CurrencyRateHandler currencyRateHandler;

    public BulkCurrencyRateUploaderApplication(CurrencyRateHandler currencyRateHandler) {
        this.currencyRateHandler = currencyRateHandler;
    }

    public static void main(String[] args) {
        log.info("***Starting***");
        SpringApplication.run(BulkCurrencyRateUploaderApplication.class, args);
        log.info("***Finishing***");
    }

    @Override
    public void run(String... args) throws IOException, NbpApiException {
        currencyRateHandler.handle();
    }
}
