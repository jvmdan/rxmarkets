package uk.co.rxmarkets.model.source;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.net.URI;

public interface Source {

    URI getUri();

}
