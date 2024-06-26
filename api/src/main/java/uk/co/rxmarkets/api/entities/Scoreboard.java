package uk.co.rxmarkets.api.entities;

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

//    public Indicator getScore(Category forCategory) {
//        return scores.get(forCategory);
//    }
//
//    // TODO | Remove this when it is no longer required.
//    public static Scoreboard random() {
//        final Builder builder = new Builder(UUID.randomUUID());
//        Arrays.stream(Category.values()).forEachOrdered(c -> {
//            builder.addScore(c, Indicator.random());
//        });
//        return builder.build();
//    }
//
//    // TODO | Remove this when it is no longer required.
//    public static Scoreboard random(Date date) {
//        final Builder builder = new Builder(UUID.randomUUID(), date);
//        Arrays.stream(Category.values()).forEachOrdered(c -> {
//            builder.addScore(c, Indicator.random());
//        });
//        return builder.build();
//    }
//
//    public static class Builder {
//
//        private final UUID id;
//        private final Date date;
//        private final HashMap<Category, Indicator> scores = new HashMap<>(Category.values().length);
//
//        public Builder(UUID id) {
//            this(id, Date.from(Instant.now()));
//        }
//
//        public Builder(UUID id, Date date) {
//            this.id = id;
//            this.date = date;
//        }
//
//        public Builder addScore(Category c, Indicator i) {
//            this.scores.put(c, i);
//            return this; // Return the builder to allow for chained calls.
//        }
//
//        public Scoreboard build() {
//            return new Scoreboard(id.toString(), date, scores);
//        }
//
//    }

}
