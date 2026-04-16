package com.exchange.simulator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

@Service
public class JmsMessageListenerService {

    private static final Logger log = LoggerFactory.getLogger(JmsMessageListenerService.class);
    private final JmsTemplate jmsTemplate;
    private final SimulatorService simulatorService;

    public JmsMessageListenerService(JmsTemplate jmsTemplate, SimulatorService simulatorService) {
        this.jmsTemplate = jmsTemplate;
        this.simulatorService = simulatorService;
    }

    @JmsListener(destination = "simulator.in.queue")
    public void receiveMessage(Message message) {
        if (!simulatorService.getStatus().running()) {
            log.warn("Simulator is stopped. Ignoring message.");
            return;
        }

        try {
            if (message instanceof TextMessage textMessage) {
                String payload = textMessage.getText();
                log.info("Received XML request on simulator.in.queue:\n{}", payload);
                
                // Emulate rule processing - for now return a static sample response
                String responsePayload = """
                        <?xml version="1.0" encoding="UTF-8"?>
                        <Response>
                            <Status>Success</Status>
                            <Message>Message processed by emulator</Message>
                            <OriginalCorrelationId>%s</OriginalCorrelationId>
                        </Response>
                        """.formatted(message.getJMSMessageID());

                // Send the response to the output queue
                log.info("Publishing XML response to simulator.out.queue:\n{}", responsePayload);
                jmsTemplate.send("simulator.out.queue", session -> {
                    TextMessage responseMsg = session.createTextMessage(responsePayload);
                    responseMsg.setJMSCorrelationID(message.getJMSMessageID());
                    return responseMsg;
                });

            } else {
                log.warn("Received non-text message of type: {}", message.getClass().getName());
            }
        } catch (JMSException e) {
            log.error("Failed to process JMS message", e);
        }
    }
}
