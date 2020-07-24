package br.eti.emersondantas.api.rebel.services;

import java.util.concurrent.Future;

@FunctionalInterface
public interface NotifyAllRebelsAsyncWithResultService {
    Future<Boolean> notifyAllRebels(String message);
}
