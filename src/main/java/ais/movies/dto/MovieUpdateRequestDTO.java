package ais.movies.dto;

import ais.movies.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.sql.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieUpdateRequestDTO {

    @Positive
    private Long id;

    @NotEmpty
    private String title;

    private Date releaseData;

    @NotEmpty
    private List<Genre> genres;
}
