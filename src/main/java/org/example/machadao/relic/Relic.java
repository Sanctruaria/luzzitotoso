package org.example.machadao.relic;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.math.BigDecimal;

@Entity
@Table(name = "fictional_relics", uniqueConstraints = {
        @UniqueConstraint(name = "uk_fictional_relics_code", columnNames = "code")
})
public class Relic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String code;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(name = "illustrative_value_usd", nullable = false, precision = 12, scale = 2)
    private BigDecimal illustrativeValueUsd;

    protected Relic() {
    }

    public Relic(String code, String name, String description, BigDecimal illustrativeValueUsd) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.illustrativeValueUsd = illustrativeValueUsd;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getIllustrativeValueUsd() {
        return illustrativeValueUsd;
    }

    public void setIllustrativeValueUsd(BigDecimal illustrativeValueUsd) {
        this.illustrativeValueUsd = illustrativeValueUsd;
    }
}
