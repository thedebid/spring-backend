package com.example.backend.dto;


import com.example.backend.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private Integer quantity;
    private Integer totalPrice;

    private Long productId;
    private String productName;

    private Long customerId;
    private String customerName;

    private Long supplierId;
    private String supplierName;

    private LocalDateTime orderDate;
    private Status status;
}
