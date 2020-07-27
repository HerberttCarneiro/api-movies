package ais.movies.dto;

import ais.movies.model.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class MovieCreationRequestDTO {

    @NotEmpty
    private String title;

    private Date releaseData;

    @NotEmpty
    private List<Genre> genres;
}
