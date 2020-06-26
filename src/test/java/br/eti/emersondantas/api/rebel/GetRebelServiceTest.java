package br.eti.emersondantas.api.rebel;

import br.eti.emersondantas.api.exceptions.RebelNotFoundException;
import br.eti.emersondantas.api.rebel.services.GetRebelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static br.eti.emersondantas.api.rebel.builders.RebelBuilder.createRebel;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Tests the get function, which should return the rebel who has the sent id, if it exists")
public class GetRebelServiceTest {

    @Mock
    private RebelRepository rebelRepository;

    private GetRebelServiceImpl getRebelService;

    @BeforeEach
    public void setUp(){
        this.getRebelService = new GetRebelServiceImpl(rebelRepository);
    }

    /**
     * A rebel named Chewbacca must return
     * @throws ParseException
     */
    @Test
    @DisplayName("get rebel successfully")
    void shouldReturnRebelWithThisId() throws ParseException {
        when(this.rebelRepository.findById(anyLong())).thenReturn(
                Optional.of(createRebel().name("Chewbacca").genre(Genre.toEnum("male")).build())
        );

        Rebel rebelResult = this.getRebelService.get(1L);

        assertAll(
          "rebel",
                () -> assertThat(rebelResult.getName(), is("Chewbacca")),
                () -> assertThat(rebelResult.getDateOfBirth(), is(new SimpleDateFormat("yyyy-MM-dd").parse("1956-10-21"))),
                () -> assertThat(rebelResult.getGenre(), is(Genre.toEnum("male"))),
                () -> assertThat(rebelResult.getGalaxy(), is("Via Láctea")),
                () -> assertThat(rebelResult.getBase(), is("Echo")),
                () -> assertThat(rebelResult.getLocation(), is(new Location(0.0,0.0,"base"))),
                () -> assertThat(rebelResult.getDenunciations(), is(0))
        );
    }

    /**
     * Must throw exception for not finding the rebel with the sent id
     */
    @Test
    @DisplayName("get rebel with except")
    void shouldThrowRebelNotFoundException(){
        when(this.rebelRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RebelNotFoundException.class, () -> this.getRebelService.get(1L));
    }
}
