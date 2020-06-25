package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.rebel.Rebel;
import org.springframework.transaction.annotation.Transactional;

@FunctionalInterface
public interface SaveRebelService {

    @Transactional
    void save(Rebel rebel);
}
