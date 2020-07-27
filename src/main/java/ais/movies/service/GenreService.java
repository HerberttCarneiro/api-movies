package ais.movies.service;

import ais.movies.model.Genre;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class GenreService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Genre> getAll() {
        return entityManager.createNamedQuery("Genre.findAll", Genre.class).getResultList();
    }

    public Genre getById(Long id) {
        return entityManager.find(Genre.class, id);
    }

    public Genre create(Genre genre) {
        entityManager.persist(genre);
        entityManager.flush();

        return genre;
    }
}
