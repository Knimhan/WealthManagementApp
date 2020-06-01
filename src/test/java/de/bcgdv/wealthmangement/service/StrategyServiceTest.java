package de.bcgdv.wealthmangement.service;

import de.bcgdv.wealthmangement.Fixture;
import de.bcgdv.wealthmangement.entity.StrategyEntity;
import de.bcgdv.wealthmangement.exception.StrategyNotFoundException;
import de.bcgdv.wealthmangement.model.Strategy;
import de.bcgdv.wealthmangement.repository.StrategyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class StrategyServiceTest {

    @Autowired
    private StrategyRepository strategyRepository;

    private StrategyService classUnderTest;

    @Autowired
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        classUnderTest = new StrategyService(strategyRepository,customerService);
    }

    @Test
    public void testSaveStrategy() {
        //given
        Strategy strategy = Fixture.getStrategy();

        //when
        StrategyEntity strategyEntity = classUnderTest.save(strategy);

        //then
        assertStrategyEquals(Fixture.getDefaultCashStrategyEntity(), strategyEntity);
    }

    @Test
    void testGetBestSuitableStrategy() {
        //given
        strategyRepository.save(Fixture.getDefaultCashStrategyEntity());
        strategyRepository.save(Fixture.getStrategyEntity());

        //when
        StrategyEntity strategyEntity =
                classUnderTest.getSuitableStrategy(3, 14);

        //then
        assertStrategyEquals(Fixture.getStrategyEntity(), strategyEntity);
    }

    @Test
    void testGetDefaultStrategy() {
        //given
        strategyRepository.save(Fixture.getDefaultCashStrategyEntity());
        strategyRepository.save(Fixture.getStrategyEntity());

        //when
        StrategyEntity strategyEntity =
                classUnderTest.getSuitableStrategy(0, 25);

        //then
        assertStrategyEquals(Fixture.getDefaultCashStrategyEntity(), strategyEntity);
    }

    @Test
    void testStrategyNotFound() {
        Throwable exception = assertThrows(StrategyNotFoundException.class,
                () -> classUnderTest.get(100));

        assertEquals("Strategy not found", exception.getMessage());
    }

    private void assertStrategyEquals(StrategyEntity expected, StrategyEntity actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getMinRiskLevel(), actual.getMinRiskLevel());
        assertEquals(expected.getMaxRiskLevel(), actual.getMaxRiskLevel());
        assertEquals(expected.getMinYearsToRetirement(), actual.getMinYearsToRetirement());
        assertEquals(expected.getMaxYearsToRetirement(), actual.getMaxYearsToRetirement());
        assertEquals(expected.getCashPercentage(), actual.getCashPercentage());
        assertEquals(expected.getBondsPercentage(), actual.getBondsPercentage());
        assertEquals(expected.getStocksPercentage(), actual.getStocksPercentage());
    }
}
