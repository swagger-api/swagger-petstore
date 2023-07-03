# Swagger Petstore Sample

## Overview
This is the pet store sample hosted at https://petstore31.swagger.io. For other versions, check the branches.
We welcome suggestion both about the code and the API design.

This is a java project to build a stand-alone server which implements the OpenAPI 3.1 Spec.  You can find out
more about both the spec and the framework at http://swagger.io.

### To run (with Maven)
To run the server, run this task:

```
mvn package jetty:run
```

This will start Jetty embedded on port 8080.

### To run (via Docker)

Expose port 8080 from the image and access petstore via the exposed port. You can then add and delete pets as you see fit.


*Example*:

```
docker build -t swaggerapi/petstore31:unstable .
```

```
docker pull swaggerapi/petstore31:unstable
docker run  --name swaggerapi-petstore31 -d -p 8080:8080 swaggerapi/petstore31:unstable
```


### Testing the server
Once started, you can navigate to http://localhost:8080/api/v31/openapi.json to view the Swagger Resource Listing.
This tells you that the server is up and ready to demonstrate Swagger.

### Using the UI
There is an HTML5-based API tool bundled in this sample--you can view it it at [http://localhost:8080](http://localhost:8080). This lets you inspect the API using an interactive UI.  You can access the source of this code from [here](https://github.com/swagger-api/swagger-ui)
