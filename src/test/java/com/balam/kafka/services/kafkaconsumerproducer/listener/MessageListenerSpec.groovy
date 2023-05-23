package com.balam.kafka.services.kafkaconsumerproducer.listener

import com.balam.kafka.services.kafkaconsumerproducer.BaseSpecification
import com.balam.kafka.services.kafkaconsumerproducer.domain.Message
import com.balam.kafka.services.kafkaconsumerproducer.service.MessageService
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.io.FileUtils
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import reactor.core.publisher.Flux

import java.nio.charset.Charset

class MessageListenerSpec extends BaseSpecification {

    Message message

    @Autowired
    MessageListener messageListener

    @Autowired
    ObjectMapper objectMapper

    @SpringBean
    MessageService messageService = Mock()

    @Value("classpath:message/message.json")
    private Resource messageSource

    def setup() {
        String orderRequestJson = FileUtils.readFileToString(messageSource.getFile(), Charset.defaultCharset())
        message = objectMapper.readValue(orderRequestJson, Message)
    }

    def 'consume message from kafka and map the required fields' (){
        when:
        messageListener.consumeMessage(message)

        then:
        message.id == UUID.fromString('a9d99841-562a-4699-b3a4-29e29cd5657d')
        message.item == 1244
        message.ruleId == 1110
        message.discountValue == 10.0

        1 * messageService.processMessage(message) >> Flux.just()
        0 * _
    }


    def 'skip message from kafka when the processing flag is false' (){
        given:
        messageListener.processingEnabled = false

        when:
        messageListener.consumeMessage(message)

        then:
        message.id == UUID.fromString('a9d99841-562a-4699-b3a4-29e29cd5657d')
        message.item == 1244
        message.ruleId == 1110
        message.discountValue == 10.0

        0 * _
    }
}
