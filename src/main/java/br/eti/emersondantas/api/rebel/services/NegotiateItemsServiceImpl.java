package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.exceptions.ItemsAreEmptyException;
import br.eti.emersondantas.api.exceptions.ItemsHaveDifferentScoresException;
import br.eti.emersondantas.api.exceptions.RebelNotFoundException;
import br.eti.emersondantas.api.rebel.Item;
import br.eti.emersondantas.api.rebel.Rebel;
import br.eti.emersondantas.api.rebel.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NegotiateItemsServiceImpl implements NegotiateItemsService{

    private final RebelRepository rebelRepository;

    @Override
    public void negotiateItems(Long idFrom, Long idTo, List<Item> itemsFrom, List<Item> itemsTo) {
        Rebel rebelFrom = this.rebelRepository.findById(idFrom).orElseThrow(RebelNotFoundException::new);
        Rebel rebelTo = this.rebelRepository.findById(idTo).orElseThrow(RebelNotFoundException::new);

        if(!checkItemsAreEmpty(itemsFrom, itemsTo)) throw new ItemsAreEmptyException();
        if(!checkItemsSameScore(itemsFrom, itemsTo)) throw new ItemsHaveDifferentScoresException();
        if(!checkRebelHaveThisItems(itemsFrom, rebelFrom) || !checkRebelHaveThisItems(itemsTo, rebelTo)) throw new ItemsHaveDifferentScoresException();

        exchangeItems(rebelFrom, rebelTo, itemsFrom, itemsTo);
        this.rebelRepository.saveAll(Arrays.asList(rebelFrom, rebelTo));

    }

    public boolean checkItemsSameScore(List<Item> itemsFrom, List<Item> itemsTo){
        int countItemsFrom = 0;
        int countItemsTo = 0;

        for (Item item : itemsFrom){
            countItemsFrom += item.getAmount()* item.getPoints();
        }

        for (Item item : itemsTo){
            countItemsTo += item.getAmount()* item.getPoints();
        }

        return countItemsFrom == countItemsTo;

    }

    public boolean checkItemsAreEmpty(List<Item> itemsFrom, List<Item> itemsTo){
        return itemsFrom.isEmpty() || itemsTo.isEmpty();
    }

    public boolean checkRebelHaveThisItems(List<Item> items, Rebel rebel){
        if(rebel.getItems().isEmpty()) return false;

        int haveItemsCount = 0;
        for(Item item: items){
            int index = rebel.getItems().indexOf(item);
            if(index != -1){
                haveItemsCount++;
                Item rebelItem = rebel.getItems().get(index);
                if(rebelItem.getAmount() < item.getAmount()) return false;
            }
        }

        return haveItemsCount == items.size();
    }

    public void exchangeItems(Rebel rebelFrom, Rebel rebelTo, List<Item> itemsFrom, List<Item> itemsTo){
        exchangeRebelItems(itemsFrom, rebelTo);
        exchangeRebelItems(itemsTo, rebelFrom);
    }

    public void exchangeRebelItems(List<Item> items, Rebel rebel) {
        for (Item item : items) {
            int index = rebel.getItems().indexOf(item);
            Item rebelItem = rebel.getItems().get(index);
            rebelItem.setAmount(rebelItem.getAmount() - item.getAmount());
            if (rebelItem.getAmount() == 0) rebel.getItems().remove(rebelItem);
        }
    }
}
