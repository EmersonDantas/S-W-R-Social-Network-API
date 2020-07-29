package br.eti.emersondantas.api.rebel.v1;

import br.eti.emersondantas.api.rebel.Location;
import br.eti.emersondantas.api.rebel.Negotiation;
import br.eti.emersondantas.api.rebel.Rebel;
import br.eti.emersondantas.api.rebel.RebelDTO;
import br.eti.emersondantas.api.rebel.services.GetItemsAverageService;
import br.eti.emersondantas.api.rebel.services.GetLostPointsByRenegadesService;
import br.eti.emersondantas.api.rebel.services.GetRebelService;
import br.eti.emersondantas.api.rebel.services.GetRenegadePercentageService;
import br.eti.emersondantas.api.rebel.services.ListRebelService;
import br.eti.emersondantas.api.rebel.services.NegotiateItemsService;
import br.eti.emersondantas.api.rebel.services.ReportRenegadeRebelService;
import br.eti.emersondantas.api.rebel.services.SaveRebelService;
import br.eti.emersondantas.api.rebel.services.UpdateRebelLocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@Api(value = "Rebel")
@RequestMapping(value = "/api/v1/rebels")
public class RebelControllerV1 {

    private final GetRebelService getRebelService;

    private final ListRebelService listRebelService;

    private final ReportRenegadeRebelService reportRenegadeRebelService;

    private final SaveRebelService saveRebelService;

    private final UpdateRebelLocationService updateRebelLocationService;

    private final NegotiateItemsService negotiateItemsService;

    private final GetRenegadePercentageService getRenegadePercentageService;

    private final GetLostPointsByRenegadesService getLostPointsByRenegadesService;

    private final GetItemsAverageService getItemsAverageService;

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Returns rebel that has the received id if it exists.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Rebel found!"),
            @ApiResponse(code = 404, message = "Rebel not found!")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RebelDTO get(@ApiParam(value = "Wanted rebel id", required = true, example = "1") @PathVariable("id") String id) {
        return RebelDTO.from(this.getRebelService.get(id));
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Returns all rebels, paged or unpaged.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success!")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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
    @ApiOperation(value = "report as a renegade the rebel who has the sent id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Report received."),
            @ApiResponse(code = 204, message = "Rebel not found!")
    })
    @PatchMapping(value = "report-rebel/{id}")
    public void report(@ApiParam(value = "Reported rebel id", required = true, example = "1") @PathVariable("id") String id) {
        this.reportRenegadeRebelService.report(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "Save a new rebel.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Rebel created!"),
            @ApiResponse(code = 400, message = "Errors in the rebel's attributes.")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@Valid @RequestBody RebelDTO rebelDto) {
        this.saveRebelService.save(Rebel.to(rebelDto));
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Updates the location of the rebel who has the sent id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Location updated!"),
            @ApiResponse(code = 404, message = "Rebel not found!")
    })
    @PatchMapping(value = "location/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateLocation(@PathVariable("id") String id, @RequestBody Location location) {
        this.updateRebelLocationService.updateLocation(id, location);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Performs a fair exchange of items between non-traitorous rebels.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful negotiation!"),
            @ApiResponse(code = 404, message = "Rebel not found!"),
            @ApiResponse(code = 400, message = "Unfair or invalid trading!")
    })
    @PatchMapping(value = "negotiate-items/{id-from}/{id-to}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void negotiateItems(@ApiParam(value = "Rebel negotiator id", required = true, example = "1") @PathVariable("id-from") String idFrom,
                               @ApiParam(value = "Rebel who wants to negotiate id", required = true, example = "1") @PathVariable("id-to") String idTo,
                               @ApiParam(value = "Negotiation items, contains items from the negotiator and the receiver", required = true) @RequestBody Negotiation negotiation) {
        this.negotiateItemsService.negotiateItems(idFrom, idTo, negotiation.getItemsFrom(), negotiation.getItemsTo());
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Returns percents of renegade rebels.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success!")
    })
    @GetMapping(value = "renegade-percentage", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Double> getRenegadePercentage() {
        Double renegadePercentage = this.getRenegadePercentageService.getRenegadePercentage();
        HashMap<String, Double> map = new HashMap<>();
        map.put("renegadesPercentage", renegadePercentage);
        return map;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Returns percents of rebels.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success!")
    })
    @GetMapping(value = "rebels-percentage", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Double> getRebelsPercentage() {
        Double renegadePercentage = this.getRenegadePercentageService.getRenegadePercentage();
        Double rebelPercentage = 100.0 - renegadePercentage;
        HashMap<String, Double> map = new HashMap<>();
        map.put("rebelsPercentage", rebelPercentage);
        return map;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Returns lost points by renegades.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success!")
    })
    @GetMapping(value = "renegades-lost-points", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Long> getRenegadeLostPoints() {
        this.getItemsAverageService.getItemsAverage();
        Long lostPoints = this.getLostPointsByRenegadesService.getLostPointsByRenegades();
        HashMap<String, Long> map = new HashMap<>();
        map.put("lostPointsByRenegades", lostPoints);
        return map;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "Returns average of amount items.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success!")
    })
    @GetMapping(value = "items-average", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Double> getAverangeOfItems() {
        return this.getItemsAverageService.getItemsAverage();
    }
}
