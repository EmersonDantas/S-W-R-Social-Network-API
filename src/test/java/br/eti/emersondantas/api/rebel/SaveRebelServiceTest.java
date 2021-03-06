package br.eti.emersondantas.api.rebel;

import br.eti.emersondantas.api.rebel.services.SaveRebelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;

import static br.eti.emersondantas.api.rebel.builders.RebelBuilder.createRebel;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Test save rebel function")
public class SaveRebelServiceTest {

    @Mock
    private RebelRepository rebelRepository;

    @Mock
    private ItemRepository itemRepository;

    private SaveRebelServiceImpl saveRebelService;

    @BeforeEach
    public void setUp() {
        this.saveRebelService = new SaveRebelServiceImpl(this.rebelRepository, this.itemRepository);
    }

    @Test
    @DisplayName("save a rebel test")
    void shouldSaveRebel() throws ParseException {
        this.saveRebelService.save(createRebel().build());

        ArgumentCaptor<Rebel> argumentCaptor = ArgumentCaptor.forClass(Rebel.class); //Create a argumentCaptor
        verify(rebelRepository).save(argumentCaptor.capture()); // capture rebel when .save as executed

        Rebel rebelResult = argumentCaptor.getValue(); // get rebel value captured

        assertAll("rebel",
                () -> assertThat(rebelResult.getName(), is("Leia Organa"))
        );
    }
}
