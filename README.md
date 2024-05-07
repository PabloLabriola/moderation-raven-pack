## Build

To build the entire project: ./gradlew build (compile the code, generate the documentation and run the test)


To start application execute: ./gradlew bootRun

## Tests

API documentation available on the next url: http://localhost:8080/swagger-ui/index.html

Endpoint for uploading csv file, it should be called by POST, and sent in the body of the request with the name "file-to-moderate": http://localhost:8080/api/v1/message-moderation
