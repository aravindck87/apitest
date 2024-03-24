package apiService

import io.restassured.response.Response
import org.json.JSONObject
import testHelpers.SchemaTest
import static io.restassured.RestAssured.baseURI
import static org.hamcrest.Matchers.is
import static org.hamcrest.junit.MatcherAssert.assertThat
import static testHelpers.JsonHelpers.jsonObject
import static testHelpers.TestProperties.*
import static testHelpers.Endpoints.*


class apiEndpointTests extends SchemaTest {

    def setupSpec() {
        baseURI = getBaseURI()
    }

    def cleanupSpec() {

    }



    def "Test Zipcode endpoint"() {
        given: "I have the valid endpoint"

        when: "I send a get request to check the end point"
        Response response = getEndpoint()

        then: "I receive a correct response"

        def status = response.getStatusCode()
        print(response.prettyPrint())
        assertThat(status, is(200))

        JSONObject responseJson = jsonObject(response.body().asString())

        def schema = getSchema('output.json')
        validateSchema(responseJson, schema)

    }



}
