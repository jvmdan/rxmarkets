package uk.co.rxmarkets.view;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

/**
 * An injectable, application-scoped class which contains environment configuration details
 * (such as the base URL of the underlying API service).
 *
 * @author Daniel Scarfe
 */
@ApplicationScoped
@Getter
@Slf4j
public class EnvironmentConfiguration {

    private final String baseUrl;

    public EnvironmentConfiguration(@ConfigProperty(name = "rxmarkets.api.url") String baseUrl) {
        this.baseUrl = baseUrl;
        log.info("View layer configured to use API @ {}", baseUrl);
    }

}
