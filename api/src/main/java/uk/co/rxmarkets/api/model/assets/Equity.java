package uk.co.rxmarkets.api.model.assets;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
@NamedQuery(name = "Equity.findMarket", query = "SELECT e FROM Equity e WHERE e.market = :market")
@NamedQuery(name = "Equity.findSingle", query = "SELECT e FROM Equity e WHERE e.market = :market AND e.ticker = :ticker")
public class Equity implements Asset {

    @Id
    @GeneratedValue
    private Long id;

    private String market;
    private String ticker;

    @OneToMany(mappedBy = "equity", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Scoreboard> scores;

}
