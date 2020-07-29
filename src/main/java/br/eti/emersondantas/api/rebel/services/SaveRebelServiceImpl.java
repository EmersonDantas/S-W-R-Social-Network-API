package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.rebel.Item;
import br.eti.emersondantas.api.rebel.ItemRepository;
import br.eti.emersondantas.api.rebel.Rebel;
import br.eti.emersondantas.api.rebel.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SaveRebelServiceImpl implements SaveRebelService{

    private final RebelRepository rebelRepository;

    private final ItemRepository itemRepository;

    @Caching(evict = {
            @CacheEvict(cacheNames = GetItemsAverageServiceImpl.CACHE_NAME, allEntries = true),
            @CacheEvict(cacheNames = GetRenegadePercentageServiceImpl.CACHE_NAME, allEntries = true),
            @CacheEvict(cacheNames = ListRebelServiceImpl.CACHE_NAME, allEntries = true)
    })
    @Override
    public void save(Rebel rebel) {
        List<Item> rebelItems = rebel.getItems();
        Rebel savedRebel = this.rebelRepository.save(rebel);
        List<Item> savedItems = new ArrayList<>();
        if(savedRebel != null){
            for (Item item: rebel.getItems()) {
                savedItems.add(this.itemRepository.save(item));
            }
            savedRebel.setItems(savedItems);
            this.rebelRepository.save(savedRebel);
        }

    }
}
