package com.swmaestro.cotuber.exception;

public class EmptyDownloadQueueException extends RuntimeException {
    public EmptyDownloadQueueException(String message) {
        super(message);
    }
}
