package com.mb.ordersystem.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
