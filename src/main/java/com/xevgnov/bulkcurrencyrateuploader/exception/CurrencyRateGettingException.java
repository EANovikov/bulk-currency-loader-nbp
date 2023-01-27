package com.xevgnov.bulkcurrencyrateuploader.exception;

public class CurrencyRateGettingException extends Exception {

    public CurrencyRateGettingException() {
    }

    public CurrencyRateGettingException(String message) {
        super(message);
    }

    public CurrencyRateGettingException(String message, Throwable cause) {
        super(message, cause);
    }
}
