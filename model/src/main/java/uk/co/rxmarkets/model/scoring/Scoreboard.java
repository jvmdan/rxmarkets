package uk.co.rxmarkets.model.scoring;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Scoreboard {

    private final Date date;
    private final Map<Category, Double> scoreboard = new HashMap<>();

    public Scoreboard(Date date) {
        this.date = date;
        Arrays.stream(Category.values()).forEach(c -> this.scoreboard.put(c, 0.0));
    }

    public Double getScore(Category forCategory) {
        return scoreboard.get(forCategory);
    }

    public Date getDate() {
        return date;
    }

}
