package br.eti.emersondantas.api.rebel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import javax.persistence.Embedded;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"items"})
@Builder(builderClassName = "Builder")
public class RebelDTO implements Serializable {

    private static final long serialVersionUID = -157348842910553181L;

    @ApiModelProperty(notes = "Rebel primary key", name = "id", required = true)
    @NotEmpty
    @NonNull
    private Long id;

    @ApiModelProperty(notes = "Rebel name", name = "name", required = true)
    @NotEmpty
    @NonNull
    private String name;

    @ApiModelProperty(notes = "Rebel birth date", name = "dateOfBirth", required = true)
    @NotEmpty
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @ApiModelProperty(notes = "Rebel genre", name = "genre", required = true)
    @NotEmpty
    @NonNull
    private String genre;

    @ApiModelProperty(notes = "Rebel galaxy", name = "galaxy", required = true)
    @NotEmpty
    @NonNull
    private String galaxy;

    @ApiModelProperty(notes = "Rebel base", name = "base", required = true)
    @NotEmpty
    @NonNull
    private String base;

    @ApiModelProperty(notes = "Rebel location", name = "location")
    @Embedded
    private Location location;

    @ApiModelProperty(notes = "Rebel items", name = "items", required = true)
    @NonNull
    private List<Item> items;

    @ApiModelProperty(notes = "Rebel renegade denunciations", name = "denunciations")
    private int denunciations;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean isRenegade;

    public static RebelDTO from(Rebel rebel){
        return RebelDTO.builder()
                .id(rebel.getId())
                .name(rebel.getName())
                .dateOfBirth(rebel.getDateOfBirth())
                .genre(rebel.getGenre().getCod())
                .galaxy(rebel.getGalaxy())
                .base(rebel.getBase())
                .location(rebel.getLocation())
                .items(rebel.getItems())
                .denunciations(rebel.getDenunciations())
                .isRenegade(rebel.isRenegade())
                .build();
    }

    public static List<RebelDTO> from(List<Rebel> rebels){
        return rebels.stream().map(RebelDTO::from).collect(Collectors.toList());
    }

    public static Page<RebelDTO> from(Page<Rebel> pages){
        return pages.map(RebelDTO::from);
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getAge(){
        return ((new Date(System.currentTimeMillis()).getTime() - this.dateOfBirth.getTime()) /1000/60/60/24/30/12);
    }

    @JsonIgnore
    public boolean isRenegade(){
        return this.isRenegade;
    }

}


