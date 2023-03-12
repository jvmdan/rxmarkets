package uk.co.rxmarkets.model.scoring;

import java.util.Map;

public class Scoreboard {

    Map<Category, Double> scoreboard;

    public Double getScore(Category forCategory) {
        return scoreboard.get(forCategory);
    }

}
