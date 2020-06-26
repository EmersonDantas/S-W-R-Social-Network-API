package br.eti.emersondantas.api.rebel.builders;

import br.eti.emersondantas.api.rebel.Genre;
import br.eti.emersondantas.api.rebel.Item;
import br.eti.emersondantas.api.rebel.Location;
import br.eti.emersondantas.api.rebel.Rebel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class RebelBuilder {

    public static Rebel.Builder createRebel() throws ParseException {

        return Rebel.builder()
                .name("Leia Organa")
                .dateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1956-10-21"))
                .genre(Genre.toEnum("femi"))
                .galaxy("Via Láctea")
                .base("Echo")
                .location(new Location(0.0,0.0,"base"))
                .items(new ArrayList<>(Collections.singletonList(new Item("comida", 6, 1))))
                .denunciations(0);
    }

}
