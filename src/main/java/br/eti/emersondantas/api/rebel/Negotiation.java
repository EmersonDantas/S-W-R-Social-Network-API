package br.eti.emersondantas.api.rebel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Negotiation implements Serializable {

    private static final long serialVersionUID = 5054504208217649675L;

    private List<Item> itemsFrom = new ArrayList<>();

    private List<Item> itemsTo = new ArrayList<>();
}
