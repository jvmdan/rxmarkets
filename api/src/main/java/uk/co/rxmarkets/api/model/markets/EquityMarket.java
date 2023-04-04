package uk.co.rxmarkets.api.model.markets;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import uk.co.rxmarkets.api.model.assets.Equity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "markets")
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@NamedQuery(name = "EquityMarket.findAll", query = "SELECT m FROM EquityMarket m ORDER BY m.id")
public class EquityMarket implements Market<Equity> {

    @Id
    private String id;

    private String name;

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Equity> equities;

    public int getAssets() {
        // Returns the total number of assets in this market.
        return equities.size();
    }

}
