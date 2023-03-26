package uk.co.rxmarkets.model.scoring;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.*;

@RequiredArgsConstructor
@Getter
public class Scoreboard {

    private final String id;
    private final Date date;
    private final Map<Category, Indicator> scores;

    public Indicator getScore(Category forCategory) {
        return scores.get(forCategory);
    }

    // TODO | Remove this when it is no longer required.
    public static Scoreboard random() {
        final Builder builder = new Builder(UUID.randomUUID());
        Arrays.stream(Category.values()).forEachOrdered(c -> {
            builder.addScore(c, Indicator.random());
        });
        return builder.build();
    }

    public static class Builder {

        private final UUID id;
        private final Date date;
        private final HashMap<Category, Indicator> scores = new HashMap<>(Category.values().length);

        public Builder(UUID id) {
            this.id = id;
            this.date = Date.from(Instant.now());
        }

        public Builder addScore(Category c, Indicator i) {
            this.scores.put(c, i);
            return this; // Return the builder to allow for chained calls.
        }

        public Scoreboard build() {
            return new Scoreboard(id.toString(), date, scores);
        }

    }

}
