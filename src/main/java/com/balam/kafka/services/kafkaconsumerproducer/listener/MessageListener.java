package com.balam.kafka.services.kafkaconsumerproducer.listener;

import com.balam.kafka.services.kafkaconsumerproducer.domain.Message;
import com.balam.kafka.services.kafkaconsumerproducer.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
public class MessageListener {

    @Autowired
    MessageService messageService;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${kafka.message.enabled}")
    boolean processingEnabled;

    @KafkaListener(topics = "${kafka.topic}")
    public void consumeMessage(@Payload Message message) {
        Mono.fromSupplier( () -> message)
                .doOnRequest( l -> {
                    try {
                        String request = objectMapper.writeValueAsString(message);
                        log.info("Received message for item {} : {}",message.getItem(), request);
                    } catch (JsonProcessingException e) {
                        log.error("Error parsing message", e);
                    }

                })
                .filterWhen(msg -> Mono.just(processingEnabled))
                .flatMapMany( msg ->
                        messageService.processMessage(message))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnComplete(() ->  {
                    String status = processingEnabled ? "processed" : "skipped";
                    log.info("Message {} for item {}",status, message.getItem());

                })
                .blockLast();
    }
}
