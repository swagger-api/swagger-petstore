package io.swagger.petstore.resource;

import io.swagger.petstore.data.PetData;
import io.swagger.v3.oas.annotations.Hidden;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/components/schemas/category")
@Produces({"application/json"})
public class CategoryResource {
    static PetData petData = new PetData();

    @GET
    @Hidden
    public Response category() {
        String json = "{\n" +
                "  \"Category\" : {\n" +
                "    \"$id\" : \"/api/v31/components/schemas/category\",\n" +
                "    \"description\" : \"Category\",\n" +
                "    \"properties\" : {\n" +
                "      \"id\" : {\n" +
                "        \"type\" : \"integer\",\n" +
                "        \"format\" : \"int64\",\n" +
                "        \"example\" : 1\n" +
                "      },\n" +
                "      \"name\" : {\n" +
                "        \"type\" : \"string\",\n" +
                "        \"example\" : \"Dogs\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"xml\" : {\n" +
                "      \"name\" : \"Category\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}
