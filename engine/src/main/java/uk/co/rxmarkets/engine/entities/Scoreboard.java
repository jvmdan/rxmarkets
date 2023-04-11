package uk.co.rxmarkets.engine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
@Table(name = "scoreboards")
@Data
@NoArgsConstructor
@Getter
@NamedQuery(name = "Scoreboard.findAll", query = "SELECT s FROM Scoreboard s ORDER BY s.date")
@NamedQuery(name = "Scoreboard.findForEquity", query = "SELECT s FROM Scoreboard s WHERE s.equity.ticker = :ticker ORDER BY s.date")
public class Scoreboard {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "equity_id", updatable = false)
    @JsonIgnore
    private Equity equity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public String getTicker() {
        return equity.getTicker();
    }

    @ElementCollection
    @CollectionTable(name = "equity_scores", joinColumns = {@JoinColumn(name = "scoreboard_id", referencedColumnName = "uuid")})
    @MapKeyColumn(name = "category")
    @Column(name = "score")
    @Fetch(FetchMode.JOIN)
    private Map<String, Double> scores;

}
