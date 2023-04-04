package uk.co.rxmarkets.api.entities.markets;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.api.entities.assets.Equity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "equity_markets")
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@NamedQuery(name = "EquityMarket.findAll", query = "SELECT m FROM EquityMarket m ORDER BY m.id")
public class EquityMarket implements Market<Equity> {

    @Id
    private String id;

    private String name;

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Equity> equities;

    public int getAssets() {
        // Returns the total number of assets in this market.
        return equities.size();
    }

}
