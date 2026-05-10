package com.mb.ordersystem.service;

import com.mb.ordersystem.dto.response.CountryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final RestClient countryRestClient;

    public List<CountryResponse> findAll() {
        return countryRestClient.get()
                .uri("/countries")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}
