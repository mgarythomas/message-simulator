package com.exchange.simulator.controller;

import com.exchange.simulator.model.SimulatorStatus;
import com.exchange.simulator.service.SimulatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class SimulatorController {

    private final SimulatorService simulatorService;

    public SimulatorController(SimulatorService simulatorService) {
        this.simulatorService = simulatorService;
    }

    @GetMapping("/status")
    public ResponseEntity<SimulatorStatus> getStatus() {
        return ResponseEntity.ok(simulatorService.getStatus());
    }

    @PostMapping("/start")
    public ResponseEntity<Void> startSimulator() {
        simulatorService.startSimulator();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/stop")
    public ResponseEntity<Void> stopSimulator() {
        simulatorService.stopSimulator();
        return ResponseEntity.ok().build();
    }
    
    // Remaining endpoints (config, metrics, logs) will be stubbed via configuration classes.
}
