package br.eti.emersondantas.api.rebel.services;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotifyAllRebelsAsyncServiceImpl implements NotifyAllRebelsAsyncService {

    @Async
    @Override
    public void notifyAllRebels(String message) {
        // Simulate method execution time
        long pause = 5000;
        try {
            System.out.println("A");
            Thread.sleep(pause);
            System.out.println("B");
            System.out.println(message);
        } catch (Exception e) {
            System.out.println("Exception on notify all rebels");
        }
    }
}
