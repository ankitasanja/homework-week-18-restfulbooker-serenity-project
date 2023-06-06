package com.restful.booker.bookinginfo;

import com.restful.booker.testbase.TestBaseBooking;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import net.thucydides.junit.annotations.Concurrent;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;

import java.util.HashMap;

@Concurrent(threads = "4x")
@UseTestDataFrom("src/test/java/resources/testdata/bookinginfo.csv")
//@RunWith(SerenityParameterizedRunner.class)
public class CreateBookingDataDrivenTest extends TestBaseBooking {

    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private String checkin;
    private String checkout;
    private String additionalneeds;

    @Steps
    BookingSteps bookingSteps;

    @Title("Data driven test for adding multiple bookings to the application")
    @Test
    public void createMultipleBooking() {
        HashMap<String, Object> bookingDates = new HashMap<String, Object>();
        bookingDates.put("checkin", "2022-01-01");
        bookingDates.put("checkout", "2022-02-01");
        bookingSteps.createANewBooking(firstname,lastname,totalprice,depositpaid,bookingDates,additionalneeds).statusCode(200);
    }
}
