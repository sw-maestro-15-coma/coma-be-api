package com.swmaestro.cotuber.infra.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.swmaestro.cotuber.video.VideoDownloadProducer;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RabbitMQVideoDownloadProducer implements VideoDownloadProducer {
    private static final String QUEUE_NAME = "video-download";

    private final Connection connection;
    private final ObjectMapper objectMapper;

    @Override
    public void send(VideoDownloadMessageRequest request) {
        try (Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            byte[] message = objectMapper.writeValueAsBytes(request);

            channel.basicPublish("", QUEUE_NAME, null, message);
        } catch (Exception e) {
            throw new IllegalStateException("video download producer에서 예외 발생 : " + e.getMessage());
        }
    }
}
