package br.eti.emersondantas.api.rebel;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface RebelRepository extends CrudRepository<Rebel, String> {
    @Query("SELECT COUNT(r) FROM Rebel r WHERE r.isRenegade = true")
    Long countAllRenegades();

    Page<Rebel> findAll(Pageable pageable);
}
