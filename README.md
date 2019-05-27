# Vertx - Server Demo

![Clients Snapshot](clients-snapshot.jpg?raw=true "Clients Snapshot")

This project demonstrates how to create a simple RESTful web-service and a reactive, WebSocket-based, service using [Vert.x](https://vertx.io/).
The following endpoints are exposed:

    1. POST: /api/clicks
    2. GET : /api/clicks
    3. WS  : /eventbus

## Development

To build an executable fat JAR, bundled with all the dependencies:

    ./gradlew build

To run the application from command line, without packaging first:

    ./gradlew vertxRun

Once the application is started, the server is mapped to http://localhost:8080

## Deployment

In the project root, you will find the following shell script:
    
    ./deploy.sh

When executed, it builds and deploys the server to an EC2 machine on AWS. Private key is required in order to SSH to the target machine.
Please consult the project administrator on how to obtain the key.
