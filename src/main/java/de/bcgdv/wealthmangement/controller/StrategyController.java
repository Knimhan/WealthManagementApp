package de.bcgdv.wealthmangement.controller;

import de.bcgdv.wealthmangement.model.Strategy;
import de.bcgdv.wealthmangement.service.StrategyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/strategies")
@RequiredArgsConstructor
public class StrategyController {
    private final StrategyService strategyService;

    @GetMapping
    public ResponseEntity<List<Strategy>> get() {
        return ResponseEntity.ok().body(strategyService.getAll());
    }

    @PostMapping
    public ResponseEntity<Strategy> assignStrategy(@RequestParam("customerId") Integer customerId) {
        return ResponseEntity.ok().body(strategyService.assignStrategy(customerId));
    }
}
