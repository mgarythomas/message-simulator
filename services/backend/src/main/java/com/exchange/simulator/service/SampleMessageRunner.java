package com.exchange.simulator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

@Component
public class SampleMessageRunner {

    private static final Logger log = LoggerFactory.getLogger(SampleMessageRunner.class);
    private final JmsTemplate jmsTemplate;
    private final SimulatorService simulatorService;

    public SampleMessageRunner(JmsTemplate jmsTemplate, SimulatorService simulatorService) {
        this.jmsTemplate = jmsTemplate;
        this.simulatorService = simulatorService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void publishSampleMessages() {
        // Automatically start the simulator on boot to process the sample message
        simulatorService.startSimulator();

        String sampleXml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <TradeRequest>
                    <TradeId>TRD-1029384</TradeId>
                    <Instrument>BTC/USD</Instrument>
                    <Quantity>1.5</Quantity>
                    <Action>BUY</Action>
                </TradeRequest>
                """;

        log.info("Publishing sample XML message to simulator.in.queue upon startup.");
        jmsTemplate.send("simulator.in.queue", session -> session.createTextMessage(sampleXml));
    }

    // Listener to verify the output queue received the correct response
    @JmsListener(destination = "simulator.out.queue")
    public void receiveOutboundMessage(Message message) {
        try {
            if (message instanceof TextMessage textMessage) {
                log.info("Verified outbound response on simulator.out.queue. CorrelationID: {} Payload: \n{}", 
                    message.getJMSCorrelationID(), textMessage.getText());
            }
        } catch (JMSException e) {
            log.error("Failed to process outbound JMS message", e);
        }
    }
}
