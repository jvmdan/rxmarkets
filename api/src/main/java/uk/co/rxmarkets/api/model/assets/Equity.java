package uk.co.rxmarkets.api.model.assets;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import uk.co.rxmarkets.api.model.markets.EquityMarket;
import uk.co.rxmarkets.api.model.scoring.Scoreboard;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "equities")
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@NamedQuery(name = "Equity.findMarket", query = "SELECT e FROM Equity e WHERE e.market.id = :marketId ORDER BY e.id")
@NamedQuery(name = "Equity.findSingle", query = "SELECT e FROM Equity e WHERE e.id = :id")
public class Equity implements Asset {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "market_id", insertable = false, updatable = false)
    @JsonManagedReference
    private EquityMarket market;

    public String getMarket() {
        return market.getId();
    }

    public Scoreboard getLatest() {
        return null; // TODO | Grab the latest scoreboard instance?
    }

}
