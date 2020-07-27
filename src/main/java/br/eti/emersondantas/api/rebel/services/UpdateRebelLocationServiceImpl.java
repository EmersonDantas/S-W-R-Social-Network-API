package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.exceptions.RebelNotFoundException;
import br.eti.emersondantas.api.rebel.Location;
import br.eti.emersondantas.api.rebel.Rebel;
import br.eti.emersondantas.api.rebel.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateRebelLocationServiceImpl implements UpdateRebelLocationService{

    private final RebelRepository rebelRepository;

    @CacheEvict(cacheNames = Rebel.CACHE_NAME, key="#id")
    @Override
    public void updateLocation(Long id, Location location) {
        Rebel rebel = this.rebelRepository.findById(id).orElseThrow(RebelNotFoundException::new);
        rebel.setLocation(location);
        this.rebelRepository.save(rebel);
    }
}
