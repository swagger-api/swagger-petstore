package io.swagger.petstore;

import io.swagger.petstore.utils.WebApplicationExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api/v31")
public class MyApplication extends ResourceConfig {

    public MyApplication() {
        super();
        /*
         * It seems that Jersey (at least 2.26) incorrectly registers also non root resources (available via getClasses())
         * even if stated otherwise in https://jersey.github.io/documentation/latest/deployment.html
         * 4.7.2.3.2. JAX-RS application with a custom Application subclass
         * this could result in non existing operations being added if served by subresource classes.
         *
         * To avoid this behaviour provide own packages/classes/resources/singleton here
         */
        super.packages("io.swagger.petstore.resource", "io.swagger.v3.jaxrs2.integration.resources");
        register(JacksonMapperProvider.class);
        register(WebApplicationExceptionMapper.class);
    }
}
