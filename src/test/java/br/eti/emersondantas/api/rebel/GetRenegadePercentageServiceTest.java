package br.eti.emersondantas.api.rebel;

import br.eti.emersondantas.api.rebel.services.GetRenegadePercentageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Tests whether the renegade percentage report is correct")
public class GetRenegadePercentageServiceTest {

    @Mock
    private RebelRepository rebelRepository;
    private GetRenegadePercentageServiceImpl getRenegadePercentageService;

    @BeforeEach
    public void setUp(){
        this.getRenegadePercentageService = new GetRenegadePercentageServiceImpl(rebelRepository);
    }

    /**
     * Returns the percentage of renegades rebels
     */
    @Test
    @DisplayName("get percentage test")
    void shouldReturnRenegadePercentage(){
        when(this.rebelRepository.countByDenunciationsGreaterThanEqual(anyInt())).thenReturn(1L);
        when(this.rebelRepository.count()).thenReturn(2L);

        Double percentageResult = this.getRenegadePercentageService.getRenegadePercentage();

        assertAll(
          "percentage",
                () -> assertThat(percentageResult, is(50.0))
        );
    }

}
