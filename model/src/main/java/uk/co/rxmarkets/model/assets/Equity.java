package uk.co.rxmarkets.model.assets;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.model.scoring.Scoreboard;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class Equity implements Asset {

    private static final String FIND_MARKET_QUERY = "SELECT id, market, ticker FROM equities WHERE market = $1";
    private static final String FIND_EQUITY_QUERY = "SELECT id, market, ticker, active FROM equities WHERE market = $1 AND ticker = $2";
    private static final String SAVE_EQUITY_QUERY = "INSERT INTO equities (market, ticker, active) VALUES ($1, $2, $3) RETURNING (id)";
    private static final String UPDATE_EQUITY_QUERY = "UPDATE equities SET market = $1 WHERE id = $2";
    private static final String DELETE_EQUITY_QUERY = "DELETE FROM equities WHERE id = $1";

    private final Long id;
    private final String market;
    private final String ticker;
    private final boolean active;
    private final Scoreboard latest;

    public static Uni<List<Equity>> findByMarket(PgPool client, String market) {
        return client.preparedQuery(FIND_MARKET_QUERY).execute(Tuple.of(market))
                .onItem().transform(pgRowSet -> {
                    List<Equity> list = new ArrayList<>(pgRowSet.size());
                    pgRowSet.forEach(row -> list.add(from(row)));
                    return list;
                });
    }

    public static Uni<Equity> findByTicker(PgPool client, String market, String ticker) {
        return client.preparedQuery(FIND_EQUITY_QUERY).execute(Tuple.of(market, ticker))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
    }

    public Uni<Long> save(PgPool client) {
        return client.preparedQuery(SAVE_EQUITY_QUERY).execute(Tuple.of(market, ticker, active))
                .onItem().transform(pgRowSet -> pgRowSet.iterator().next().getLong("id"));
    }

    public Uni<Boolean> update(PgPool client) {
        return client.preparedQuery(UPDATE_EQUITY_QUERY).execute(Tuple.of(market, id))
                .onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
    }

    public static Uni<Boolean> delete(PgPool client, Long id) {
        return client.preparedQuery(DELETE_EQUITY_QUERY).execute(Tuple.of(id))
                .onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
    }

    private static Equity from(Row row) {
        final Long id = row.getLong("id");
        final String market = row.getString("market");
        final String ticker = row.getString("ticker");
        final Boolean active = row.getBoolean("active");
        final Scoreboard scoreboard = Scoreboard.random();
        final Equity equity = new Equity(id, market, ticker, active, scoreboard);
        log.info("Fetched {}", equity);
        return equity;
    }

}
