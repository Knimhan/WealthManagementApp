package de.bcgdv.wealthmangement.controller;

import de.bcgdv.wealthmangement.model.Customer;
import de.bcgdv.wealthmangement.model.CustomerPortfolio;
import de.bcgdv.wealthmangement.service.CustomerService;
import de.bcgdv.wealthmangement.service.RebalancingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final RebalancingService rebalancingService;

    @GetMapping
    public ResponseEntity<List<Customer>> get() {
        return ResponseEntity.ok().body(customerService.getAll());
    }

    @PostMapping("/{customerId}/rebalance")
    public ResponseEntity<CustomerPortfolio> rebalance(
            @PathVariable("customerId") Integer customerId) {
        return ResponseEntity.ok()
                .body(rebalancingService.balancePortfolio(customerId));
    }
}
