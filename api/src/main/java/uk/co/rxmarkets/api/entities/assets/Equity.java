package uk.co.rxmarkets.api.entities.assets;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.api.entities.markets.EquityMarket;
import uk.co.rxmarkets.api.entities.scoring.Scoreboard;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "equities")
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@NamedQuery(name = "Equity.findAll", query = "SELECT e FROM Equity e")
@NamedQuery(name = "Equity.findMarket", query = "SELECT e FROM Equity e WHERE e.market.id = :market ORDER BY e.id")
@NamedQuery(name = "Equity.findSingle", query = "SELECT e FROM Equity e WHERE e.market.id = :market AND e.ticker = :ticker")
public class Equity implements Asset {

    @Id
    private String ticker;

    @ManyToOne
    @JoinColumn(name = "market_id", insertable = false, updatable = false)
    @JsonIgnore
    private EquityMarket market;

    private String name;

    @OneToMany(mappedBy = "equity", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Scoreboard> scores;

    public String getMarket() {
        // Override the default JSON behaviour and return only the market ID.
        return market.getId();
    }

}
