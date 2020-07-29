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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static br.eti.emersondantas.api.rebel.builders.RebelBuilder.createRebel;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
                .andExpect(jsonPath("lostPointsByRenegades", is(6)))
                .andDo(print());
    }

    @Test
    @DisplayName("Test /api/v1/rebels/{id}")
    void mustReturnARebel() throws Exception {
        when(this.getRebelService.get(anyString())).thenReturn(createRebel().id("1").build());

        this.mockMvc.perform(get("/api/v1/rebels/{id}", "1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andDo(print());
    }

    @Test
    @DisplayName("Test /api/v1/rebels/renegade-percentage")
    void mustReturnRenegadePercentage() throws Exception {
        when(this.getRenegadePercentageService.getRenegadePercentage()).thenReturn(50.0);

        this.mockMvc.perform(get("/api/v1/rebels/renegade-percentage")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("renegadesPercentage", is(50.0)))
                .andDo(print());
    }

    @Test
    @DisplayName("Test list rebels /api/v1/rebels")
    void mustReturnRebelList() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.fromString("ASC"), "id"));
        when(this.listRebelService.list(pageable)).thenReturn(
                new PageImpl<>(new ArrayList<>(Arrays.asList(createRebel().id("1").name("Chewbacca").build(), createRebel().id("2").build())))
        );

        this.mockMvc.perform(get("/api/v1/rebels")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements", is(2)))
                .andExpect(jsonPath("$.content[0].name", is("Chewbacca")))
                .andDo(print());
    }

    @Test
    @DisplayName("Test /api/v1/rebels/report-rebel/{id}")
    void mustReportARenegadeRebel() throws Exception {
        when(this.getRebelService.get(anyString())).thenReturn(createRebel().id("1").denunciations(2).build());
        this.reportRenegadeRebelService.report("1");

        this.mockMvc.perform(patch("/api/v1/rebels/report-rebel/{id}", 1))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Test create rebel /api/v1/rebels")
    void mustCreateARebel() throws Exception {
        this.mockMvc.perform(post("/api/v1/rebels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson("RebelPost.json")))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test /api/v1/rebels/location/{id}")
    void mustUpdateARebelLocation() throws Exception {
        when(this.getRebelService.get(anyString())).thenReturn(createRebel().id("1").build());

        this.mockMvc.perform(patch("/api/v1/rebels/location/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson("LocateUpdatePath.json")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test /api/v1/rebels/items-average")
    void mustReturnItemsAverage() throws Exception {
        HashMap<String, Double> map = new HashMap<String, Double>();
        map.put("comida", 6.0);
        when(this.getItemsAverageService.getItemsAverage()).thenReturn(map);

        this.mockMvc.perform(get("/api/v1/rebels/items-average")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("comida", is(6.0)))
                .andDo(print());
    }

    public static String getJson(String fileName) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/java/resources/json/"+fileName).toAbsolutePath());
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
