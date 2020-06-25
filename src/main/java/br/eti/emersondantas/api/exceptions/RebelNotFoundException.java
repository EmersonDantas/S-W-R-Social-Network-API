package br.eti.emersondantas.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RebelNotFoundException extends RuntimeException{
    public RebelNotFoundException(){
        super("Rebel not found!");
    }
}
