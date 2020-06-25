package br.eti.emersondantas.api.rebel;

import lombok.AllArgsConstructor;
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
@Embeddable
public class Location implements Serializable {

    private Double latitude;

    private Double longitude;

    private String locationName;
}
