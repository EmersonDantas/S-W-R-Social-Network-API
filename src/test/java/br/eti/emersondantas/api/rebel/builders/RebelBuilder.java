package br.eti.emersondantas.api.rebel.builders;

import br.eti.emersondantas.api.rebel.Genre;
import br.eti.emersondantas.api.rebel.Item;
import br.eti.emersondantas.api.rebel.Location;
import br.eti.emersondantas.api.rebel.Rebel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class RebelBuilder {

    public static Rebel.Builder createRebel() throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("1956-10-21");
        return Rebel.builder()
                .name("Leia Organa")
                .dateOfBirth(date)
                .genre(Genre.toEnum("femi"))
                .galaxy("Via Láctea")
                .base("Echo")
                .location(new Location(0.0,0.0,"base"))
                .items(new ArrayList<>(Arrays.asList(new Item("comida", 6, 1))))
                .denunciations(0);
    }

}
