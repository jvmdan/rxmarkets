package uk.co.rxmarkets.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.net.URI;

@RequiredArgsConstructor
@Getter
public class Source {

    private final URI uri;

}
