package br.eti.emersondantas.api.rebel;

import br.eti.emersondantas.api.rebel.services.GetLostPointsByRenegadesServiceImpl;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Test get lost points by renegade relatory")
public class GetLostPointsByRenegadesServiceTest {

    @Mock
    private ItemRepository itemRepository;

    private GetLostPointsByRenegadesServiceImpl getLostPointsByRenegadesService;

    @BeforeEach
    public void setUp(){
        this.getLostPointsByRenegadesService = new GetLostPointsByRenegadesServiceImpl(itemRepository);
    }

    /**
     * Returns the number of lost points by renegades
     */
    @Test
    @DisplayName("get lost points by renegades")
    void shouldReturnLostPointsByRenegades(){
        when(this.itemRepository.getLostPointsByRenegades()).thenReturn(6L);

        Long lostPoints = this.getLostPointsByRenegadesService.getLostPointsByRenegades();

        assertAll(
          "lost-points",
                () -> assertThat(lostPoints, is(6L))
        );
    }

}
