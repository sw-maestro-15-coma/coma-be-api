package com.swmaestro.cotuber.exception;

public class VideoDownloadFailException extends RuntimeException {
    public VideoDownloadFailException(String message) {
        super(message);
    }
}
