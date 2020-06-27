package br.eti.emersondantas.api.rebel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RebelRepository extends JpaRepository<Rebel, Long> {
    @Query("SELECT COUNT(r) FROM Rebel r WHERE r.isRenegade = true")
    Long countAllRenegades();
}
