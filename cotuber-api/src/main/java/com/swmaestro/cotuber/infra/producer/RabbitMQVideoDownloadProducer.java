package com.swmaestro.cotuber.infra.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.swmaestro.cotuber.batch.dto.VideoDownloadTask;
import com.swmaestro.cotuber.video.VideoDownloadProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@RequiredArgsConstructor
@Component
public class RabbitMQVideoDownloadProducer implements VideoDownloadProducer {
    private static final String QUEUE_NAME = "video-download";

    private final Connection connection;
    private final ObjectMapper objectMapper;

    @Override
    public void send(VideoDownloadTask task) {
        try (Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String message = objectMapper.writeValueAsString(task);

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException | TimeoutException e) {
            throw new IllegalStateException("video download producer에서 예외 발생 : " + e.getMessage());
        }
    }
}
