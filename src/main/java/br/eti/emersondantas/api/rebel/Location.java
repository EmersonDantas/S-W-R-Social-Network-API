package br.eti.emersondantas.api.rebel;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Embeddable
public class Location implements Serializable {

    private static final long serialVersionUID = 5422429712536251527L;

    @ApiModelProperty(notes = "Rebel location latitude", name = "latitude")
    private Double latitude;

    @ApiModelProperty(notes = "Rebel location longitude", name = "longitude")
    private Double longitude;

    @ApiModelProperty(notes = "Rebel location name", name = "locationName")
    private String locationName;
}
