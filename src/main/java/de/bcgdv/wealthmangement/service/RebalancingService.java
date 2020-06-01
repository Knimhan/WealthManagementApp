package de.bcgdv.wealthmangement.service;

import de.bcgdv.wealthmangement.entity.CustomerEntity;
import de.bcgdv.wealthmangement.entity.StrategyEntity;
import de.bcgdv.wealthmangement.exception.StrategyNotFoundException;
import de.bcgdv.wealthmangement.model.CustomerPortfolio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class RebalancingService {

    private final CustomerService customerService;

    private final FinancialPortfolioService financialPortfolioService;

    public CustomerPortfolio balancePortfolio(Integer customerId) {
        CustomerEntity customerEntity = customerService.get(customerId);
        CustomerPortfolio customerPortfolio = financialPortfolioService.get();
        StrategyEntity strategyEntity = validateCurrentStrategy(customerEntity);
        CustomerPortfolio newCustomerPortfolio = getCustomerPortfolio(customerId, customerPortfolio, strategyEntity);
        financialPortfolioService.post(Collections.singletonList(newCustomerPortfolio), PageRequest.of(1,10));
        return newCustomerPortfolio;
    }

    private CustomerPortfolio getCustomerPortfolio(Integer customerId, CustomerPortfolio customerPortfolio, StrategyEntity strategyEntity) {
        CustomerPortfolio newCustomerPortfolio = new CustomerPortfolio();
        newCustomerPortfolio.setCustomerId(customerId);
        newCustomerPortfolio.setStocks(
                getRebalancingValue(
                        strategyEntity.getStocksPercentage(),
                        customerPortfolio.getStocks(),
                        getTotalAssets(customerPortfolio)
                )
        );
        newCustomerPortfolio.setBonds(
                getRebalancingValue(
                        strategyEntity.getBondsPercentage(),
                        customerPortfolio.getBonds(),
                        getTotalAssets(customerPortfolio)
                )
        );
        newCustomerPortfolio.setCash(
                getRebalancingValue(
                        strategyEntity.getCashPercentage(),
                        customerPortfolio.getCash(),
                        getTotalAssets(customerPortfolio)
                )
        );
        return newCustomerPortfolio;
    }

    private StrategyEntity validateCurrentStrategy(CustomerEntity customerEntity) {
        StrategyEntity strategyEntity = customerEntity.getStrategyEntity();
        if (strategyEntity == null)
            throw new StrategyNotFoundException("Strategy not assigned to customer");
        return strategyEntity;
    }

    private Integer getRebalancingValue(Integer expectedPercent, Integer customerValue, Integer totalAssets) {
        return calculateExpectedValue(expectedPercent,totalAssets)- customerValue;
    }

    private Integer calculateExpectedValue(Integer expectedPercentageOfCash, Integer totalAssets) {
        return (totalAssets * expectedPercentageOfCash) / 100;
    }

    private Integer getTotalAssets(CustomerPortfolio customerPortfolio) {
        return customerPortfolio.getStocks() + customerPortfolio.getBonds() + customerPortfolio.getCash();
    }
}
