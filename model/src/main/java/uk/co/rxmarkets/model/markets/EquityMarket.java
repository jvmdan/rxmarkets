package uk.co.rxmarkets.model.markets;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.model.assets.Equity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
@Slf4j
public class EquityMarket implements Market<Equity> {

    private static final String FIND_ALL_QUERY = "SELECT id, mic, name, location FROM markets ORDER BY name ASC";
    private static final String FIND_SINGLE_QUERY = "SELECT id, mic, name, location FROM markets WHERE mic = $1";

    private final Long id;
    private final String mic;
    private final String name;
    private final Market.Location location;

    @Override
    public Class<Equity> getType() {
        return Equity.class;
    }

    public static Uni<List<EquityMarket>> findAll(PgPool client) {
        return client.query(FIND_ALL_QUERY).execute()
                .onItem().transform(pgRowSet -> {
                    final List<EquityMarket> list = new ArrayList<>(pgRowSet.size());
                    pgRowSet.forEach(row -> list.add(from(row)));
                    return list;
                });
    }

    public static Uni<List<EquityMarket>> findSingle(PgPool client, String mic) {
        return client.preparedQuery(FIND_SINGLE_QUERY).execute(Tuple.of(mic))
                .onItem().transform(pgRowSet -> {
                    final List<EquityMarket> list = new ArrayList<>(pgRowSet.size());
                    pgRowSet.forEach(row -> list.add(from(row)));
                    return list;
                });
    }

    private static EquityMarket from(Row row) {
        final String mic = row.getString("mic");
        final String name = row.getString("name");
        final Location l = Location.valueOf(row.getString("location").toUpperCase(Locale.ROOT));
        final EquityMarket market = new EquityMarket(row.getLong("id"), mic, name, l);
        log.info("Fetched Market: {}", market);
        return market;
    }

}
