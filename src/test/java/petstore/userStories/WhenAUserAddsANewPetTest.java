package petstore.userStories;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import petstore.Base;
import petstore.userStories.actions.PetApiActions;

@ExtendWith(SerenityJUnit5Extension.class)
public class WhenAUserAddsANewPetTest extends Base {

  @BeforeEach
  public void setup() {
    super.setup();
  }

  Long newPetId = null;
  Long sessionId = null;
  PetApiActions petApi;

  @Test
  public void createsANewPetSuccessfully() {
    sessionId = petApi.givenIamAuthenticated("theUser", "XXXXXXXXXXX");
    newPetId = petApi.givenIAddMyNewPetSun(sessionId);
    petApi.whenIAskForAPetWithId(newPetId);
    petApi.thenIGetSunAsResult();
  }
}
