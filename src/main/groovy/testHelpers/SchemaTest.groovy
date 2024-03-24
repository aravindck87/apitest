package testHelpers

import org.everit.json.schema.Schema
import org.everit.json.schema.ValidationException
import org.everit.json.schema.loader.SchemaClient
import org.everit.json.schema.loader.SchemaLoader
import org.json.JSONObject
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

abstract class SchemaTest extends Specification {
    Schema getSchema(String schemaFileName) {
        def path = Paths.get(getClass().getResource("/schemas/$schemaFileName").getPath())
        String content = new String(Files.readAllBytes(path))
        def json = new JSONObject(content)

        SchemaLoader schemaLoader = SchemaLoader.builder()
                .schemaClient(SchemaClient.classPathAwareClient())
                .schemaJson(json)
                .resolutionScope(getClass().getResource('/schemas/').toURI())
                .build()

        return schemaLoader.load().build() as Schema
    }

    void validateSchema(Object thingToValidate, Schema schema) {
        try {
            schema.validate(thingToValidate)
        } catch (ValidationException e) {
            def childErrors = e.getCausingExceptions()
                    .stream()
                    .map {
                        err -> err.getAllMessages()
                    }
                    .collect(Collectors.toList())

            def parentError = e.errorMessage

            def allErrors = new LinkedList<String>()
            allErrors.add("\nThe following issues were found with the JSON:\n")
            allErrors.add(parentError)
            allErrors.addAll(childErrors)

            throw new ValidationException(allErrors.join('\n'))
        }
    }

}
