package br.eti.emersondantas.api.rebel;

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

    private Double latitude;

    private Double longitude;

    private String locationName;
}
