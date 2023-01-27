package com.xevgnov.bulkcurrencyrateuploader.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize
public class Rate {

    private String no;
    private String effectiveDate;
    private String mid;

}
