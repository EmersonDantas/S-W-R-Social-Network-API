package br.eti.emersondantas.api.rebel;

import br.eti.emersondantas.api.rebel.services.GetItemsAverageService;
import br.eti.emersondantas.api.rebel.services.GetLostPointsByRenegadesService;
import br.eti.emersondantas.api.rebel.services.GetRebelService;
import br.eti.emersondantas.api.rebel.services.GetRenegadePercentageService;
import br.eti.emersondantas.api.rebel.services.ListRebelService;
import br.eti.emersondantas.api.rebel.services.NegotiateItemsService;
import br.eti.emersondantas.api.rebel.services.ReportRenegadeRebelService;
import br.eti.emersondantas.api.rebel.services.SaveRebelService;
import br.eti.emersondantas.api.rebel.services.UpdateRebelLocationService;
import br.eti.emersondantas.api.rebel.v1.RebelControllerV1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RebelControllerV1.class)
@Tag("controller")
@DisplayName("Check all rebel v1 controller endpoints")
public class RebelControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetLostPointsByRenegadesService getLostPointsByRenegadesService;

    @MockBean
    private GetRebelService getRebelService;

    @MockBean
    private GetRenegadePercentageService getRenegadePercentageService;

    @MockBean
    private ListRebelService listRebelService;

    @MockBean
    private NegotiateItemsService negotiateItemsService;

    @MockBean
    private ReportRenegadeRebelService reportRenegadeRebelService;

    @MockBean
    private SaveRebelService saveRebelService;

    @MockBean
    private UpdateRebelLocationService updateRebelLocationService;

    @MockBean
    private GetItemsAverageService getItemsAverageService;

    @Test
    @DisplayName("Test /api/v1/renegades-lost-points")
    void mustReturnLostPointsByRenegades() throws Exception {
        when(this.getLostPointsByRenegadesService.getLostPointsByRenegades()).thenReturn(6L);

        this.mockMvc.perform(get("/api/v1/rebels/renegades-lost-points")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("lostPointsByRenegades", is(6)));
    }

}
