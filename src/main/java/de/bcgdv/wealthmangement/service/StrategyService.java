package de.bcgdv.wealthmangement.service;

import de.bcgdv.wealthmangement.entity.CustomerEntity;
import de.bcgdv.wealthmangement.entity.StrategyEntity;
import de.bcgdv.wealthmangement.exception.StrategyNotFoundException;
import de.bcgdv.wealthmangement.model.Strategy;
import de.bcgdv.wealthmangement.repository.StrategyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StrategyService {

    private final StrategyRepository strategyRepository;

    private final CustomerService customerService;

    public void saveAll(List<Strategy> strategies) {
        for (Strategy strategy : strategies) {
            save(strategy);
        }
    }

    public List<Strategy> getAll() {
        List<Strategy> strategies = new ArrayList<>();
        for (StrategyEntity strategyEntity : strategyRepository.findAll()) {
            Strategy strategy = new Strategy();
            BeanUtils.copyProperties(strategyEntity, strategy, "strategyId", "id");
            strategy.setStrategyId(strategyEntity.getId());
            strategies.add(strategy);
        }
        return strategies;
    }

    public StrategyEntity save(Strategy strategy) {
        StrategyEntity strategyEntity = new StrategyEntity();
        strategyEntity.setId(strategy.getStrategyId());
        BeanUtils.copyProperties(strategy, strategyEntity, "strategyId", "id");
        return strategyRepository.save(strategyEntity);
    }

    public StrategyEntity get(Integer id) {
        Optional<StrategyEntity> optionalStrategyEntity = strategyRepository.findById(id);
        if (!optionalStrategyEntity.isPresent())
            throw new StrategyNotFoundException("Strategy not found");
        return optionalStrategyEntity.get();
    }

    public StrategyEntity getSuitableStrategy
            (Integer riskLevel, Integer yearsToRetire) {
        List<StrategyEntity> strategyEntityList = strategyRepository
                .findBestSuitableStrategy
                        (riskLevel, yearsToRetire);
        return strategyEntityList == null || strategyEntityList.isEmpty() ? null : strategyEntityList.get(0);
    }

    public StrategyEntity getDefaultCashStrategy() {
        return strategyRepository.findDefaultCashStrategy();
    }


    public Strategy assignStrategy(Integer customerId) {
        CustomerEntity customerEntity = customerService.get(customerId);
        StrategyEntity bestSuitableStrategy = findStrategy(customerEntity);
        mapStrategy(customerEntity, bestSuitableStrategy == null ? getDefaultCashStrategy() : bestSuitableStrategy);
        return getStrategy(bestSuitableStrategy);
    }

    private Strategy getStrategy(StrategyEntity strategyEntity) {
        Strategy strategy = new Strategy();
        BeanUtils.copyProperties(strategyEntity, strategy, "strategyId", "id");
        strategy.setStrategyId(strategyEntity.getId());
        return strategy;
    }

    private StrategyEntity findStrategy(CustomerEntity customerEntity) {
        return getSuitableStrategy(customerEntity.getRiskLevel(),
                getYearsToRetire(customerEntity.getDateOfBirth(),
                        customerEntity.getRetirementAge()));

    }

    private CustomerEntity mapStrategy(CustomerEntity customerEntity, StrategyEntity strategyEntity) {
        customerEntity.setStrategyEntity(strategyEntity);
        return customerService.save(customerEntity);
    }

    private Integer getYearsToRetire(String dateOfBirth, Integer retirementAge) {
        LocalDate birthDate = Date.valueOf(dateOfBirth).toLocalDate();
        Integer age = Period.between(birthDate, LocalDate.now()).getYears();
        return retirementAge - age;
    }
}
