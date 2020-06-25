package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.rebel.Item;

import java.util.List;

@FunctionalInterface
public interface NegotiateItemsService {

    void negotiateItems(Long idFrom, Long idTo, List<Item> itemsFrom, List<Item> itemsTo);

}
