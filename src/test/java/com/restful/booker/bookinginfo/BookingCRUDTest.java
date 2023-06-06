package com.restful.booker.bookinginfo;

import com.restful.booker.testbase.TestBaseBooking;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

@RunWith(SerenityRunner.class)
public class BookingCRUDTest extends TestBaseBooking {
    public static int bookingId;
    static String firstName = "PrimeUser" + TestUtils.getRandomValue();
    static String firstname = TestUtils.getRandomValue();
    static String lastname = TestUtils.getRandomValue();;
    static int totalprice;
    static boolean depositpaid;
    static String additionalneeds;

    @Steps
    BookingSteps bookingSteps;


    @Title("This will create a new booking")
    @Test
    public void test001() {
        HashMap<String, Object> bookingDates = new HashMap<String, Object>();
        bookingDates.put("checkin", "2022-01-01");
        bookingDates.put("checkout", "2022-02-01");

        ValidatableResponse response = bookingSteps.createANewBooking(firstname, lastname, totalprice, depositpaid, bookingDates, additionalneeds);
        response.log().all().statusCode(200);
        bookingId = response.extract().path("bookingid");
        System.out.println("myId ===== " + bookingId);


    }

    @Title("Verify if the booking was added to the application")
    @Test
    public void test002() {
        System.out.println("booking id is: " + bookingId);
        ValidatableResponse response = bookingSteps.getBookingInfoById(bookingId);
        response.log().all().statusCode(200);

    }


    @Title("Update the booking information and verify the updated information")
    @Test
    public void test003() {
        firstname = firstname + "_updated";
        lastname = lastname + "_updated";

        HashMap<String, Object> bookingDates = new HashMap<String, Object>();
        bookingDates.put("checkin", "2022-01-01");
        bookingDates.put("checkout", "2022-02-01");

        ValidatableResponse response = bookingSteps.updatingBooking(bookingId,firstname, lastname, totalprice, depositpaid, bookingDates, additionalneeds);
        response.statusCode(200).log().all();


    }

    @Title("Update the partial booking information and verify the updated information")
    @Test
    public void test004() {
        //firstname = firstname + "_updated";
        HashMap<String, Object> bookingDates = new HashMap<String, Object>();
        bookingDates.put("checkin", "2022-01-01");
        bookingDates.put("checkout", "2022-02-01");


        ValidatableResponse response = bookingSteps.updatingPartialBooking(bookingId,firstname, lastname, totalprice, depositpaid, bookingDates, additionalneeds);
        response.statusCode(200).log().all();

    }

    @Title("Deleting booking information")
    @Test
    public void test005() {
        bookingSteps.deleteBookingInfo(bookingId)
                        .statusCode(201);

        /*bookingSteps.getAllBookingInfo()
                .statusCode(404);*/

    }

    @Title("Get booking")
    @Test
    public void test006() {
        ValidatableResponse response = bookingSteps.getAllBookingInfo().log().all().statusCode(200);
    }


}
