package petstore.userStories.actions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.swagger.petstore.model.Category;
import io.swagger.petstore.model.Pet;
import net.serenitybdd.core.steps.UIInteractions;
import petstore.Paths;
import static org.hamcrest.Matchers.*;

import static net.serenitybdd.rest.SerenityRest.*;

import java.util.Collections;
import java.util.Random;

import static io.swagger.petstore.data.PetData.createPet;

public class PetApiActions extends UIInteractions {

  @Given("I am authenticated")
  public Long givenIamAuthenticated(String userName, String password) {
    String response = given()
      .basePath(Paths.Login)
      .queryParam("username", userName)
      .queryParam("password", password)
      .accept(ContentType.XML)
      .get()
      .getBody()
      .toString();

     String idStr = response.replaceAll("\\D", "");
     return Long.parseLong(idStr);
  }

  @Given("I add my new pet Sun")
  public Long givenIAddMyNewPetSun(Long sessionId) {
    Pet pet = createPet(
      new Random().nextLong(),
      new Category(),
      "Sun",
      Collections.emptyList(),
      Collections.emptyList(),
      "available"
    );
    return given()
      .basePath(Paths.Pet)
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON)
      .header("sessionId", sessionId)
      .body(pet, ObjectMapperType.GSON)
      .post()
      .getBody()
      .as(Pet.class, ObjectMapperType.GSON)
      .getId();
  }

  @When("I ask for a pet using Sun's ID: {0}")
  public void whenIAskForAPetWithId(Long id) {
    when()
      .get("/" + id);
  }

  @Then("I get Sun as result")
  public void thenIGetSunAsResult() {
    then()
      .body("name", equalTo("Sun"));
  }

}
