package com.swmaestro.cotuber.infra.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.swmaestro.cotuber.shorts.ShortsGenerateProducer;
import com.swmaestro.cotuber.shorts.dto.ShortsGenerateMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RabbitMQShortsGenerateProducer implements ShortsGenerateProducer {
    private static final String QUEUE_NAME = "shorts-generate";

    private final Connection connection;
    private final ObjectMapper objectMapper;

    @Override
    public void send(ShortsGenerateMessageRequest request) {
        try (Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            byte[] message = objectMapper.writeValueAsBytes(request);

            channel.basicPublish("", QUEUE_NAME, null, message);
        } catch (Exception e) {
            throw new IllegalStateException("shorts generate producer에서 예외 발생 : " + e.getMessage());
        }
    }
}
