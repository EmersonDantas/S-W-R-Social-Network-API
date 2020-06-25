package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.rebel.Rebel;
import br.eti.emersondantas.api.rebel.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SaveRebelServiceImpl implements SaveRebelService{

    private final RebelRepository rebelRepository;

    @Transactional
    @Override
    public void save(Rebel rebel) {
        this.rebelRepository.save(rebel);
    }
}
