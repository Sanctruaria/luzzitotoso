package org.example.machadao.relic;

import java.math.BigDecimal;

public record RelicResponse(
        Long id,
        String code,
        String name,
        String description,
        BigDecimal illustrativeValueUsd,
        boolean fictional
) {

    public static RelicResponse from(Relic relic) {
        return new RelicResponse(
                relic.getId(),
                relic.getCode(),
                relic.getName(),
                relic.getDescription(),
                relic.getIllustrativeValueUsd(),
                true
        );
    }
}
