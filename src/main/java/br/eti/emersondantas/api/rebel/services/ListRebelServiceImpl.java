package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.rebel.Rebel;
import br.eti.emersondantas.api.rebel.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListRebelServiceImpl implements ListRebelService{

    private final RebelRepository rebelRepository;

    @Override
    public Page<Rebel> list(Pageable pageable) {
        return this.rebelRepository.findAll(pageable);
    }
}
