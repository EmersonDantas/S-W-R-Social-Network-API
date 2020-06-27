package br.eti.emersondantas.api.rebel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT SUM(i.points * i.amount) FROM Item i WHERE i.rebel.isRenegade = true")
    Long getLostPointsByRenegades();

    @Query("SELECT DISTINCT(i.name) FROM Item i")
    List<String> getAllDistinctName();

    @Query("SELECT AVG(i.amount) FROM Item i WHERE i.name = ?1")
    Long getAverageOfItemsWithThatName(String name);
}
