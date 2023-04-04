package uk.co.rxmarkets.gatherer;

public interface Connector<T> {

    /**
     * This method shall return the client used to scrape the API.
     *
     * @return the client instance
     */
    T getClient();

}
