# Vala Demo Vertx
This application demonstrates how to create a simple RESTfull web service using Vert.x.
The following endpoints are exposed:

1. POST: /api/click/new
2. GET:  /api/clicks/get-count

## Development

You can build an executable JAR with all dependencies, from command line:

    ./gradlew build

You can also run the application from command line, without packaging a JAR at all:

    ./gradlew vertxRun

Once the application is started, the server is mapped to localhost port :8080

## Deployment

In the project root, you will find the following shell script:
    
    ./deploy.sh

When executed, it builds and then deploys the server to AWS. Private key is required in order to SSH to the AWS account.
Please consult the project administrator on how to obtain the key.
