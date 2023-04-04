package uk.co.rxmarkets.api.model.assets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import uk.co.rxmarkets.api.model.scoring.Scoreboard;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "equities")
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@NamedQuery(name = "Equity.findAll", query = "SELECT e FROM Equity e ORDER BY e.ticker")
@NamedQuery(name = "Equity.findMarket", query = "SELECT e FROM Equity e WHERE e.market = :market ORDER BY e.ticker")
@NamedQuery(name = "Equity.findSingle", query = "SELECT e FROM Equity e WHERE e.market = :market AND e.ticker = :ticker ORDER BY e.ticker")
public class Equity implements Asset {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

//    @ManyToOne
//    @JoinColumn(name = "market_id", insertable = false, updatable = false)
//    private EquityMarket market;r

    private String market;

    private String ticker;

    @OneToMany(mappedBy = "equity", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Scoreboard> scores;

    public Scoreboard getLatest() {
        return null; // TODO | Grab the latest scoreboard instance?
    }

}
