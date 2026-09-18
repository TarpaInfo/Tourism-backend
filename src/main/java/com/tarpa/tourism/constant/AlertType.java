package com.tarpa.tourism.constant;

public final class AlertType {

    private AlertType() {
    }

    // ==========================
    // BOOKING
    // ==========================

    public static final String BOOKING_CONFIRMED =
            "BOOKING_CONFIRMED";

    public static final String BOOKING_UPDATED =
            "BOOKING_UPDATED";

    public static final String BOOKING_CANCELLED =
            "BOOKING_CANCELLED";


    // ==========================
    // ARRIVAL
    // ==========================

    public static final String ARRIVAL_REMINDER =
            "ARRIVAL_REMINDER";

    public static final String AIRPORT_PICKUP =
            "AIRPORT_PICKUP";


    // ==========================
    // HOTEL
    // ==========================

    public static final String HOTEL_BOOKING =
            "HOTEL_BOOKING";

    public static final String HOTEL_CHECKIN_REMINDER =
            "HOTEL_CHECKIN_REMINDER";

    public static final String HOTEL_CHECKIN =
            "HOTEL_CHECKIN";

    public static final String HOTEL_CHECKOUT_REMINDER =
            "HOTEL_CHECKOUT_REMINDER";

    public static final String HOTEL_CHECKOUT =
            "HOTEL_CHECKOUT";


    // ==========================
    // TRANSPORTATION
    // ==========================

    public static final String DOMESTIC_FLIGHT =
            "DOMESTIC_FLIGHT";

    public static final String DOMESTIC_FLIGHT_REMINDER =
            "DOMESTIC_FLIGHT_REMINDER";

    public static final String TRANSPORTATION =
            "TRANSPORTATION";

    public static final String AIRPORT_TRANSFER =
            "AIRPORT_TRANSFER";


    // ==========================
    // TOUR
    // ==========================

    public static final String TOUR_START =
            "TOUR_START";

    public static final String TOUR_RETURN =
            "TOUR_RETURN";


    // ==========================
    // TREKKING
    // ==========================

    public static final String TREKKING_START =
            "TREKKING_START";

    public static final String TREKKING_RETURN =
            "TREKKING_RETURN";


    // ==========================
    // PEAK CLIMBING
    // ==========================

    public static final String PEAK_CLIMBING_START =
            "PEAK_CLIMBING_START";

    public static final String PEAK_CLIMBING_RETURN =
            "PEAK_CLIMBING_RETURN";


    // ==========================
    // MOUNTAIN EXPEDITION
    // ==========================

    public static final String EXPEDITION_START =
            "EXPEDITION_START";

    public static final String EXPEDITION_RETURN =
            "EXPEDITION_RETURN";


    // ==========================
    // HELI TOUR
    // ==========================

    public static final String HELI_TOUR_START =
            "HELI_TOUR_START";

    public static final String HELI_TOUR_RETURN =
            "HELI_TOUR_RETURN";



    // DEPARTURE

    public static final String DEPARTURE_REMINDER =
            "DEPARTURE_REMINDER";

    public static final String AIRPORT_DROPOFF =
            "AIRPORT_DROPOFF";


    //Payment Reminder
    public static final String PAYMENT_REMINDER = "PAYMENT_REMINDER";
    public static final String PAYMENT_RECEIVED = "PAYMENT_RECEIVED";

    //Trip Feedback
    public static final String TRIP_FEEDBACK = "TRIP_FEEDBACK";
}