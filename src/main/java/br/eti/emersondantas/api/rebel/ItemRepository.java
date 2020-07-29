package br.eti.emersondantas.api.rebel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, String> {
    @Query("SELECT SUM(i.points * i.amount) FROM Item i WHERE i.rebel.isRenegade = TRUE")
    Long getLostPointsByRenegades();

    @Query("SELECT DISTINCT(i.name) FROM Item i WHERE i.rebel.isRenegade = FALSE")
    List<String> getAllDistinctName();

    @Query("SELECT AVG(i.amount) FROM Item i WHERE i.name = ?1 AND i.rebel.isRenegade = FALSE")
    Double getAverageOfItemsWithThatName(String name);
}
