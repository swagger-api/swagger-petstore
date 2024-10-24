package io.swagger.petstore.resource;

import io.swagger.petstore.data.PetData;
import io.swagger.v3.oas.annotations.Hidden;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/components/schemas/tag")
@Produces({"application/json"})
public class TagResource {
    static PetData petData = new PetData();

    @GET
    @Hidden
    public Response tag() {
        String json = "{\n" +
                "  \"$id\": \"/api/v3/components/schemas/tag\",\n" +
                "  \"properties\": {\n" +
                "    \"id\": {\n" +
                "      \"type\": \"integer\",\n" +
                "      \"format\": \"int64\"\n" +
                "    },\n" +
                "    \"name\": {\n" +
                "      \"type\": \"string\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"xml\": {\n" +
                "    \"name\": \"Tag\"\n" +
                "  }\n" +
                "}";

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}
