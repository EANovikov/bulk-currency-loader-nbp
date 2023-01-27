package com.xevgnov.bulkcurrencyrateuploader.service;

import com.xevgnov.bulkcurrencyrateuploader.dto.CurrencyRateDto;
import com.xevgnov.bulkcurrencyrateuploader.exception.NbpApiException;
import org.springframework.stereotype.Service;

public interface CurrencyRateGetter {

    CurrencyRateDto getCurrencyRate(String date, String currency) throws NbpApiException;
}
