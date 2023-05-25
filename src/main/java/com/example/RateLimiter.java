package com.example;

import java.util.concurrent.Semaphore;

public class RateLimiter {
    private final Semaphore semaphore;
    private int acceptedRequests;
    private int rejectedRequests;

    public RateLimiter(int rate) {
        this.semaphore = new Semaphore(rate);
        this.acceptedRequests = 0;
        this.rejectedRequests = 0;
    }

    public void submitRequest() {
        if (semaphore.tryAcquire()) {
            acceptedRequests++;
            processRequest();
            semaphore.release();
        } else {
            rejectedRequests++;
        }
    }

    private void processRequest() {
        // Логика обработки запроса
    }

    public int getAcceptedRequests() {
        return acceptedRequests;
    }

    public int getRejectedRequests() {
        return rejectedRequests;
    }
}
