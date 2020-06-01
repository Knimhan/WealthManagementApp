package de.bcgdv.wealthmangement.service;

import de.bcgdv.wealthmangement.model.CustomerPortfolio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FinancialPortfolioService {

    public CustomerPortfolio get() {
        CustomerPortfolio customerPortfolio = new CustomerPortfolio();
        customerPortfolio.setCustomerId(1);
        customerPortfolio.setStocks(6700);
        customerPortfolio.setBonds(1200);
        customerPortfolio.setCash(400);
        return customerPortfolio;
    }

    public void post(List<CustomerPortfolio> customerPortfolios, PageRequest pageRequest) {
        for (CustomerPortfolio customerPortfolio : customerPortfolios) {
            System.out.println("Updating customer portfolio for customer " + customerPortfolio.getCustomerId() +
                    " Stocks: " + customerPortfolio.getStocks() +
                    " Bonds: " + customerPortfolio.getBonds() +
                    " Cash: " + customerPortfolio.getCash());
        }
    }
}
