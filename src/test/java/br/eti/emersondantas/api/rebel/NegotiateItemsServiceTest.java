package br.eti.emersondantas.api.rebel;

import br.eti.emersondantas.api.exceptions.RebelNotFoundException;
import br.eti.emersondantas.api.rebel.services.GetRebelServiceImpl;
import br.eti.emersondantas.api.rebel.services.NegotiateItemsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static br.eti.emersondantas.api.rebel.builders.RebelBuilder.createRebel;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Test items negotiation function")
public class NegotiateItemsServiceTest {

    @Mock
    private RebelRepository rebelRepository;

    @Mock
    private ItemRepository itemRepository;

    private NegotiateItemsServiceImpl negotiateItemsService;

    private GetRebelServiceImpl getRebelService;

    @BeforeEach
    public void setUp() {
        this.negotiateItemsService = new NegotiateItemsServiceImpl(this.rebelRepository, itemRepository);
        this.getRebelService = new GetRebelServiceImpl(this.rebelRepository);
    }

    @Test
    @DisplayName("make a item negotiation test successfully")
    void shouldMakeANegotiation() throws ParseException { //TODO: Refactoring
        Rebel rebelFrom = createRebel().id(1L).build();

        List<Item> itemsRebelFrom = new ArrayList<>(Arrays.asList(
                new Item(1L, "comida", 6, 1, rebelFrom)
        ));

        rebelFrom.setItems(itemsRebelFrom);

        Rebel rebelTo = createRebel().id(2L).build();

        List<Item> itemsRebelTo = new ArrayList<>(Arrays.asList(
                new Item(2L, "arma", 1, 4, rebelTo),
                new Item(3L, "agua", 1, 2, rebelTo)
        ));

        rebelTo.setItems(itemsRebelTo);

        List<Item> itemsFrom = new ArrayList<>(Arrays.asList(
                new Item(1L, "comida", 6, 1, rebelFrom)
        ));

        List<Item> itemsTo = new ArrayList<>(Arrays.asList(
                new Item(2L, "arma", 1, 4, rebelTo),
                new Item(3L, "agua", 1, 2, rebelTo)
        ));


        when(this.rebelRepository.findById(1L)).thenReturn(Optional.of(rebelFrom));
        when(this.rebelRepository.findById(2L)).thenReturn(Optional.of(rebelTo));

        this.negotiateItemsService.negotiateItems(1L, 2L, itemsFrom, itemsTo);

        ArgumentCaptor<Item> argumentCaptor = ArgumentCaptor.forClass(Item.class);
        verify(this.itemRepository, times(6)).save(argumentCaptor.capture());

        List<Item> result = argumentCaptor.getAllValues();

        assertAll("rebel",
                () -> assertThat(result.get(0).getRebel().getId(), is(2L)),
                () -> assertThat(result.get(1).getRebel().getId(), is(1L)),
                () -> assertThat(result.get(2).getRebel().getId(), is(2L)),
                () -> assertThat(result.get(3).getRebel().getId(), is(1L)),
                () -> assertThat(result.get(4).getRebel().getId(), is(1L)),
                () -> assertThat(result.get(5).getRebel().getId(), is(2L))
        );
    }

    @Test
    @DisplayName("make items negotiation with except")
    void shouldThrowRebelNotFoundException(){
        when(this.rebelRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RebelNotFoundException.class, () -> this.negotiateItemsService.negotiateItems(1L, 2L, createRebel().build().getItems(), null));
    }
}
