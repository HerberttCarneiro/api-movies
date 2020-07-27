package ais.movies.resource;

import ais.movies.model.Genre;
import ais.movies.service.GenreService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/genres")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GenreResource {

    @Inject
    GenreService genreService;

    @GET
    public Response getAllGenres() {
        List<Genre> genres = genreService.getAll();

        return Response.ok(genres).build();
    }

    @GET
    @Path("/{id}")
    public Response getGenreById(@PathParam("id") Long id) {
        Genre genre = genreService.getById(id);
        if (genre == null) {
            Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(genre).build();
    }

    @POST
    public Response createNewGenre(@Context UriInfo uriInfo, @RequestBody Genre genre) {
        Genre response = genreService.create(genre);
        URI uri = uriInfo.getAbsolutePathBuilder().path(response.getId().toString()).build();

        return Response.created(uri).build();
    }
}
