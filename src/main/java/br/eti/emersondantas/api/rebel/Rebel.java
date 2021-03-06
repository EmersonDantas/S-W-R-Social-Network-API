package br.eti.emersondantas.api.rebel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.NonNull;
import lombok.ToString;

import org.springframework.data.domain.Page;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
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
@Entity
public class Rebel implements Serializable {

    private static final long serialVersionUID = -7988612442966286011L;

    @ApiModelProperty(notes = "Rebel primary key", name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rebel_generator")
    @SequenceGenerator(name = "rebel_generator", sequenceName = "rebel_sequence", allocationSize = 1)
    private Long id;

    @ApiModelProperty(notes = "Rebel name", name = "name", required = true)
    @NonNull
    @Column(nullable = false)
    private String name;

    @ApiModelProperty(notes = "Rebel birth date", name = "dateOfBirth", required = true)
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date dateOfBirth;

    @ApiModelProperty(notes = "Rebel genre", name = "genre", required = true)
    @NonNull
    @Column(nullable = false)
    private Genre genre;

    @ApiModelProperty(notes = "Rebel galaxy", name = "galaxy", required = true)
    @NonNull
    @Column(nullable = false)
    private String galaxy;

    @ApiModelProperty(notes = "Rebel base", name = "base", required = true)
    @NonNull
    @Column(nullable = false)
    private String base;

    @ApiModelProperty(notes = "Rebel location", name = "location")
    @Embedded
    private Location location;

    @ApiModelProperty(notes = "Rebel items", name = "items", required = true)
    @NotNull
    @NonNull
    @OneToMany(mappedBy="rebel", fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>();

    @ApiModelProperty(notes = "Rebel renegade denunciations", name = "denunciations")
    @Column(nullable = false)
    private int denunciations;

    @ApiModelProperty(notes = "Rebel renegade status", name = "isRenegade")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false)
    private boolean isRenegade;

    public static Rebel to(RebelDTO rebelDTO){
        return Rebel.builder()
                .id(rebelDTO.getId())
                .name(rebelDTO.getName())
                .dateOfBirth(rebelDTO.getDateOfBirth())
                .genre(Genre.toEnum(rebelDTO.getGenre()))
                .galaxy(rebelDTO.getGalaxy())
                .base(rebelDTO.getBase())
                .location(rebelDTO.getLocation())
                .items(rebelDTO.getItems())
                .denunciations(rebelDTO.getDenunciations())
                .isRenegade(rebelDTO.isRenegade())
                .build();
    }

    public static List<Rebel> to(List<RebelDTO> rebels){
        return rebels.stream().map(Rebel::to).collect(Collectors.toList());
    }

    public static Page<Rebel> to(Page<RebelDTO> pages){
        return pages.map(Rebel::to);
    }

    public void markAsRenegade(){
        this.isRenegade = true;
    }

}


