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

    private final Long id;
    private final String market;

    private String ticker = "default";
    private boolean active = true;

    public static Uni<List<Equity>> findAll(PgPool client) {
        return client.query("SELECT id, market FROM equities ORDER BY market ASC").execute()
                .onItem().transform(pgRowSet -> {
                    List<Equity> list = new ArrayList<>(pgRowSet.size());
                    for (Row row : pgRowSet) {
                        list.add(from(row));
                    }
                    return list;
                });
    }

    public static Uni<List<Equity>> findByMarket(PgPool client, String market) {
        return client.preparedQuery("SELECT id, market FROM equities WHERE market = $1").execute(Tuple.of(market))
                .onItem().transform(pgRowSet -> {
                    List<Equity> list = new ArrayList<>(pgRowSet.size());
                    pgRowSet.forEach(row -> list.add(from(row)));
                    return list;
                });
    }

    public static Uni<Equity> findById(PgPool client, Long id) {
        return client.preparedQuery("SELECT id, market FROM equities WHERE id = $1").execute(Tuple.of(id))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
    }

    public Uni<Long> save(PgPool client) {
        return client.preparedQuery("INSERT INTO equities (market) VALUES ($1) RETURNING (id)").execute(Tuple.of(market))
                .onItem().transform(pgRowSet -> pgRowSet.iterator().next().getLong("id"));
    }

    public Uni<Boolean> update(PgPool client) {
        return client.preparedQuery("UPDATE equities SET market = $1 WHERE id = $2").execute(Tuple.of(market, id))
                .onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
    }

    public static Uni<Boolean> delete(PgPool client, Long id) {
        return client.preparedQuery("DELETE FROM equities WHERE id = $1").execute(Tuple.of(id))
                .onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
    }

    private static Equity from(Row row) {
        return new Equity(row.getLong("id"), row.getString("market"));
    }

}
