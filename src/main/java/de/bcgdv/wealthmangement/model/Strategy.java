package de.bcgdv.wealthmangement.model;


import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class Strategy {

    @CsvBindByName
    private int strategyId;

    @CsvBindByName
    private int minRiskLevel;

    @CsvBindByName
    private int maxRiskLevel;

    @CsvBindByName
    private int minYearsToRetirement;

    @CsvBindByName
    private int maxYearsToRetirement;

    @CsvBindByName
    private int stocksPercentage;

    @CsvBindByName
    private int cashPercentage;

    @CsvBindByName
    private int bondsPercentage;
}
