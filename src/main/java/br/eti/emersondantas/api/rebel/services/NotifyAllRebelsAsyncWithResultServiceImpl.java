package br.eti.emersondantas.api.rebel.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class NotifyAllRebelsAsyncWithResultServiceImpl implements NotifyAllRebelsAsyncWithResultService {

    @Async("taskExecutorB")
    @Override
    public CompletableFuture<Boolean> notifyAllRebels(String message) {
        Boolean result = Boolean.FALSE;
        // Simulate method execution time
        long pause = 5000;
        try {
            log.info("A - sending notifications. Priority: {}", Thread.currentThread().getPriority());
            Thread.sleep(pause);
            result = Boolean.TRUE;
            log.info("B - successfully notified, returning result: {}", result);
        } catch (Exception e) {
            log.error("Exception on notify all rebels");
        }

        return CompletableFuture.completedFuture(result);
    }
}
