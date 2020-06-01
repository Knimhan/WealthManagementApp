package de.bcgdv.wealthmangement.service;

import de.bcgdv.wealthmangement.Fixture;
import de.bcgdv.wealthmangement.entity.CustomerEntity;
import de.bcgdv.wealthmangement.exception.StrategyNotFoundException;
import de.bcgdv.wealthmangement.model.CustomerPortfolio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RebalancingServiceTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private FinancialPortfolioService financialPortfolioService;

    private RebalancingService classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new RebalancingService(customerService, financialPortfolioService);
    }

    @Test
    public void testRebalancing() {
        //given
        Integer customerId = 1;
        CustomerEntity customerEntity = Fixture.getCustomerEntity();

        //when
        when(customerService.get(customerId)).thenReturn(customerEntity);
        CustomerPortfolio customerPortfolio = classUnderTest.balancePortfolio(customerId);

        //then
        CustomerPortfolio expected = Fixture.getCustomerPortfolio();
        assertEquals(expected.getStocks(), customerPortfolio.getStocks());
        assertEquals(expected.getBonds(), customerPortfolio.getBonds());
        assertEquals(expected.getCash(), customerPortfolio.getCash());
    }


    @Test
    void testStrategyNotAssignedToCustomer() {
        //given
        Integer customerId = 1;
        CustomerEntity customerEntity = Fixture.getCustomerEntity();
        customerEntity.setStrategyEntity(null);
        when(customerService.get(customerId)).thenReturn(customerEntity);

        //when
        Throwable exception = assertThrows(StrategyNotFoundException.class,
                () -> classUnderTest.balancePortfolio(1));

        //then
        assertEquals("Strategy not assigned to customer", exception.getMessage());
    }
}
