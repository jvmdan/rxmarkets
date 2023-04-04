package uk.co.rxmarkets.api.model.scoring;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import uk.co.rxmarkets.api.model.assets.Equity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "equity_scores")
@Data
@NoArgsConstructor
@Getter
@NamedQuery(name = "Scoreboard.findAll", query = "SELECT s FROM Scoreboard s")
@NamedQuery(name = "Scoreboard.findForEquity", query = "SELECT s FROM Scoreboard s WHERE s.equity.ticker = :ticker")
public class Scoreboard {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "equity_id", insertable = false, updatable = false)
    @JsonIgnore
    private Equity equity;

    private Date date;

    public String getTicker() {
        return equity.getTicker();
    }

//    private Map<Category, Indicator> scores;
//
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
