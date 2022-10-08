package com.example.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderRequest {
    private Integer quantity;
    private Integer totalPrice;
    private Long productId;
}
