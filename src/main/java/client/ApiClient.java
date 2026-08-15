package client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ApiClient {

    public static Response post(String endpoint, String body) {
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(endpoint);
    }

    public static Response get(String endpoint) {
        return given()
                .when()
                .get(endpoint);
    }
    public static Response put(String endpoint, String body, String Token) {
		return given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + Token)
                .body(body)
                .when()
                .put(endpoint);
	}
    
    public static Response delete( String endpoint,String Token) {
		return given()
				.header("Content-Type", "application/json")
				.header("Cookie", "token=" + Token)
				.when()
				.delete(endpoint);
}}

