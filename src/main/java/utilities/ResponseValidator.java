package utilities;

import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.Map;

public class ResponseValidator {

    /* =====================================================
       BASIC VALIDATIONS
       ===================================================== */

    // Validate HTTP status code
    public static void validateStatusCode(Response response, int expectedStatusCode) {
        Assert.assertNotNull(response, "Response is null");
        Assert.assertEquals(
                response.statusCode(),
                expectedStatusCode,
                "Status code mismatch"
        );
    }

    // Validate response header
    public static void validateHeader(Response response, String headerName, String expectedValue) {
        Assert.assertEquals(
                response.getHeader(headerName),
                expectedValue,
                "Header mismatch for: " + headerName
        );
    }

    // Validate cookie value
    public static void validateCookie(Response response, String cookieName) {
        Assert.assertTrue(
                response.getCookies().containsKey(cookieName),
                "Cookie not found: " + cookieName
        );
    }

    /* =====================================================
       JSON KEY-VALUE VALIDATIONS
       ===================================================== */

    // Validate simple JSON key
    public static void validateJsonKey(Response response, String jsonPath, Object expectedValue) {
        JsonPath responseJson = response.jsonPath();
        Object actualValue = responseJson.get(jsonPath);

        Assert.assertEquals(
                actualValue,
                expectedValue,
                "Mismatch at JSON path: " + jsonPath
        );
    }

    // Validate key exists
    public static void validateKeyExists(Response response, String jsonPath) {
        JsonPath responseJson = response.jsonPath();
        Assert.assertNotNull(
                responseJson.get(jsonPath),
                "Key not found: " + jsonPath
        );
    }

    /* =====================================================
       REQUEST vs RESPONSE VALIDATION (MAP BASED)
       ===================================================== */

    public static void validateRequestVsResponse(
            String requestPayload,
            Response response,
            String responseRootNode
    ) {

        JsonPath requestJson = new JsonPath(requestPayload);
        JsonPath responseJson = response.jsonPath();

        Map<String, Object> requestMap = requestJson.getMap("$");
        Map<String, Object> responseMap = responseJson.getMap(responseRootNode);

        for (String key : requestMap.keySet()) {

            Object requestValue = requestMap.get(key);
            Object responseValue = responseMap.get(key);

            // Nested JSON handling
            if (requestValue instanceof Map) {

                Map<String, Object> requestNested = (Map<String, Object>) requestValue;
                Map<String, Object> responseNested = (Map<String, Object>) responseValue;

                for (String nestedKey : requestNested.keySet()) {
                    Assert.assertEquals(
                            responseNested.get(nestedKey),
                            requestNested.get(nestedKey),
                            "Mismatch at " + responseRootNode + "." + key + "." + nestedKey
                    );
                }

            } else {
                Assert.assertEquals(
                        responseValue,
                        requestValue,
                        "Mismatch at " + responseRootNode + "." + key
                );
            }
        }
    }

    /* =====================================================
       FULL RESPONSE LOGGING (DEBUGGING)
       ===================================================== */

    public static void logResponse(Response response) {
        Assert.assertNotNull(response, "Response is null");
        System.out.println("===== RESPONSE BODY =====");
        System.out.println(response.asPrettyString());
    }
}

