package de.bcgdv.wealthmangement.repository;

import de.bcgdv.wealthmangement.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
}
