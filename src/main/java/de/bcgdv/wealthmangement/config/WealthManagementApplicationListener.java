package de.bcgdv.wealthmangement.config;

import de.bcgdv.wealthmangement.model.Customer;
import de.bcgdv.wealthmangement.model.Strategy;
import de.bcgdv.wealthmangement.util.CSVReader;
import de.bcgdv.wealthmangement.service.CustomerService;
import de.bcgdv.wealthmangement.service.StrategyService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WealthManagementApplicationListener
        implements ApplicationListener<ContextRefreshedEvent> {

    public static final String CUSTOMERS_CSV = "/customers.csv";
    public static final String STRATEGY_CSV = "/strategy.csv";


    private final CustomerService customerService;

    private final StrategyService strategyService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        customerService.saveAll(CSVReader.readObjects(CUSTOMERS_CSV,Customer.class));
        strategyService.saveAll(CSVReader.readObjects(STRATEGY_CSV,Strategy.class));
    }
}
