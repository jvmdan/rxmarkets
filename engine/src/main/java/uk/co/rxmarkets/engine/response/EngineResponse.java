package uk.co.rxmarkets.engine.response;

import lombok.Data;
import lombok.Getter;
import uk.co.rxmarkets.engine.requests.EngineRequest;

import java.time.Instant;
import java.util.*;

@Data
@Getter
public class EngineResponse {

    private final String requestId;
    private final Date date;
    private final Map<String, Double> scores;

    // Private constructor to prevent instantiation outside of builder class.
    private EngineResponse(String requestId, Date date, Map<String, Double> scores) {
        this.requestId = requestId;
        this.date = date;
        this.scores = scores;
    }

    public static class Builder {

        private final UUID requestId;
        private final Date date;
        private final HashMap<String, Double> scores = new HashMap<>();

        public Builder(EngineRequest requestId) {
            this(requestId, Date.from(Instant.now()));
        }

        public Builder(EngineRequest request, Date date) {
            this.requestId = request.getId();
            this.date = date;
        }

        public Builder addScore(String category, Double score) {
            this.scores.put(category, score);
            return this; // Return the builder to allow for chained calls.
        }

        public EngineResponse build() {
            return new EngineResponse(requestId.toString(), date, scores);
        }

    }

}
