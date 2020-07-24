package br.eti.emersondantas.api.rebel.services;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@RequiredArgsConstructor
@Service
public class NotifyAllRebelsAsyncWithResultServiceImpl implements NotifyAllRebelsAsyncWithResultService {

    @Async
    @Override
    public Future<Boolean> notifyAllRebels(String message) {
        AsyncResult<Boolean> result = null;
        // Simulate method execution time
        long pause = 5000;
        try {
            System.out.println("A");
            Thread.sleep(pause);
            System.out.println("B");
            System.out.println(message);
            result = new AsyncResult<>(Boolean.TRUE);
        } catch (Exception e) {
            result = new AsyncResult<>(Boolean.FALSE);
            System.out.println("Exception on notify all rebels");
        }

        return result;
    }
}
