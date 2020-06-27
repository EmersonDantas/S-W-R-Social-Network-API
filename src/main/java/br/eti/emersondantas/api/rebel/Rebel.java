package br.eti.emersondantas.api.rebel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.NonNull;
import lombok.ToString;

import org.springframework.data.domain.Page;

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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rebel_generator")
    @SequenceGenerator(name = "rebel_generator", sequenceName = "rebel_sequence", allocationSize = 1)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @NonNull
    private Genre genre;

    @NonNull
    private String galaxy;

    @NonNull
    private String base;

    @Embedded
    private Location location;

    @NotNull
    @NonNull
    @OneToMany(mappedBy="rebel", fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>();

    private int denunciations;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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


