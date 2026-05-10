package com.mb.ordersystem.controller;

import com.mb.ordersystem.common.ApiResponse;
import com.mb.ordersystem.dto.response.CountryResponse;
import com.mb.ordersystem.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RequiredArgsConstructor
@RestController
@RequestMapping("/countries")
public class CountryEndpointController {

    private final CountryService countryService;

    @GetMapping
    public ApiResponse<List<CountryResponse>> findAll() {
        return ApiResponse.ok(countryService.findAll());
    }
}
