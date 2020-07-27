package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.rebel.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetLostPointsByRenegadesServiceImpl implements GetLostPointsByRenegadesService{

    private final ItemRepository itemRepository;

    public static final String CACHE_NAME = "LostPointsByRenegades";

    @Cacheable(cacheNames = CACHE_NAME, key="#root.method.name")
    @Override
    public Long getLostPointsByRenegades() {
        Long lostPoints = this.itemRepository.getLostPointsByRenegades();
        return (lostPoints != null ? lostPoints: 0);
    }
}
