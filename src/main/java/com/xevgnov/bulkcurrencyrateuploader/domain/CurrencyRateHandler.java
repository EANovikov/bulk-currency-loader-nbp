package com.xevgnov.bulkcurrencyrateuploader.domain;

import com.xevgnov.bulkcurrencyrateuploader.exception.NbpApiException;

import java.io.IOException;

public interface CurrencyRateHandler {

    void handle() throws IOException, NbpApiException;
}
