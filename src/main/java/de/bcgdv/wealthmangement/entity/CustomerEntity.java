package de.bcgdv.wealthmangement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    private Integer id;

    private String email;

    private String dateOfBirth;

    private Integer riskLevel;

    private Integer retirementAge;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private StrategyEntity strategyEntity;
}
