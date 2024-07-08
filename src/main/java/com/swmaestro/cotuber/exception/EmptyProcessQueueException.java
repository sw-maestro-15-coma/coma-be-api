package com.swmaestro.cotuber.exception;

public class EmptyProcessQueueException extends RuntimeException {
    public EmptyProcessQueueException(String message) {
        super(message);
    }
}
