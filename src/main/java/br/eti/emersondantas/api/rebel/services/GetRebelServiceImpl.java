package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.exceptions.RebelNotFoundException;
import br.eti.emersondantas.api.rebel.Rebel;
import br.eti.emersondantas.api.rebel.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetRebelServiceImpl implements GetRebelService{

    private final RebelRepository rebelRepository;

    @Cacheable(cacheNames = Rebel.CACHE_NAME, key="#id")
    @Override
    public Rebel get(String id) {
        return this.rebelRepository.findById(id).orElseThrow(RebelNotFoundException::new);
    }
}
