package ais.movies.resource;

import ais.movies.dto.MovieCreationRequestDTO;
import ais.movies.dto.MovieUpdateRequestDTO;
import ais.movies.dto.RatingMovieRequestDTO;
import ais.movies.model.Movie;
import ais.movies.service.MovieService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/movies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class MovieResource {

    @Inject
    MovieService movieService;

    @GET
    public Response getAllMovies() {
        List<Movie> movies = movieService.getAll();

        return Response.ok(movies).build();
    }

    @GET
    @Path("/{id}")
    public Response getMovieById(@PathParam("id") Long id) {
        Movie movie = movieService.getById(id);
        if (movie == null) {
            Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(movie).build();
    }

    @POST
    public Response createNewMovie(@Context UriInfo uriInfo, @RequestBody @Valid MovieCreationRequestDTO movieCreationRequestDTO) {
        try {
            Movie movie = movieService.create(movieCreationRequestDTO);
            URI uri = uriInfo.getAbsolutePathBuilder().path(movie.getId().toString()).build();

            return Response.created(uri).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Genre not found").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateMovie(@PathParam("id") Long id, @RequestBody MovieUpdateRequestDTO movieUpdateRequestDTO) {
        Movie movie = movieService.update(movieUpdateRequestDTO, id);
        if (movie == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMovie(@PathParam("id") Long id) {
        movieService.delete(id);
        return Response.ok().build();
    }

    @POST()
    @Path("/{id}/rating")
    public Response createNewMovie(@Context UriInfo uriInfo, @PathParam("id") Long id, @RequestBody @Valid RatingMovieRequestDTO ratingMovieRequestDTO) {
        Movie movie = movieService.rating(ratingMovieRequestDTO, id);
        URI uri = uriInfo.getAbsolutePathBuilder().path(movie.getId().toString()).build();

        return Response.created(uri).build();
    }
}
