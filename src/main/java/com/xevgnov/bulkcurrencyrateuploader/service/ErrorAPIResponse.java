package com.xevgnov.bulkcurrencyrateuploader.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorAPIResponse {
    private String status;
    private String error;
}
