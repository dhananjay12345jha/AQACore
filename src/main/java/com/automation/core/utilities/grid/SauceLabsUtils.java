package com.automation.core.utilities.grid;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;


public class SauceLabsUtils {

    public static void testPasses(final String sessionId, final boolean passed, final String username, final String accessKey) {
        RestAssured
                .given()
                .spec(RestUtils.defaultRequestSpec())
                .auth()
                .preemptive()
                .basic(username, accessKey)
                .body("{\"passed\": " + passed + "}")
                .when()
                .put(String.format("https://app.testobject.com/api/rest/v2/appium/session/%s/test", sessionId))
                .then()
                .spec(RestUtils.defaultResponseSpec())
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    public static void testPassesdesktop(final String sessionId, final boolean passed, final String username, final String accessKey, final String scenarioName) {
        final JSONObject requestParams = new JSONObject();
        requestParams.put("passed",passed);
        requestParams.put("name",scenarioName);

        RestAssured
                .given()
                .spec(RestUtils.defaultRequestSpec())
                .auth()
                .preemptive()
                .basic(username, accessKey)
                .body(requestParams.toJSONString())
                .when()
                .put(String.format("https://app.eu-central-1.saucelabs.com/rest/v1/%s/jobs/%s", username, sessionId))
                .then()
                .spec(RestUtils.defaultResponseSpec())
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    public static void testPasses(final String sessionId, final boolean passed) {
        testPasses(sessionId, passed, PropUtils.get("SAUCE_USERNAME").orElseThrow(IllegalStateException::new), PropUtils.get("RDC_SAUCE_CONNECT_API_KEY").orElseThrow(IllegalStateException::new));
    }
}
