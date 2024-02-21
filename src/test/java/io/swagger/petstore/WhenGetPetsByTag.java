package io.swagger.petstore;

import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static net.serenitybdd.rest.SerenityRest.*;

@ExtendWith(SerenityJUnit5Extension.class)
public class WhenGetPetsByTag {

    @Test
    public void fetchPetsByTag() {
        given()
                .baseUri("http://localhost:8080")
                .basePath("/api/v3/pet/findByTags")
                .queryParam("tags", "tag2")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get()
                .then()
                .body("tags.name", )
    }
}
