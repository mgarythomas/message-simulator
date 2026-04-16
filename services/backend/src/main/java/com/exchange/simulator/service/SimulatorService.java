package com.exchange.simulator.service;

import com.exchange.simulator.model.SimulatorStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SimulatorService {
    
    private static final Logger log = LoggerFactory.getLogger(SimulatorService.class);
    
    private boolean isRunning = false;
    private Instant startTime = null;

    public SimulatorStatus getStatus() {
        long uptime = isRunning && startTime != null 
            ? java.time.Duration.between(startTime, Instant.now()).getSeconds() 
            : 0;
        return new SimulatorStatus(isRunning, uptime);
    }

    public void startSimulator() {
        if (!isRunning) {
            log.info("Starting message simulator engines.");
            this.isRunning = true;
            this.startTime = Instant.now();
        }
    }

    public void stopSimulator() {
        if (isRunning) {
            log.info("Stopping message simulator engines.");
            this.isRunning = false;
            this.startTime = null;
        }
    }
}
