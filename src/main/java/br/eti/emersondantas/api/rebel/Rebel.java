package br.eti.emersondantas.api.rebel;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.NonNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Rebel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rebel_generator")
    @SequenceGenerator(name="rebel_generator", sequenceName = "rebel_sequence", allocationSize=1)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private Date dateOfBirth;

    @NonNull
    private String genre;

    @NonNull
    private Double latitude;

    @NonNull
    private Double longitude;

    @NonNull
    private String galaxy;

    @NonNull
    private String base;
}
