package br.eti.emersondantas.api.rebel.services;

import java.util.HashMap;

@FunctionalInterface
public interface GetItemsAverageService {

    HashMap<String, Double> getItemsAverage();
}
