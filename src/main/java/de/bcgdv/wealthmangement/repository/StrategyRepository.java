package de.bcgdv.wealthmangement.repository;

import de.bcgdv.wealthmangement.entity.StrategyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface StrategyRepository extends JpaRepository<StrategyEntity, Integer> {

    @Query(value = "SELECT s FROM StrategyEntity s WHERE :riskLevel BETWEEN minRiskLevel AND maxRiskLevel " +
            "AND :yearsToRetire BETWEEN minYearsToRetirement AND maxYearsToRetirement ORDER BY id")
    List<StrategyEntity> findBestSuitableStrategy(@Param("riskLevel") Integer riskLevel, @Param("yearsToRetire") Integer yearsToRetire);

    @Query(value = "SELECT s FROM StrategyEntity s WHERE cashPercentage = 100")
    StrategyEntity findDefaultCashStrategy();
}
