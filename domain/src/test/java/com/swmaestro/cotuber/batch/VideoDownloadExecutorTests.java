package com.swmaestro.cotuber.batch;

import com.swmaestro.cotuber.batch.dto.VideoDownloadTask;
import com.swmaestro.cotuber.video.VideoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoDownloadExecutorTests {

    @Mock
    VideoService videoService;
    @Mock
    VideoDownloadQueue queue;

    @InjectMocks
    VideoDownloadExecutor downloadExecutor;

    @DisplayName("큐가 비어있으면 바로 리턴")
    @Test
    void nothingWhenQueueIsEmpty() {
        // given
        when(queue.isEmpty()).thenReturn(true);

        // when
        downloadExecutor.execute();

        // then
        verify(videoService, never()).downloadYoutube(any());
    }

    @DisplayName("큐가 비어있지 않으면 다운로드 실행")
    @Test
    void executeWhenQueueIsNotEmpty() {
        // given
        when(queue.isEmpty()).thenReturn(false);
        when(queue.pop()).thenReturn(VideoDownloadTask.builder().build());

        // when
        downloadExecutor.execute();

        // then
        verify(videoService, times(1)).downloadYoutube(any());
    }
}
