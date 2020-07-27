package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.rebel.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetRenegadePercentageServiceImpl implements GetRenegadePercentageService{

    private final RebelRepository rebelRepository;

    public static final String CACHE_NAME = "RenegadePercentage";

    @Cacheable(cacheNames = CACHE_NAME, key="#root.method.name")
    @Override
    public Double getRenegadePercentage() {
        Long total = this.rebelRepository.count();
        Long renegades = this.rebelRepository.countAllRenegades();
        return (total > 0 ? ((renegades / (total+0.0)) * 100.0): 0);
    }
}
