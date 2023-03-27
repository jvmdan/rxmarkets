# RxMarkets ~ Viewing Platform

The 'view' module contains a microservice that can be used to visualise data retrieved from the API. Built using Quarkus and the Qute templating engine, this service renders JSON data into HTML for plotting.

## Prerequisites 

For the viewing platform to function properly, it requires a valid 'api' service to be ready & available. The developer will need to ensure that they have a development API (or production equivalent) running upon start-up.

## Getting Started

Run the application like so:

```
./mvnw quarkus:dev
```

Then visit http://localhost:8888/ in your browser.

Equity viewing is determined by your HTTP query. 

An example ($TSLA) is shown on the index page.