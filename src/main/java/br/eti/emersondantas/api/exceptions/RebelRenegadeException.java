package br.eti.emersondantas.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class RebelRenegadeException extends RuntimeException{
    public RebelRenegadeException(){
        super("This rebel is a renegade, and therefore cannot perform this action.");
    }
}
