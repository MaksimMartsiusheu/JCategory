package com.jhipster.sender.impl;

import com.jhipster.domain.Category;
import com.jhipster.avro.EntityEvent;
import com.jhipster.avro.EntityEventKey;
import com.jhipster.avro.EventType;
import com.jhipster.sender.DeletedMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;



@Component
public class KafkaAvroDeletedMessageSender implements DeletedMessageSender {

    private final Processor processor;

    @Autowired
    public KafkaAvroDeletedMessageSender(Processor processor) {
        this.processor = processor;
    }

    @Override
    public void send(Long entityId) {
        EntityEvent entityEvent = EntityEvent.newBuilder()
            .setEntityId(entityId)
            .setEntityName(Category.class.getSimpleName())
            .build();

        EntityEventKey entityEventKey = EntityEventKey.newBuilder()
            .setEventType(EventType.DELETED)
            .setDateTime(System.currentTimeMillis())
            .build();

        Message<EntityEvent> message = MessageBuilder.withPayload(entityEvent)
            .setHeader(KafkaHeaders.MESSAGE_KEY, entityEventKey)
            .build();

        processor.output()
            .send(message);
    }

}
