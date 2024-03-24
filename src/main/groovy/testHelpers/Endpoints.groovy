package testHelpers


import io.restassured.response.Response
import static io.restassured.RestAssured.given



/**
 * Endpoints being tested
 */
class Endpoints {

    static def getEndpoint() {
        Response response =
                given().
                        header("Content-Type", "application/json").
                        when().
                        get("/us/80021").
                        then().
                        extract().response()

        return response
    }

}

