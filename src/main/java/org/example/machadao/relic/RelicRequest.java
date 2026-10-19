package org.example.machadao.relic;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RelicRequest(
        @NotBlank @Size(max = 80) String code,
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Size(max = 500) String description,
        @NotNull @DecimalMin("0.0") @Digits(integer = 10, fraction = 2) BigDecimal illustrativeValueUsd
) {
}
