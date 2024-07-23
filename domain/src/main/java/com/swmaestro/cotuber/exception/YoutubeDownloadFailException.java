package com.swmaestro.cotuber.exception;

public class YoutubeDownloadFailException extends RuntimeException {
    public YoutubeDownloadFailException(String message) {
        super(message);
    }
}
