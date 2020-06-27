package br.eti.emersondantas.api.rebel;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(notes = "Rebel negotiator items", name = "itemsFrom")
    private List<Item> itemsFrom = new ArrayList<>();

    @ApiModelProperty(notes = "Items of the rebel who wants to negotiate", name = "itemsTo")
    private List<Item> itemsTo = new ArrayList<>();
}
