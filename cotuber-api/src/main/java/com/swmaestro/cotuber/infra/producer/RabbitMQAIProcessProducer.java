package com.swmaestro.cotuber.infra.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.swmaestro.cotuber.ai.AIProcessProducer;
import com.swmaestro.cotuber.ai.dto.AIProcessMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RabbitMQAIProcessProducer implements AIProcessProducer {
    private static final String QUEUE_NAME = "ai-process";

    private final Connection connection;
    private final ObjectMapper objectMapper;

    @Override
    public void send(AIProcessMessageRequest request) {
        try (Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            byte[] message = objectMapper.writeValueAsBytes(request);

            channel.basicPublish("", QUEUE_NAME, null, message);
        } catch (Exception e) {
            throw new IllegalStateException("ai process producer에서 예외 발생 : " + e.getMessage());
        }
    }
}
