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
import static org.mockito.ArgumentMatchers.anyString;
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
        Rebel rebelFrom = createRebel().id("1").build();

        List<Item> itemsRebelFrom = new ArrayList<>(Arrays.asList(
                new Item("1", "comida", 6, 1, rebelFrom)
        ));

        rebelFrom.setItems(itemsRebelFrom);

        Rebel rebelTo = createRebel().id("2").build();

        List<Item> itemsRebelTo = new ArrayList<>(Arrays.asList(
                new Item("2", "arma", 1, 4, rebelTo),
                new Item("3", "agua", 1, 2, rebelTo)
        ));

        rebelTo.setItems(itemsRebelTo);

        List<Item> itemsFrom = new ArrayList<>(Arrays.asList(
                new Item("1", "comida", 6, 1, rebelFrom)
        ));

        List<Item> itemsTo = new ArrayList<>(Arrays.asList(
                new Item("2", "arma", 1, 4, rebelTo),
                new Item("3", "agua", 1, 2, rebelTo)
        ));


        when(this.rebelRepository.findById("1")).thenReturn(Optional.of(rebelFrom));
        when(this.rebelRepository.findById("2")).thenReturn(Optional.of(rebelTo));

        this.negotiateItemsService.negotiateItems("1", "2", itemsFrom, itemsTo);

        ArgumentCaptor<Item> argumentCaptor = ArgumentCaptor.forClass(Item.class);
        verify(this.itemRepository, times(6)).save(argumentCaptor.capture());

        List<Item> result = argumentCaptor.getAllValues();

        assertAll("rebel",
                () -> assertThat(result.get(0).getRebel().getId(), is("2")),
                () -> assertThat(result.get(1).getRebel().getId(), is("1")),
                () -> assertThat(result.get(2).getRebel().getId(), is("2")),
                () -> assertThat(result.get(3).getRebel().getId(), is("1")),
                () -> assertThat(result.get(4).getRebel().getId(), is("1")),
                () -> assertThat(result.get(5).getRebel().getId(), is("2"))
        );
    }

    @Test
    @DisplayName("make items negotiation with except")
    void shouldThrowRebelNotFoundException(){
        when(this.rebelRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(RebelNotFoundException.class, () -> this.negotiateItemsService.negotiateItems("1", "2", createRebel().build().getItems(), null));
    }
}
