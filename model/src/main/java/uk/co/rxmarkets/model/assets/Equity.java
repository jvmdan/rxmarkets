package uk.co.rxmarkets.model.assets;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Equity implements Asset {

    public final Long id;
    public final String ticker;
    private boolean active;

    public static Uni<List<Equity>> findAll(PgPool client) {
        return client.query("SELECT id, ticker FROM equities ORDER BY ticker ASC").execute()
                .onItem().transform(pgRowSet -> {
                    List<Equity> list = new ArrayList<>(pgRowSet.size());
                    for (Row row : pgRowSet) {
                        list.add(from(row));
                    }
                    return list;
                });
    }

    public static Uni<Equity> findById(PgPool client, Long id) {
        return client.preparedQuery("SELECT id, ticker FROM equities WHERE id = $1").execute(Tuple.of(id))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
    }

    public Uni<Long> save(PgPool client) {
        return client.preparedQuery("INSERT INTO equities (ticker) VALUES ($1) RETURNING (id)").execute(Tuple.of(ticker))
                .onItem().transform(pgRowSet -> pgRowSet.iterator().next().getLong("id"));
    }

    public Uni<Boolean> update(PgPool client) {
        return client.preparedQuery("UPDATE equities SET ticker = $1 WHERE id = $2").execute(Tuple.of(ticker, id))
                .onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
    }

    public static Uni<Boolean> delete(PgPool client, Long id) {
        return client.preparedQuery("DELETE FROM equities WHERE id = $1").execute(Tuple.of(id))
                .onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
    }

    private static Equity from(Row row) {
        return new Equity(row.getLong("id"), row.getString("ticker"));
    }

    @Override
    public String id() {
        return this.ticker;
    }

    @Override
    public boolean isActivelyTraded() {
        return this.active;
    }

}
