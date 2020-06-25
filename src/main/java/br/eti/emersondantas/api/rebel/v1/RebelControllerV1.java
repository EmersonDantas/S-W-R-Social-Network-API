package br.eti.emersondantas.api.rebel.v1;

import br.eti.emersondantas.api.rebel.Item;
import br.eti.emersondantas.api.rebel.Location;
import br.eti.emersondantas.api.rebel.Rebel;
import br.eti.emersondantas.api.rebel.RebelDTO;
import br.eti.emersondantas.api.rebel.services.GetRebelService;
import br.eti.emersondantas.api.rebel.services.ListRebelService;
import br.eti.emersondantas.api.rebel.services.NegotiateItemsService;
import br.eti.emersondantas.api.rebel.services.ReportRenegadeRebelService;
import br.eti.emersondantas.api.rebel.services.SaveRebelService;
import br.eti.emersondantas.api.rebel.services.UpdateRebelLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/rebels")
public class RebelControllerV1 {

    private final GetRebelService getRebelService;

    private final ListRebelService listRebelService;

    private final ReportRenegadeRebelService reportRenegadeRebelService;

    private final SaveRebelService saveRebelService;

    private final UpdateRebelLocationService updateRebelLocationService;

    private final NegotiateItemsService negotiateItemsService;

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(value = "/{id}")
    public RebelDTO get(@PathVariable("id") Long id) {
        return RebelDTO.from(this.getRebelService.get(id));
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping
    public Page<RebelDTO> list(
            @RequestParam(value = "isPaged", defaultValue = "true") boolean isPaged,
            @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "sort", defaultValue = "ASC") String sort) {
        if (isPaged) {
            return RebelDTO.from(this.listRebelService.list(PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.fromString(sort), ord))));
        } else {
            return RebelDTO.from(this.listRebelService.list(Pageable.unpaged()));
        }
    }

    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @PatchMapping(value = "report-rebel/{id}")
    public void report(@PathVariable("id") Long id) {
        this.reportRenegadeRebelService.report(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public void save(@Valid @RequestBody RebelDTO rebelDto) {
        this.saveRebelService.save(Rebel.to(rebelDto));
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PatchMapping(value = "location/{id}")
    public void updateLocation(@PathVariable("id") Long id, @RequestBody Location location){
        this.updateRebelLocationService.updateLocation(id, location);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PatchMapping(value = "negotiate-items/{id-from}/{id-to}")
    public void negotiateItems(@PathVariable("id-from") Long idFrom, @PathVariable("id-to") Long idTo, @RequestBody List<Item> itemsFrom, @RequestBody List<Item> itemsTo){
        this.negotiateItemsService.negotiateItems(idFrom, idTo, itemsFrom, itemsTo);
    }
}
