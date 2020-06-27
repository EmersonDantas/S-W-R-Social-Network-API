package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.rebel.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GetItemsAverageServiceImpl implements GetItemsAverageService{

    private final ItemRepository itemRepository;

    @Override
    public HashMap<String, Long> getItemsAverage(){
        List<String> names = this.itemRepository.getAllDistinctName();
        HashMap<String, Long> itemsAmountAvgMap = new HashMap<>();
        for(String itemName: names){
            Long avg = this.itemRepository.getAverageOfItemsWithThatName(itemName);
            itemsAmountAvgMap.put(itemName, avg);
        }
        return itemsAmountAvgMap;
    }
}
