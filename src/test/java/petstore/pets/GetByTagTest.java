package petstore.pets;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Description;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import petstore.Base;
import petstore.Paths;

@ExtendWith(SerenityJUnit5Extension.class)
public class GetByTagTest extends Base {

  @BeforeEach
  public void setup() {
    super.setup();
  }

  @Test
  @Description("Fetch pets by tag and validate tag presence")
  public void eachPetHasSameTagAsQueryTag() {
    System.out.println(getDefaultBasePath());
    given()
      .basePath(Paths.FindByTags)
      .queryParam("tags", "tag2")
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON)
      .get()
      .then()
      .body("tags.name", everyItem(hasItem("tag2")));
  }

  @Test
  public void emptyCollectionForUnknownTag() {
    given()
      .basePath(Paths.FindByTags)
      .queryParam("tags", "unknowntag")
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON)
      .get()
      .then()
      .body(equalTo("[]"));
  }

  @Test
  public void errorForEmptyTag() {
    given()
      .basePath(Paths.FindByTags)
      .queryParam("tags", "")
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON)
      .get()
      .then()
      .body("message", equalTo("No tags provided. Try again?"));
  }

  @Test
  public void validateJsonSchema() {
    given()
      .basePath(Paths.FindByTags)
      .queryParam("tags", "tag1")
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON)
      .get()
      .then()
      .assertThat()
      .body(matchesJsonSchemaInClasspath("schemas/petsByTag.json"));
  }
}
