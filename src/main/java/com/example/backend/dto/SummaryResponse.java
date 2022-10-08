package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryResponse {

    private Optional<Integer> totalTransaction;
    private Optional<Integer> totalNewTransaction;

    private Optional<Integer> totalProducts;
    private Optional<Integer> totalNewProducts;

    private Optional<Integer> totalAmount;
    private Optional<Integer> totalNewAmount;

}
