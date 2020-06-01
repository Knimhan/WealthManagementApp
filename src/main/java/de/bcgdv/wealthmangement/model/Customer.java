package de.bcgdv.wealthmangement.model;


import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Customer {
    @CsvBindByName
    private int customerId;

    @CsvBindByName
    private String email;

    @CsvBindByName
    private String dateOfBirth;

    @CsvBindByName
    private int riskLevel;

    @CsvBindByName
    private int retirementAge;
}
