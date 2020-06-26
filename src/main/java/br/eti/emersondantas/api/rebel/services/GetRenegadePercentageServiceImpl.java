package br.eti.emersondantas.api.rebel.services;

import br.eti.emersondantas.api.rebel.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetRenegadePercentageServiceImpl implements GetRenegadePercentageService{

    private final RebelRepository rebelRepository;

    private final int RENEGADE_VALUE = 3;

    @Override
    public Double getRenegadePercentage() {
        Long total = this.rebelRepository.count();
        Long renegades = this.rebelRepository.countByDenunciationsGreaterThanEqual(RENEGADE_VALUE);
        return (total > 0 ? ((renegades / (total+0.0)) * 100.0): 0);
    }
}
