package uk.co.rxmarkets.api.services.repo;

import io.smallrye.mutiny.Uni;
import uk.co.rxmarkets.api.model.assets.Equity;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.*;

public interface Repository<ID, T> {

    Uni<List<T>> findAll();


    Uni<T> findById(ID id);

    Uni<Response> create(T entity);

    // TODO | We may later need to add an 'update' method to this interface.
    // Uni<Response> update(ID id, T entity);

    Uni<Response> delete(ID id);

}
