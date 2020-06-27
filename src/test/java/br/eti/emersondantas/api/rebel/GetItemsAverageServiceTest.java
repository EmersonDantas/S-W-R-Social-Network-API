package br.eti.emersondantas.api.rebel;

import br.eti.emersondantas.api.rebel.services.GetItemsAverageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Test get items average relatory")
public class GetItemsAverageServiceTest {

    @Mock
    private ItemRepository itemRepository;

    private GetItemsAverageServiceImpl getItemsAverageService;

    @BeforeEach
    public void setUp(){
        this.getItemsAverageService = new GetItemsAverageServiceImpl(itemRepository);
    }

    /**
     * Returns the number of lost points by renegades
     */
    @Test
    @DisplayName("get items amount average")
    void shouldReturnItemsAverage(){
        when(this.itemRepository.getAllDistinctName()).thenReturn(new ArrayList<>(Arrays.asList("comida")));
        when(this.itemRepository.getAverageOfItemsWithThatName("comida")).thenReturn(6.0);

        HashMap<String, Double> map = this.getItemsAverageService.getItemsAverage();

        assertAll(
          "lost-points",
                () -> assertThat(map.get("comida"), is(6.0))
        );
    }

}
