package hotelapp.hotel.review;

import com.google.gson.annotations.SerializedName;

import java.util.TreeSet;

/**
 * This class stores an Tress set of Hotel-s reviews.
 * Used while parsing a json file that contains info about several hotels reviews.
 */
public class Reviews {
    @SerializedName("reviewDetails")
    private HotelReviewDetails hotelReviewDetails;

    /**
     * Give the hotel id.
     *
     * @return hotel id.
     */
    public int getHotelId() {
        return hotelReviewDetails.getHotelId();
    }

    /**
     * Gives the TreeSet of HotelReview class.
     *
     * @return Set of HotelReview class.
     */
    public TreeSet<HotelReview> getHotelReviews() {
        return hotelReviewDetails.getHotelReviews();
    }

    /**
     * toString() method
     *
     * @return a String representing this Reviews
     */
    @Override
    public String toString() {
        return "Reviews{" + System.lineSeparator() +
                "hotelReviewDetails: " + hotelReviewDetails + System.lineSeparator() +
                "}";
    }
}
