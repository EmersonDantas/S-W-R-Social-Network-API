package br.eti.emersondantas.api.rebel;

import br.eti.emersondantas.api.exceptions.RebelNotFoundException;
import br.eti.emersondantas.api.rebel.services.GetRebelServiceImpl;
import br.eti.emersondantas.api.rebel.services.ReportRenegadeRebelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Tests the report function, which should increase the counter of reports of the rebel who has the id sent")
public class ReportRenegadeServiceTest {

    @Mock
    private RebelRepository rebelRepository;

    private ReportRenegadeRebelServiceImpl reportRenegadeRebelService;

    private GetRebelServiceImpl getRebelService;

    @BeforeEach
    public void setUp(){
        this.reportRenegadeRebelService = new ReportRenegadeRebelServiceImpl(rebelRepository);
        this.getRebelService = new GetRebelServiceImpl(rebelRepository);
    }

    /**
     * Report the rebel with that id
     */
    @Test
    @DisplayName("report rebel successfully")
    void shouldReportRenegadeRebel() throws ParseException {
        when(this.rebelRepository.findById(anyLong())).thenReturn(
                Optional.of(createRebel().build())
        );

        this.reportRenegadeRebelService.report(1L);
        Rebel reportedRebel = this.getRebelService.get(1L);

        assertAll(
          "rebel",
                () -> assertThat(reportedRebel.getDenunciations(), is(1))
        );
    }

    /**
     * Must throw exception for not finding the rebel with the sent id
     */
    @Test
    @DisplayName("report rebel that does not exist - except")
    void shouldThrowRebelNotFoundException(){
        when(this.rebelRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RebelNotFoundException.class, () -> this.reportRenegadeRebelService.report(1L));
    }
}
