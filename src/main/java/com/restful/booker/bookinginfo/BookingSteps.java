package com.restful.booker.bookinginfo;

import com.restful.booker.constants.EndPoints;
import com.restful.booker.model.AuthPojo;
import com.restful.booker.model.BookingPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class BookingSteps {
    static String token;

    @Step("Creating a new Booking with firstname: {0},lastname: {1},totalPrice: {2}, depositPaid: {3}, booking: {4}, additionalneeds: {5}")
    public ValidatableResponse createANewBooking(String firstname, String lastname, int totalprice, boolean depositpaid, HashMap<String, Object> booking, String additionalneeds) {

        BookingPojo bookingPojo = BookingPojo.getBookingPojo(firstname, lastname, totalprice, depositpaid, booking, additionalneeds);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .when()
                .body(bookingPojo)
                .post()
                .then();

    }

    @Step("Getting booking information with bookingId: {0}")
    public ValidatableResponse getBookingInfoById(int bookingId) {
        //find the new record by id
        return SerenityRest.given().log().all()
                .header("Accept", "application/json")
                //.contentType(ContentType.JSON)
                .pathParam("bookingid", bookingId)
                .when()
                .get(EndPoints.GET_SINGLE_BOOKING_BY_ID)
                .then();
    }

    @Step("Getting all the booking information")
    public ValidatableResponse getAllBookingInfo() {
        return SerenityRest.given().log().all()
                .when()
                .get()
                .then();
    }

    @Step("Updating the booking information with bookingid: {0},firstname: {1},lastname: {2},totalPrice: {3}, depositPaid: {4}, booking: {5}, additionalneeds: {6}")
    public ValidatableResponse updatingBooking(int bookingId,String firstname, String lastname, int totalprice, boolean depositpaid, HashMap<String, Object> booking, String additionalneeds) {
        token = "token=" + AuthPojo.getAuthToken();

        BookingPojo bookingPojo = BookingPojo.getBookingPojo(firstname, lastname, totalprice, depositpaid, booking, additionalneeds);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("Content-Type", "application/json")
                .header("cookie", token)
                .pathParam("bookingid", bookingId)
                .body(bookingPojo)
                .when()
                .put(EndPoints.UPDATE_BOOKING_BY_ID)
                .then();

    }

    @Step("Updating partial booking information with bookingid: {0},firstname: {1},lastname: {2},totalPrice: {3}, depositPaid: {4}, booking: {5}, additionalneeds: {6}")
    public ValidatableResponse updatingPartialBooking(int bookingId, String firstname, String lastname, int totalprice, boolean depositpaid, HashMap<String, Object> booking, String additionalneeds) {

        BookingPojo bookingPojo = BookingPojo.getBookingPojo(firstname, lastname, totalprice, depositpaid, booking, additionalneeds);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("cookie", token)
                .body("{\"firstname\":\"_updated\"}")
                .pathParam("bookingid", bookingId)
                .when()
                .patch(EndPoints.UPDATE_BOOKING_BY_ID)
                .then();
    }

//       Response response = request.body("{ \"firstname\": \"Patch firstname\" }")
//                .patch("/" + newId);
//        response.then().statusCode(200).log().all();
//        System.out.println("patch----> " + response.asString());*//*

//        *//*//*/ //Get status line
//        String statusLine = response.getStatusLine();
//        System.out.println("Status line: " + statusLine);
//        Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
//
//        // Get status code
//        int statusCode = response.getStatusCode();
//        Assert.assertEquals(statusCode, 200);*//*
//    }
/*
    @Step("Updating the booking information with bookingid: {0},firstname: {1},lastname: {2},totalPrice: {3}, depositPaid: {4}, booking: {5}, additionalneeds: {6}")
    public ValidatableResponse updateBookingPartiallyById( int bookingId,String firstname, String lastname, int totalprice,
                                                          boolean depositpaid, HashMap<String, Object> bookingdates,
                                                          String additionalneeds) {
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .header("cookie", token)
                .body()
                .pathParam("bookingId", bookingId)
                .when()
                .patch(EndPoints.UPDATE_BOOKING_BY_ID)
                .then()
                .statusCode(200)
                .log().all();
    }*/

    @Step("Deleting booking information with bookingid: {0}")
    public ValidatableResponse deleteBookingInfo(int bookingId) {
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("Cookie", token)
                .pathParam("bookingid", bookingId)
                .when()
                .delete(EndPoints.DELETE_BOOKING_BY_ID)
                .then();

    }
}
