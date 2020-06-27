package br.eti.emersondantas.api.rebel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString()
@Builder(builderClassName = "Builder")
@Entity
public class Item implements Serializable {

    private static final long serialVersionUID = -2660202316072114081L;

    @ApiModelProperty(notes = "Item primary key", name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_generator")
    @SequenceGenerator(name = "item_generator", sequenceName = "item_sequence", allocationSize = 1)
    private Long id;

    @ApiModelProperty(notes = "Item name", name = "name", required = true)
    @NonNull
    private String name;

    @ApiModelProperty(notes = "Amount of this item", name = "amount", required = true)
    @NonNull
    private int amount;

    @ApiModelProperty(notes = "Points of this item", name = "points", required = true)
    @NonNull
    private int points;

    @ApiModelProperty(notes = "Rebel owner of this item", name = "rebel", required = true)
    @ManyToOne
    @JoinColumn(name="rebel_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Rebel rebel;

}
