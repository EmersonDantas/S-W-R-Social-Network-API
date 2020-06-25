package br.eti.emersondantas.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ItemsHaveDifferentScoresException extends RuntimeException{
    public ItemsHaveDifferentScoresException(){
        super("Items have different scores.");
    }
}
