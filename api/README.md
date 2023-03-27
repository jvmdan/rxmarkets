# RxMarkets ~ RESTful API

The 'api' module contains a microservice that provides a RESTful API for accessing our sentiment data. This module is built using Quarkus and the Vert.x reactive web server.

The 'api' interacts with the underlying data store (PostgreSQL) to store & retrieve our sentiment information in a non-blocking manner. By design, it is an incredibly efficient way of retrieving large amounts of information in JSON form.

<strong>Note:</strong> The API also provides a mechanism for triggering the analysis of a given dataset via the 'engine' service. This is provided only for development reasons and will be deprecated at a later date. 

## Prerequisites 

This module expects an underlying Postgres instance to be running. If one is not, Quarkus will start a container for you during 'development' mode. The API uses this database to serve the sentiment data via the RESTful interface.

The aforementioned triggering of new sentiment data requires an 'engine' service to be available. Executing the trigger will result in the API module sending an `EngineRequest` to the message broker, which returns a `Scoreboard` if the service is available. 

You can run an API without an 'engine' if you wish, but the triggering mechanism will be unsupported. 

## Getting Started

Run the application like so:

```
./mvnw quarkus:dev
```

Then you can query the API in your browser @ http://localhost:8080/api/. 

Triggering of the 'engine' can be performed via http://localhost:8080/trigger.html.