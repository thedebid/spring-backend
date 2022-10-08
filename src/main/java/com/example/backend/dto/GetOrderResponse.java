package com.example.backend.dto;

import com.example.backend.enums.Status;

import java.time.LocalDateTime;

public interface GetOrderResponse {
    Long getId();
    Integer getQuantity();
    Integer getTotalPrice();
    Long getProductId();
    String getProductName();
    Integer getProductPrice();
    Long getCustomerId();
    String getCustomerName();
    Long getSupplierId();
    String getSupplierName();
    LocalDateTime getOrderDate();

    Status getStatus();

}
