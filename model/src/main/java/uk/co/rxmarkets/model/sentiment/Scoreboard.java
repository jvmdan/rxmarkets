package uk.co.rxmarkets.model.sentiment;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Scoreboard {

    private final Date date;
    private final Map<Category, Indicator> scoreboard = new HashMap<>(Category.values().length);

    public Scoreboard(Date date) {
        this.date = date;
        Arrays.stream(Category.values()).forEach(c -> this.scoreboard.put(c, Indicator.reset(c)));
    }

    public Indicator getScore(Category forCategory) {
        return scoreboard.get(forCategory);
    }

    public Date getDate() {
        return date;
    }

}
