package com.automation.core.utilities.grid;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.ConnectionConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static io.restassured.RestAssured.given;


public class RestUtils {
    private static ValidatableResponse response;

    public static RequestSpecification defaultRequestSpec() {
        return new RequestSpecBuilder()
                .setConfig(
                        RestAssuredConfig.config()
                                .sslConfig(new SSLConfig().relaxedHTTPSValidation().allowAllHostnames())
                        .connectionConfig(ConnectionConfig.connectionConfig().closeIdleConnectionsAfterEachResponseAfter(5, TimeUnit.SECONDS))
                ).log(LogDetail.ALL)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification defaultResponseSpec() {
        return new ResponseSpecBuilder().log(LogDetail.ALL).build();
    }

    private static ValidatableResponse baseRequest(final Object requestBody, final RequestSpecification requestSpec, final ResponseSpecification responseSpec, final Function<RequestSpecification, Response> call) {
        return call.apply(
                given()
                        .when()
                        .spec(requestSpec)
                        .body(requestBody)
        ).then()
                .spec(responseSpec);
    }

    public static ValidatableResponse post(final Object requestBody, final String serviceEndPoint, final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        return baseRequest(requestBody, requestSpec, responseSpec, req -> req.post(serviceEndPoint));
    }

    public static ValidatableResponse post(final Object requestBody, final URL serviceEndPoint, final RequestSpecification spec) {
        return post(requestBody, serviceEndPoint.toString(), spec, defaultResponseSpec());
    }

    public static ValidatableResponse post(final Object requestBody, final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        return post(requestBody, "", requestSpec, responseSpec);
    }

    public static ValidatableResponse post(final Object requestBody, final RequestSpecification spec) {
        return post(requestBody, "", spec, defaultResponseSpec());
    }

    public static ValidatableResponse put(final Object requestBody, final URL serviceEndPoint, final RequestSpecification spec) {
        return put(requestBody, serviceEndPoint.toString(), spec);
    }

    public static ValidatableResponse put(final Object requestBody, final String serviceEndPoint, final RequestSpecification spec) {
        response = given()
                .body(requestBody)
                .when().spec(spec)
                .put(serviceEndPoint)
                .then();
        return response;
    }

    public static ValidatableResponse get(final URL serviceEndPoint, final RequestSpecification spec) {
        return get(serviceEndPoint.toString(), spec);
    }

    public static ValidatableResponse get(final String serviceEndPoint, final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        response = given()
                .when()
                    .spec(requestSpec)
                    .get(serviceEndPoint)
                .then()
                    .spec(responseSpec);
        return response;
    }

    public static ValidatableResponse get(final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        return get("", requestSpec, responseSpec);
    }

    public static ValidatableResponse get(final String serviceEndPoint, final RequestSpecification requestSpec) {
        return get(serviceEndPoint, requestSpec, defaultResponseSpec());
    }

    public static ValidatableResponse get(final RequestSpecification requestSpec) {
        return get (requestSpec, defaultResponseSpec());
    }

    public static void urlEncoding(final boolean enabled) {
        RestAssured.urlEncodingEnabled = enabled;
    }

}
