package uk.co.rxmarkets.api.model.markets;

import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.api.model.assets.Equity;
import uk.co.rxmarkets.api.services.repo.MarketService;

import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "markets")
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@NamedQuery(name = "EquityMarket.findAll", query = "SELECT m FROM EquityMarket m ORDER BY m.id")
public class EquityMarket implements Market<Equity> {

    @Id
    private String id;

    private String name;
//
//    @OneToMany(mappedBy = "market_id", cascade = CascadeType.ALL)
//    @Fetch(FetchMode.JOIN)
//    private Set<Equity> equities;

    public int getAssets() {
        return 1; // TODO | Fetch the number of equities from the database for the given market.
    }

}
