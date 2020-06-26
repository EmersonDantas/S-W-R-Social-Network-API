package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.rebel.Item;
import br.eti.emersondantas.api.rebel.ItemRepository;
import br.eti.emersondantas.api.rebel.Rebel;
import br.eti.emersondantas.api.rebel.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SaveRebelServiceImpl implements SaveRebelService{

    private final RebelRepository rebelRepository;

    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public void save(Rebel rebel) {
        Rebel savedRebel = this.rebelRepository.save(rebel);

        if(savedRebel != null){
            for (Item item: rebel.getItems()) {
                item.setRebel(savedRebel);
            }
            this.itemRepository.saveAll(savedRebel.getItems());
        }
    }
}
