package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.rebel.Rebel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@FunctionalInterface
public interface ListRebelService {

    Page<Rebel> list(Pageable pageable);
}
