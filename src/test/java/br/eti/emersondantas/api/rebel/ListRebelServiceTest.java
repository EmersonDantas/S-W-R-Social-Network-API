package br.eti.emersondantas.api.rebel;

import br.eti.emersondantas.api.rebel.services.ListRebelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import static br.eti.emersondantas.api.rebel.builders.RebelBuilder.createRebel;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Tests the list function, which should return an page counting rebels")
public class ListRebelServiceTest {

    @Mock
    private RebelRepository rebelRepository;
    private ListRebelServiceImpl listRebelService;

    @BeforeEach
    public void setUp(){
        this.listRebelService = new ListRebelServiceImpl(rebelRepository);
    }

    /**
     * Should return an page counting rebels
     */
    @Test
    @DisplayName("get rebel paged")
    void getPageOfRebels() throws ParseException {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.fromString("ASC"), "id"));
        when(this.listRebelService.list(pageable)).thenReturn(
                new PageImpl<>(new ArrayList<>(Arrays.asList(createRebel().name("Chewbacca").build(), createRebel().build())))
        );

        Page<Rebel> rebelPageResult = this.listRebelService.list(pageable);

        assertAll(
          "rebels page",
                () -> assertThat(rebelPageResult.getTotalElements(), is(2L)),
                () -> assertThat(rebelPageResult.getNumber(), is(0)),
                () -> assertThat(rebelPageResult.getTotalPages(), is(1)),
                () -> assertThat(rebelPageResult.getContent().get(0).getName(), is("Chewbacca")),
                () -> assertThat(rebelPageResult.getContent().get(1).getName(), is("Leia Organa"))
        );
    }

}
