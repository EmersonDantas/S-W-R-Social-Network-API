package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.exceptions.ItemsAreEmptyException;
import br.eti.emersondantas.api.exceptions.ItemsHaveDifferentScoresException;
import br.eti.emersondantas.api.exceptions.RebelNotFoundException;
import br.eti.emersondantas.api.exceptions.RebelRenegadeException;
import br.eti.emersondantas.api.rebel.Item;
import br.eti.emersondantas.api.rebel.ItemRepository;
import br.eti.emersondantas.api.rebel.Rebel;
import br.eti.emersondantas.api.rebel.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NegotiateItemsServiceImpl implements NegotiateItemsService{

    private final RebelRepository rebelRepository;

    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public void negotiateItems(Long idFrom, Long idTo, List<Item> itemsFrom, List<Item> itemsTo) {
        Rebel rebelFrom = this.rebelRepository.findById(idFrom).orElseThrow(RebelNotFoundException::new);
        Rebel rebelTo = this.rebelRepository.findById(idTo).orElseThrow(RebelNotFoundException::new);

        if(rebelFrom.isRenegade() || rebelTo.isRenegade()) throw new RebelRenegadeException();
        if(checkItemsAreEmpty(itemsFrom, itemsTo)) throw new ItemsAreEmptyException();
        if(!checkItemsSameScore(itemsFrom, itemsTo)) throw new ItemsHaveDifferentScoresException();
        if(!checkRebelHaveThisItems(itemsFrom, rebelFrom) || !checkRebelHaveThisItems(itemsTo, rebelTo)) throw new ItemsHaveDifferentScoresException();

        exchangeItems(rebelFrom, rebelTo, itemsFrom, itemsTo);

    }

    private boolean checkItemsSameScore(List<Item> itemsFrom, List<Item> itemsTo){
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

    private boolean checkItemsAreEmpty(List<Item> itemsFrom, List<Item> itemsTo){
        return itemsFrom.isEmpty() || itemsTo.isEmpty();
    }

    private boolean checkRebelHaveThisItems(List<Item> items, Rebel rebel){
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

    private void exchangeItems(Rebel rebelFrom, Rebel rebelTo, List<Item> itemsFrom, List<Item> itemsTo){
        this.makeExchange(itemsTo, rebelTo, rebelFrom);
        this.makeExchange(itemsFrom, rebelFrom, rebelTo);
    }

    public void makeExchange(List<Item> items, Rebel rebelA, Rebel rebelB){
        for (Item listItem : items) {
            int index = rebelA.getItems().indexOf(listItem);
            Item rebelItem = rebelA.getItems().get(index);
            rebelItem.setAmount(rebelItem.getAmount() - listItem.getAmount());
            if (rebelItem.getAmount() == 0) {
                rebelA.getItems().remove(listItem);
            }else{
                listItem.setId(null);
            }
            listItem.setRebel(rebelB);

            this.itemRepository.save(rebelItem);
            this.itemRepository.save(listItem);
        }
    }

}
