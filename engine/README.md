# RxMarkets ~ Analysis Engine

The 'engine' module contains a microservice that is used to analyse our sentiment data, generating scores across a given set of categories for our provided dataset. This module is built using Quarkus and communication with the service is facilitated by an AMQP broker (in this case, RabbitMQ). 

The use of a messaging broker allows us to remain durable to downtime events. Therefore, if the engine is unavailable for any reason, the message will be delivered when the service is back up & running. This guarantees that no request goes missing, and all requests are anaylsed by the engine. 

## Prerequisites 

There are no prerequisites to running the 'engine' service. The service can exist silently on its own, awaiting requests via the message broker. 

## Getting Started

Run the application like so:

```
./mvnw quarkus:dev
```

This will start listening for incoming `EngineRequest` events on the "engine-requests" channel. Upon receipt of a request, the engine shall analyse the dataset and a resulting `Scoreboard` will be propagated downstream via the "scores" response channel. 

The aim is to decouple the analysis from the rest of the implementation, so it is ultimately up to the consumer what they choose to do with the `Scoreboard` result.