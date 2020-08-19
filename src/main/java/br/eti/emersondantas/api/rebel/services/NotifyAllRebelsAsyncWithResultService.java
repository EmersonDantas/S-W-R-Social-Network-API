package br.eti.emersondantas.api.rebel.services;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface NotifyAllRebelsAsyncWithResultService {
    CompletableFuture<Boolean> notifyAllRebels(String message);
}
