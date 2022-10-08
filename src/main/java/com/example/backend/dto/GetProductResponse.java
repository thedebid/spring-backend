package com.example.backend.dto;

import java.time.LocalDateTime;

public interface GetProductResponse {

    Long getId();

    String getName();

    int getStock();

    int getPrice();

    String getImage();

    Long getSupplierId();

    String getSupplierName();

    LocalDateTime getCreated_At();

    LocalDateTime getUpdated_At();


}
