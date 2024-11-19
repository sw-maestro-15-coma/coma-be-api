package com.swmaestro.cotuber.infra.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.swmaestro.cotuber.shorts.upload.YoutubeUploadProducer;
import com.swmaestro.cotuber.shorts.upload.dto.YoutubeUploadMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RabbitMQYoutubeUploadProducer implements YoutubeUploadProducer {
    private static final String QUEUE_NAME = "youtube-upload";

    private final Connection connection;
    private final ObjectMapper objectMapper;

    @Override
    public void send(YoutubeUploadMessageRequest request) {
        try (Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            byte[] message = objectMapper.writeValueAsBytes(request);

            channel.basicPublish("", QUEUE_NAME, null, message);
        } catch (Exception e) {
            throw new IllegalStateException("youtube upload producer에서 예외 발생 : " + e.getMessage());
        }
    }
}
