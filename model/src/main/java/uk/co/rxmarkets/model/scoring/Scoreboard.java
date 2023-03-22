package uk.co.rxmarkets.model.scoring;

import lombok.Getter;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

@Getter
public class Scoreboard {

    private final Date date;
    private final HashMap<Category, Indicator> scoreboard = new HashMap<>(Category.values().length);

    public Scoreboard() {
        this.date = Date.from(Instant.now());
        Arrays.stream(Category.values()).forEachOrdered(c -> scoreboard.put(c, Indicator.random()));
    }

    public Indicator getScore(Category forCategory) {
        return scoreboard.get(forCategory);
    }

    public void setScore(Category c, Indicator i) {
        this.scoreboard.put(c, i);
    }

}
