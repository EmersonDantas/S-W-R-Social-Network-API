package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.exceptions.RebelNotFoundException;
import br.eti.emersondantas.api.rebel.Rebel;
import br.eti.emersondantas.api.rebel.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportRenegadeRebelServiceImpl implements ReportRenegadeRebelService{

    private final RebelRepository rebelRepository;

    @Override
    public void report(Long id) {
        Rebel rebel = this.rebelRepository.findById(id).orElseThrow(RebelNotFoundException::new);
        rebel.setDenunciations(rebel.getDenunciations()+1);
        this.rebelRepository.save(rebel);
    }
}
