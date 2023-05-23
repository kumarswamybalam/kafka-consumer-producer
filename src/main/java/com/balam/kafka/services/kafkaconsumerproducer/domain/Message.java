package com.balam.kafka.services.kafkaconsumerproducer.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Message {
    UUID id;

    Long ruleId;

    LocalDateTime beginTimeStamp;

    LocalDateTime endTimeStamp;

    Long item;

    DiscountType discountType;

    Double discountValue;
}
