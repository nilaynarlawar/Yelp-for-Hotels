package hotelapp.hotel.review;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This class is used while parsing a json file that contains info about several hotels reviews.
 */
public class HotelReviewSummaryCollection {
    @SerializedName("reviewSummary")
    private List<HotelReviewSummary> hotelReviewSummaries;

    /**
     * HotelReviewSummaryCollection parameterize constructor.
     *
     * @param hotelReviewSummaries List of HotelReviewSummaries class object.
     */
    public HotelReviewSummaryCollection(List<HotelReviewSummary> hotelReviewSummaries) {
        this.hotelReviewSummaries = hotelReviewSummaries;
    }

    /**
     * Gives hotel id.
     *
     * @return Hotel id.
     */
    public int getHotelId() {
        if (hotelReviewSummaries.size() > 0)
            return hotelReviewSummaries.get(0).getHotelId();
        return -1;
    }

    /**
     * toString() method
     *
     * @return a String representing this HotelReviewSummaryCollection
     */
    @Override
    public String toString() {
        return "HotelReviewSummaryCollection{" + System.lineSeparator() +
                "hotelReviewSummaries: " + hotelReviewSummaries + System.lineSeparator() +
                "}";
    }
}
