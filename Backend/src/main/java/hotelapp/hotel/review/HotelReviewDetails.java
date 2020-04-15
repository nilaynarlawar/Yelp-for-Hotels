package hotelapp.hotel.review;

import com.google.gson.annotations.SerializedName;

import java.util.TreeSet;

/**
 * This class is used while parsing a json file that contains info about several hotels reviews.
 */
public class HotelReviewDetails {
    @SerializedName("reviewCollection")
    private HotelReviewCollection hotelReviewCollection;
    @SerializedName("reviewSummaryCollection")
    private HotelReviewSummaryCollection hotelReviewSummaryCollection;

    /**
     * Give the id of the hotel.
     *
     * @return hotel id
     */
    public int getHotelId() {
        return hotelReviewSummaryCollection.getHotelId();
    }

    /**
     * Give the TreeSet of the Hotel Reviews
     *
     * @return TreeSet of HotelReviews
     */
    public TreeSet<HotelReview> getHotelReviews() {
        return hotelReviewCollection.getHotelReviews();
    }

    /**
     * toString() method
     *
     * @return a String representing this HotelReviewDetails
     */
    @Override
    public String toString() {
        return "HotelReviewDetails{" + System.lineSeparator() +
                "hotelReviewCollection: " + hotelReviewCollection + "," + System.lineSeparator() +
                "hotelReviewSummaryCollection: " + hotelReviewSummaryCollection + System.lineSeparator() +
                "}";
    }
}
