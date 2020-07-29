package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.rebel.Location;

@FunctionalInterface
public interface UpdateRebelLocationService {

    void updateLocation(String id, Location location);
}
