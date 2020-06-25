package br.eti.emersondantas.api.rebel;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @NotEmpty
    @NonNull
    private Long id;

    @NotEmpty
    @NonNull
    private String name;

    @NotEmpty
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @NotEmpty
    @NonNull
    private String genre;

    @NotEmpty
    @NonNull
    private String galaxy;

    @NotEmpty
    @NonNull
    private String base;

    @Embedded
    private Location location;

    @NonNull
    private List<Item> items;

    private int denunciations;

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
                .build();
    }

    public static List<RebelDTO> from(List<Rebel> rebels){
        return rebels.stream().map(RebelDTO::from).collect(Collectors.toList());
    }

    public static Page<RebelDTO> from(Page<Rebel> pages){
        return pages.map(RebelDTO::from);
    }

}


