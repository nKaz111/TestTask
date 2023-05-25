package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RateLimiterTest {
    @Test
    public void testRateLimiter() throws InterruptedException {
        int rate = 20;
        int totalRequests = 20;

        RateLimiter rateLimiter = new RateLimiter(rate);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < totalRequests; i++) {
            executor.execute(rateLimiter::submitRequest); // Запускаем запросы в параллельных потоках
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        int acceptedRequests = rateLimiter.getAcceptedRequests();
        int rejectedRequests = rateLimiter.getRejectedRequests();

        // Проверка, что общее количество запросов равно ожидаемому
        Assertions.assertEquals(totalRequests, acceptedRequests + rejectedRequests);

        // Проверка, что принятые запросы не превышают ограничение в rate
        Assertions.assertTrue(acceptedRequests <= rate);
    }
}
