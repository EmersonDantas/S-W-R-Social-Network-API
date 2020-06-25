package br.eti.emersondantas.api.rebel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Genre {
    feminine("femi", "feminine"),
    male("male", "male");

    private String cod;
    private String description;

    public static Genre toEnum(String cod) {
        if(cod == null) {
            return null;
        }

        for(Genre x : Genre.values()) {
            if(cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Invalid ID: " + cod);
    }
}

