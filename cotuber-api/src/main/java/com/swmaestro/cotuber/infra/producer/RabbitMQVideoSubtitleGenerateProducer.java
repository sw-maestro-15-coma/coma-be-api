package com.swmaestro.cotuber.infra.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.swmaestro.cotuber.video.VideoSubtitleGenerateProducer;
import com.swmaestro.cotuber.video.dto.VideoSubtitleGenerateMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RabbitMQVideoSubtitleGenerateProducer implements VideoSubtitleGenerateProducer {
    private static final String QUEUE_NAME = "video-subtitle-generate";

    private final Connection connection;
    private final ObjectMapper objectMapper;

    @Override
    public void send(VideoSubtitleGenerateMessageRequest request) {
        try (Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            byte[] message = objectMapper.writeValueAsBytes(request);

            channel.basicPublish("", QUEUE_NAME, null, message);
        } catch (Exception e) {
            throw new IllegalStateException("video subtitle generate producer에서 예외 발생 : " + e.getMessage());
        }
    }
}
