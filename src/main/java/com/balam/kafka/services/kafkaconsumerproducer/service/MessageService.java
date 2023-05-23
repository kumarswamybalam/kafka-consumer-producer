package com.balam.kafka.services.kafkaconsumerproducer.service;

import com.balam.kafka.services.kafkaconsumerproducer.domain.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class MessageService {

    public Flux<String> processMessage(Message message) {
        return  null;
    }
}
