package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.exceptions.RebelNotFoundException;
import br.eti.emersondantas.api.rebel.Rebel;
import br.eti.emersondantas.api.rebel.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportRenegadeRebelServiceImpl implements ReportRenegadeRebelService{

    private final RebelRepository rebelRepository;

    private final int RENEGADE_VALUE = 3;

    @CacheEvict(cacheNames = Rebel.CACHE_NAME, key="#id")
    @Override
    public void report(Long id) {
        Rebel rebel = this.rebelRepository.findById(id).orElseThrow(RebelNotFoundException::new);
        rebel.setDenunciations(rebel.getDenunciations()+1);
        if(rebel.getDenunciations() == RENEGADE_VALUE) rebel.markAsRenegade();
        this.rebelRepository.save(rebel);
    }
}
