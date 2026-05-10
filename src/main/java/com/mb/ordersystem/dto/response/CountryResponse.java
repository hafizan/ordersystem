package com.mb.ordersystem.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryResponse {

    private String name;
    private String alpha3Code;
}
