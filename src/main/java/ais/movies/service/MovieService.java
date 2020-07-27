package ais.movies.service;

import ais.movies.dto.MovieCreationRequestDTO;
import ais.movies.dto.MovieUpdateRequestDTO;
import ais.movies.dto.RatingMovieRequestDTO;
import ais.movies.dto.VoteResponseDTO;
import ais.movies.model.Genre;
import ais.movies.model.Movie;
import ais.movies.model.Rating;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieService {
    @Inject
    GenreService genreService;
    @PersistenceContext
    private EntityManager entityManager;

    public List<Movie> getAll() {
        List<Movie> movies = entityManager.createNamedQuery("Movie.findAll", Movie.class).getResultList();
        this.setVotes(movies);

        return movies;
    }

    public Movie getById(Long id) {
        return entityManager.find(Movie.class, id);
    }

    public Movie create(MovieCreationRequestDTO movieCreationRequestDTO) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Movie movie = modelMapper.map(movieCreationRequestDTO, Movie.class);
        this.updateOrCreateGenre(movie);
        entityManager.persist(movie);

        return movie;
    }

    public Movie update(MovieUpdateRequestDTO movieUpdateRequestDTO, Long id) {
        Movie MovieToUpdate = entityManager.find(Movie.class, id);
        if (MovieToUpdate == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        Movie movie = modelMapper.map(movieUpdateRequestDTO, Movie.class);
        movie.setId(id);
        entityManager.merge(movie);

        return movie;
    }

    public void delete(Long id) {
        Movie MovieToDelete = entityManager.find(Movie.class, id);
        if (MovieToDelete != null) {
            entityManager.remove(MovieToDelete);
        }
    }

    public Movie rating(RatingMovieRequestDTO ratingMovieRequestDTO, Long id) {
        Movie movie = entityManager.find(Movie.class, id);
        Rating rating = new Rating();
        rating.setMovie(movie);
        rating.setVote(ratingMovieRequestDTO.getVote());
        entityManager.persist(rating);

        return movie;
    }

    private void updateOrCreateGenre(Movie movie) throws Exception {
        try {
            Set<Genre> genreHashSet = new HashSet<Genre>();
            movie.getGenres().forEach(item -> {
                if (item.getId() != null) {
                    Genre genre = genreService.getById(item.getId());
                    genre.setName(item.getName());
                    genreHashSet.add(genre);
                } else {
                    genreHashSet.add(item);
                }
            });
            movie.setGenres(genreHashSet);
        } catch (Exception e) {
            throw new Exception("Genre not found");
        }
    }

    private void setVotes(List<Movie> movies) {
        String sql = "SELECT new ais.movies.dto.VoteResponseDTO(AVG(r.vote), COUNT(r.id)) FROM Movie m LEFT JOIN Rating r ON r.movie = :movie";
        movies.forEach(movie -> {
            VoteResponseDTO voteResponseDTO = entityManager.createQuery(sql, VoteResponseDTO.class)
                    .setParameter("movie", movie)
                    .getSingleResult();
            if (voteResponseDTO != null) {
                movie.setVoteAverage(voteResponseDTO.getVoteAverage());
                movie.setVoteCount(voteResponseDTO.getVoteCount());
            }
        });
    }
}
