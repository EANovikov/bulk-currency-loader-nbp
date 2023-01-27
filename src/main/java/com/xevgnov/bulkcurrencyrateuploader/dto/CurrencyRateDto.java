package com.xevgnov.bulkcurrencyrateuploader.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize
public class CurrencyRateDto {

    private String table;
    private String currency;
    private String code;
    private List<Rate> rates;

}
