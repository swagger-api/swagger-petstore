package io.swagger.petstore.resource;

import io.swagger.petstore.data.PetData;
import io.swagger.v3.oas.annotations.Hidden;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/components/schemas/petdetails")
@Produces({"application/json"})
public class PetDetailsResource {
    static PetData petData = new PetData();

    @GET
    @Hidden
    public Response petDetails() {
        String json = "{\n" +
                "  \"$id\": \"/api/v31/components/schemas/petdetails\",\n" +
                "  \"$schema\": \"https://json-schema.org/draft/2020-12/schema\",\n" +
                "  \"$vocabulary\": \"https://spec.openapis.org/oas/3.1/schema-base\",\n" +
                "  \"properties\": {\n" +
                "    \"id\": {\n" +
                "      \"type\": \"integer\",\n" +
                "      \"format\": \"int64\",\n" +
                "      \"$anchor\": \"pet_details_id\",\n" +
                "      \"examples\": [10]\n" +
                "    },\n" +
                "    \"category\": {\n" +
                "      \"$ref\": \"/api/v31/components/schemas/category\",\n" +
                "      \"description\": \"PetDetails Category\"\n" +
                "    },\n" +
                "    \"tag\": {\n" +
                "      \"$ref\": \"/api/v31/components/schemas/tag\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"xml\": {\n" +
                "    \"name\": \"PetDetails\"\n" +
                "  }\n" +
                "}";

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}
