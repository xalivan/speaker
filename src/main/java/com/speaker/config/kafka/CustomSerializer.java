package com.speaker.config.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.speaker.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class CustomSerializer implements Serializer<MessageDTO> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, MessageDTO data) {
        try {
            if (data == null) {
                System.out.println("Null received at serializing");
                return null;
            }
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return objectMapper.writeValueAsString(data).getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing MessageDTO to byte[]");
        }
    }
    @Override
    public byte[] serialize(String topic, Headers headers, MessageDTO data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
