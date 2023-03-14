# rxmarkets

## Introduction 

A reactive application for real-time assessment of market sentiment vs. risk in equity trading.

Highly concurrent data-driven processing built upon a Quarkus & Vert.x stack using JDK 17. 

## Scope

This is the **public** repository which exposes the RESTful API & underlying Java model. This is made available so that customers can integration the "rxmarkets" stack into their own software products.

A **private** repository also exists, which contains the proprietry analysis engine. This is a server-side environment not available for public consumption.

In due course, a valid API token will be required to access the product. A user or organisation need to buy a licence. During the product beta, a free trial option shall be made available. 

## Prerequisites

The application expects you to have a running postgreSQL instance for the purpose of testing the reactive database layer. Fortunately, if you don't have postgres installed, Quarkus will pull a container & create the necessary schemas for you automatically. For this to work you must have `Docker Engine` installed.

Developers are also advised to install the `Quarkus Run Configs` plugin for their IDE. This allows you to strap a debugger to the Quarkus application with ease. Otherwise, you must connect a remote debugger yourself, which can be cumbersome to do each time.

Another useful plugin is the `Database Navigator` which will allow you to connect to the running postgres instance and view its content during development.