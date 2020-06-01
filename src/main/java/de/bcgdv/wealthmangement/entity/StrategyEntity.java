package de.bcgdv.wealthmangement.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "strategy")
public class StrategyEntity {
    @Id
    private Integer id;

    private Integer minRiskLevel;

    private Integer maxRiskLevel;

    private Integer minYearsToRetirement;

    private Integer maxYearsToRetirement;

    private Integer stocksPercentage;

    private Integer cashPercentage;

    private Integer bondsPercentage;
}
