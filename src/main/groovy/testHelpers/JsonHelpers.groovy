package testHelpers

import org.json.JSONException
import org.json.JSONObject
import org.slf4j.LoggerFactory

class JsonHelpers {

    private static def logger = LoggerFactory.getLogger(JsonHelpers)

    static JSONObject jsonObject(Object jsonObjectString) {

        try {
            new JSONObject(jsonObjectString)
        } catch (JSONException jsonException) {
            throwRuntimeExceptionWithMessage(
                    jsonException,
                    "Failed to create JSON object for: $jsonObjectString"
            )
        }
    }

    private static void throwRuntimeExceptionWithMessage(JSONException jsonException, String message) {
        logger.error(message)
        throw new RuntimeException(message, jsonException)
    }

}
