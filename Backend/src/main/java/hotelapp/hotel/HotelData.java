package hotelapp.hotel;


import hotelapp.hotel.review.HotelReview;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class Hotel Data - it is parent class for ThreadSafeHotelData class.
 * Thread-safe, uses ReentrantReadWriteLock to synchronize access to all data structures.
 */
public class HotelData {
    Connection dbcntx;
    /**
     * Default constructor
     */
    public HotelData(Connection connection) {
            this.dbcntx =connection;
    }

    private void parseRating(int rating) throws InvalidParameterException {
        if (rating < 1 || rating > 5) {
            throw new InvalidParameterException("Invalid Rating");
        }
    }

    private void validateDate(String date) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.parse(date);
    }



    /**
     * Create a Hotel given for the parameters, and add it to the appropriate data
     * structure(s).
     *
     * @param hotelId       - the id of the hotel
     * @param hotelName     - the name of the hotel
     * @param city          - the city where the hotel is located
     * @param state         - the state where the hotel is located.
     * @param streetAddress - the building number and the street
     * @param lat           - Latitude related to location of Hotel
     * @param lon           - Longitude related to location of Hotel
     */
    public boolean addHotel(String hotelId, String hotelName, String city, String state, String streetAddress,
                         String country,
                         double lat,
                         double lon){
            try{

                String expediaLink = getExpediaLink(hotelId).toString();
                PreparedStatement sql;
                    sql = dbcntx.prepareStatement(CREATE_HOTEL_INFO_SQL);
                    sql.setInt(1, Integer.parseInt(hotelId));
                    sql.setString(2,hotelName);
                    sql.setDouble(3,lat);
                    sql.setDouble(4,lon);
                    sql.setString(5,city);
                    sql.setString(6,streetAddress);
                    sql.setString(7,country);
                    sql.setString(8,state);
                    sql.setString(9,expediaLink);
                    sql.setString(10,"0");
                    sql.setString(11,"IMAGE FILE PATH");
                    sql.executeUpdate();
                    return true;
            }
            catch (SQLException  e){
                System.err.println("Unable to connect properly to database.");
                System.err.println(e.getMessage());
                return false;
            }

    }

    private static final String CREATE_HOTEL_INFO_SQL = "INSERT INTO finalproject.hotel(" +
            "hotelid," +
            "hotel_name," +
            "lat," +
            "lng," +
            "city," +
            "address," +
            "country," +
            "state," +
            "expedia_link,"+
            "overall_rating," +
            "image_path)" +
            "VALUES(?,?,?,?,?,?,?,?,?,?,?)";



    /**
     * Create a hotel review for the parameters, and add it to the appropriate data.
     *
     * @param hotelId     - the id of the hotel reviewed
     * @param reviewId    - the id of the review
     * @param rating      - integer rating 1-5.
     * @param reviewTitle - the title of the review
     * @param review      - text of the review
     * @param isRecom     - whether the user recommends it or not
     * @param date        - date of the review
     * @param username    - the nickname of the user writing the review.
     * @return true if successful, false if unsuccessful because of invalid date
     * or rating. Needs to catch and handle the following exceptions:
     * ParseException if the date is invalid InvalidRatingException if
     * the rating is out of range
     */
    public boolean addReview(String hotelId, String reviewId, int rating, String reviewTitle, String review,
                             boolean isRecom, String date, String username) {

        try {
            validateDate(date);
            parseRating(rating);
            //String isRecommended = isRecom ? "Yes" : "NO";
                PreparedStatement sql;
                    sql = dbcntx.prepareStatement(CREATE_HOTEL_REVIEW_SQL);
                    sql.setString(1, reviewId);
                    sql.setInt(2, Integer.parseInt(hotelId));
                    sql.setDouble(3, rating);
                    sql.setString(4, review);
                    sql.setString(5, reviewTitle);
                    sql.setInt(6, 0);
                    sql.setString(7, date);
                    sql.setString(8, username);
                    sql.setBoolean(9, isRecom);
                    sql.executeUpdate();
            return true;
            }
        catch (SQLException | ParseException e){
                System.err.println("Unable to connect properly to database.");
                System.err.println(e.getMessage());
                return false;
            }
    }

    private static final String CREATE_HOTEL_REVIEW_SQL = "INSERT INTO finalproject.hotel_review(" +
            "reviewid," +
            "hotelid," +
            "rating," +
            "review_text," +
            "review_title," +
            "userid," +
            "review_post_date," +
            "username," +
            "is_recommended)" +
            "VALUES(?,?,?,?,?,?,?,?,?)";


    public boolean addBrands(){
        return true;
    }
    private URL getExpediaLink(String hotelId){
        URL url = null;

        try {
            url = new URL("https://www.expedia.com/h" + hotelId + ".Hotel-Information");
        }
        catch(MalformedURLException e){
            System.out.println(e.getMessage());
        }
        return url;

    }

}
