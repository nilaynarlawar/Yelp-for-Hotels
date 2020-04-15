package hotelapp.hotel.review;

import com.google.gson.annotations.SerializedName;

/**
 * This class is used while parsing a json file that contains info about several hotels reviews.
 * specifically used to store the hotel id for each review json file.
 */
public class HotelReviewSummary {
    @SerializedName("hotelId")
    private int hotelId;

    /**
     * HotelReviewSummary parameterize constructor.
     *
     * @param hotelId Id of hotel
     */
    public HotelReviewSummary(int hotelId) {
        this.hotelId = hotelId;
    }

    /**
     * Give the hotel id.
     *
     * @return Hotel id.
     */
    public int getHotelId() {
        return hotelId;
    }

    /**
     * toString() method
     *
     * @return a String representing this HotelReviewSummary
     */
    @Override
    public String toString() {
        return "HotelReviewSummary{" + System.lineSeparator() +
                "hotelId: " + hotelId + System.lineSeparator() +
                "}";
    }
}
