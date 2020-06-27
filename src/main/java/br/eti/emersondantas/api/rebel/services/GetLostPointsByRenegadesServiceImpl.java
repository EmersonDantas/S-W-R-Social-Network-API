package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.rebel.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetLostPointsByRenegadesServiceImpl implements GetLostPointsByRenegadesService{

    private final ItemRepository itemRepository;

    @Override
    public Long getLostPointsByRenegades() {
        Long lostPoints = this.itemRepository.getLostPointsByRenegades();
        return (lostPoints != null ? lostPoints: 0);
    }
}
