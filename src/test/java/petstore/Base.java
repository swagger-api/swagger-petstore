package petstore;

import io.restassured.RestAssured;
import net.thucydides.model.util.EnvironmentVariables;

public class Base {

  EnvironmentVariables env;

  public void setup() {
    RestAssured.baseURI = env.getProperty("restapi.baseurl");
  }
}
