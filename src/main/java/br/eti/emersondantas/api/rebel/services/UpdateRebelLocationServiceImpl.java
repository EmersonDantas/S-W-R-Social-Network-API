package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.exceptions.RebelNotFoundException;
import br.eti.emersondantas.api.rebel.Location;
import br.eti.emersondantas.api.rebel.Rebel;
import br.eti.emersondantas.api.rebel.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateRebelLocationServiceImpl implements UpdateRebelLocationService{

    private final RebelRepository rebelRepository;

    @Caching(evict = {
            @CacheEvict(cacheNames = Rebel.CACHE_NAME, key="#id"),
            @CacheEvict(cacheNames = ListRebelServiceImpl.CACHE_NAME, allEntries = true)
    })
    @Override
    public void updateLocation(Long id, Location location) {
        Rebel rebel = this.rebelRepository.findById(id).orElseThrow(RebelNotFoundException::new);
        rebel.setLocation(location);
        this.rebelRepository.save(rebel);
    }
}
