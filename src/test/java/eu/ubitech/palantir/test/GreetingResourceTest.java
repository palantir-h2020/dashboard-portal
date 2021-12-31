package eu.ubitech.palantir.test;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@Disabled
public class GreetingResourceTest {

  @Disabled
  @Test
  public void testHelloEndpoint() {
    given().when().get("/api/v1/service/dummy").then().statusCode(200);
  }

}
