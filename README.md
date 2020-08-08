# Swagger Petstore Sample

## Overview
This is the pet store sample hosted at https://petstore3.swagger.io. For other versions, check the branches.
We welcome suggestion both the code and the API design.
To make changes to the design itself, take a look at https://github.com/swagger-api/swagger-petstore/blob/master/src/main/resources/openapi.yaml.

This is a java project to build a stand-alone server which implements the OpenAPI 3 Spec.  You can find out
more about both the spec and the framework at http://swagger.io.

This sample is based on [swagger-inflector](https://github.com/swagger-api/swagger-inflector), and provides an example of swagger / OpenAPI 3 petstore.

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
docker build -t swaggerapi/petstore3:unstable .
```

```
docker pull swaggerapi/petstore3:unstable
docker run  --name swaggerapi-petstore3 -d -p 8080:8080 swaggerapi/petstore3:unstable
```


### Testing the server
Once started, you can navigate to http://{aws.loadbalancer}:8080/api/v3/openapi.json to view the Swagger Resource Listing.
This tells you that the server is up and ready to demonstrate Swagger.

### Using the UI
There is an HTML5-based API tool bundled in this sample--you can view it it at [http://{aws.loadbalancer}:8080]. This lets you inspect the API using an interactive UI.  You can access the source of this code from [here](https://github.com/swagger-api/swagger-ui)

### ECS 
* Your AWS account must be setup with an ECS cluster named `main`, a service with name `node-app` that uses task-definition named `node-app` (Terraform code here: https://github.com/praddevops/ecs-terraform)

### App code is located in src/. Any changes made will trigger the pipeline which builds new image and updates the ECS service with new image

## CICD pipeline:

Jenkins version: 2.234

Jenkins Plugin Requirements(in addition to default plugins): AnsiColor, Pipeline Utility Steps

### Following credentials should be present in Jenkins

* `aws_access_key_id` (Credential type: secret text): "AWS Access Key ID"
* `aws_access_key` (Credential type: secret text): "AWS Access Secret Key"
* `dockerhub_username` (Credential type: secret text): "Dockerhub username"
* `dockerhub_pw` (Credential type: secret text): "Dockerhub repo password"

### Jenkins Troubleshooting

* If pipeline fails due to `org.jenkinsci.plugins.scriptsecurity.sandbox.RejectedAccessException: Scripts not permitted to use....`, go to Manage Jenkins-->In process Script Approval to approve the scripts 
