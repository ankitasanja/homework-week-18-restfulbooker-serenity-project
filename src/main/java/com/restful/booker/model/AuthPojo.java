package com.restful.booker.model;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;

public class AuthPojo {

    static String token;
    private String username;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public static String getAuthToken() {
        AuthPojo authPojo = new AuthPojo();
        authPojo.setUsername("admin");
        authPojo.setPassword("password123");
        token = SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(authPojo)
                .when()
                .post("https://restful-booker.herokuapp.com/auth")
                .then()
                .extract()
                .path("token");
        System.out.println("Token is: " + token);
        return token;
    }

}
