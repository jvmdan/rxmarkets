package uk.co.rxmarkets.api.data;

public interface Scraper<T> {

    /**
     * This method shall return the client used to scrape the API.
     *
     * @return the client instance
     */
    T getClient();

}
