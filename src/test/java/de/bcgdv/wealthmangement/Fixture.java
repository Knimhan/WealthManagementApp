package de.bcgdv.wealthmangement;

import de.bcgdv.wealthmangement.entity.CustomerEntity;
import de.bcgdv.wealthmangement.entity.StrategyEntity;
import de.bcgdv.wealthmangement.model.Customer;
import de.bcgdv.wealthmangement.model.CustomerPortfolio;
import de.bcgdv.wealthmangement.model.Strategy;

public class Fixture {
    public static CustomerEntity getCustomerEntity() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1);
        customerEntity.setEmail("bob@bob.com");
        customerEntity.setDateOfBirth("1969-04-29");
        customerEntity.setRiskLevel(3);
        customerEntity.setRetirementAge(65);
        customerEntity.setStrategyEntity(getStrategyEntity());
        return customerEntity;
    }

    public static StrategyEntity getStrategyEntity() {
        StrategyEntity strategyEntity = new StrategyEntity();
        strategyEntity.setId(3);
        strategyEntity.setMinRiskLevel(0);
        strategyEntity.setMaxRiskLevel(3);
        strategyEntity.setMinYearsToRetirement(10);
        strategyEntity.setMaxYearsToRetirement(20);
        strategyEntity.setStocksPercentage(10);
        strategyEntity.setBondsPercentage(70);
        strategyEntity.setCashPercentage(20);
        return strategyEntity;
    }

    public static StrategyEntity getDefaultCashStrategyEntity() {
        StrategyEntity strategyEntity = new StrategyEntity();
        strategyEntity.setId(1);
        strategyEntity.setMinRiskLevel(0);
        strategyEntity.setMaxRiskLevel(3);
        strategyEntity.setMinYearsToRetirement(20);
        strategyEntity.setMaxYearsToRetirement(30);
        strategyEntity.setStocksPercentage(0);
        strategyEntity.setBondsPercentage(0);
        strategyEntity.setCashPercentage(100);
        return strategyEntity;
    }

    public static CustomerPortfolio getCustomerPortfolio() {
        CustomerPortfolio customerPortfolio = new CustomerPortfolio();
        customerPortfolio.setCustomerId(1);
        customerPortfolio.setStocks(-5870);
        customerPortfolio.setBonds(4610);
        customerPortfolio.setCash(1260);
        return customerPortfolio;
    }

    public static Customer getCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setEmail("bob@bob.com");
        customer.setDateOfBirth("1969-04-29");
        customer.setRiskLevel(3);
        customer.setRetirementAge(65);
        return customer;
    }

    public static Strategy getStrategy() {
        Strategy strategy = new Strategy();
        strategy.setStrategyId(1);
        strategy.setMinRiskLevel(0);
        strategy.setMaxRiskLevel(3);
        strategy.setMinYearsToRetirement(20);
        strategy.setMaxYearsToRetirement(30);
        strategy.setStocksPercentage(0);
        strategy.setBondsPercentage(0);
        strategy.setCashPercentage(100);
        return strategy;
    }
}
