package br.eti.emersondantas.api.rebel;

import br.eti.emersondantas.api.exceptions.RebelNotFoundException;
import br.eti.emersondantas.api.rebel.services.UpdateRebelLocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.Optional;

import static br.eti.emersondantas.api.rebel.builders.RebelBuilder.createRebel;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Test update rebel location function")
public class UpdateRebelLocationServiceTest {

    @Mock
    private RebelRepository rebelRepository;

    private UpdateRebelLocationServiceImpl updateRebelLocationService;

    @BeforeEach
    public void setUp() {
        this.updateRebelLocationService = new UpdateRebelLocationServiceImpl(this.rebelRepository);
    }

    @Test
    @DisplayName("update a rebel location test successfully")
    void shouldUpdateRebelLocation() throws ParseException {
        when(this.rebelRepository.findById(1L)).thenReturn(Optional.of(createRebel().build()));

        this.updateRebelLocationService.updateLocation(1L, new Location(1.0,1.0,"updated"));

        ArgumentCaptor<Rebel> argumentCaptor = ArgumentCaptor.forClass(Rebel.class);
        verify(rebelRepository).save(argumentCaptor.capture());

        Rebel rebelResult = argumentCaptor.getValue();

        assertAll("rebel",
                () -> assertThat(rebelResult.getLocation().getLatitude(), is(1.0)),
                () -> assertThat(rebelResult.getLocation().getLongitude(), is(1.0)),
                () -> assertThat(rebelResult.getLocation().getLocationName(), is("updated"))
        );
    }

    @Test
    @DisplayName("update rebel location with except")
    void shouldThrowRebelNotFoundException(){
        when(this.rebelRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RebelNotFoundException.class, () -> this.updateRebelLocationService.updateLocation(1L, new Location(1.0,1.0,"updated")));
    }
}
