package com.swmaestro.cotuber.infra.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.swmaestro.cotuber.shorts.ShortsProcessProducer;
import com.swmaestro.cotuber.shorts.dto.ShortsProcessMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Component
public class RabbitMQShortsProcessProducer implements ShortsProcessProducer {
    private static final String QUEUE_NAME = "shorts-process";

    private final Connection connection;
    private final ObjectMapper objectMapper;

    @Override
    public void send(ShortsProcessMessageRequest request) {
        try (Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String message = objectMapper.writeValueAsString(request);

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IllegalStateException("shorts process producer에서 예외 발생 : " + e.getMessage());
        }
    }
}
