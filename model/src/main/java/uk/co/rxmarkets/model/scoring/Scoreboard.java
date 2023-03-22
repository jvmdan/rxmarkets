package uk.co.rxmarkets.model.scoring;

import lombok.Getter;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Scoreboard {

    private final Date date;
    private final Map<Category, Indicator> scores;

    private Scoreboard(Date date, Map<Category, Indicator> scores) {
        this.date = date;
        this.scores = scores;
    }

    public Indicator getScore(Category forCategory) {
        return scores.get(forCategory);
    }

    // TODO | Remove this when it is no longer required.
    public static Scoreboard random() {
        final Builder builder = new Builder();
        Arrays.stream(Category.values()).forEachOrdered(c -> {
            builder.addScore(c, Indicator.random());
        });
        return builder.build();
    }

    public static class Builder {

        private final Date date;
        private final HashMap<Category, Indicator> scores = new HashMap<>(Category.values().length);

        public Builder() {
            this.date = Date.from(Instant.now());
        }

        public Builder addScore(Category c, Indicator i) {
            this.scores.put(c, i);
            return this; // Return the builder to allow for chained calls.
        }

        public Scoreboard build() {
            return new Scoreboard(date, scores);
        }

    }

}
