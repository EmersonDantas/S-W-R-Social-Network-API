package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.rebel.Rebel;

@FunctionalInterface
public interface GetRebelService {

    Rebel get(Long id);
}
