package br.eti.emersondantas.api.rebel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RebelRepository extends JpaRepository<Rebel, Long> {
    Page<Rebel> findAllByDenunciationsLessThanEqual(Pageable pageable, int denunciationsValue);

    Long countByDenunciationsGreaterThanEqual(int denunciationsValue);
}
