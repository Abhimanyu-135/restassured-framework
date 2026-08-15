package utilities;

import java.nio.file.Files;
import java.nio.file.Paths;

public class PayloadReader {

    public static String read(String fileName) {

        try {

            String payload = new String(
                    Files.readAllBytes(
                            Paths.get("src/main/resources/payloads/" + fileName)
                    )
            );

            // Replace only if the placeholder exists in the payload
            if (payload.contains("${BOOKER_USERNAME}")) {
                payload = payload.replace(
                        "${BOOKER_USERNAME}",
                        getRequiredEnvironmentVariable("BOOKER_USERNAME")
                );
            }

            if (payload.contains("${BOOKER_PASSWORD}")) {
                payload = payload.replace(
                        "${BOOKER_PASSWORD}",
                        getRequiredEnvironmentVariable("BOOKER_PASSWORD")
                );
            }

            return payload;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to read payload: " + fileName,
                    e
            );
        }
    }

    private static String getRequiredEnvironmentVariable(String variableName) {

        String value = System.getenv(variableName);

        if (value == null || value.isBlank()) {

            throw new IllegalStateException(
                    "Required environment variable is missing: "
                            + variableName
            );
        }

        return value;
    }
}
