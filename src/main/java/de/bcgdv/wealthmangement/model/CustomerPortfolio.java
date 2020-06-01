package de.bcgdv.wealthmangement.model;

import lombok.Builder;
import lombok.Data;

@Data
public class CustomerPortfolio {
    private Integer customerId;

    private Integer stocks;

    private Integer bonds;

    private Integer cash;
}
