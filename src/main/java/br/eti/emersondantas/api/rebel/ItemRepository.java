package br.eti.emersondantas.api.rebel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT SUM(i.points * i.amount) FROM Item i WHERE i.rebel.isRenegade = true")
    Long getLostPointsByRenegades();
}
