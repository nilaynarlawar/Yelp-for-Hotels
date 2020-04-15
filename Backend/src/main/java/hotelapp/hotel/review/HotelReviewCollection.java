package hotelapp.hotel.review;

import com.google.gson.annotations.SerializedName;

import java.util.TreeSet;

/**
 * This class stores a tree set of Hotel-s reviews. Tree set is used because
 * reviews should be in sorted order and non duplicates
 * Used while parsing a json file that contains info about several hotels reviews.
 */
public class HotelReviewCollection {

    @SerializedName("review")
    private TreeSet<HotelReview> hotelReviews;

    /**
     * Give the TreeSet of the HotelReviews.
     *
     * @return TreeSet of HotelReview
     */
    public TreeSet<HotelReview> getHotelReviews() {
        return new TreeSet<>(hotelReviews);
    }

    /**
     * toString() method
     *
     * @return a String representing this HotelReviewCollection
     */
    @Override
    public String toString() {
        return "HotelReviewCollection{" + System.lineSeparator() +
                "hotelReviews=" + hotelReviews + System.lineSeparator() +
                "}";
    }
}
