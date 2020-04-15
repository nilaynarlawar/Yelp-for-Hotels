package hotelapp.hotel;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

/**
 * This class stores an ArrayList of Hotels.
 * This is used by GSON while parsing a json file that contains info about several hotels.
 */
public class Hotels {
    @SerializedName("sr")
    private List<HotelInfo> hotels;

    /**
     * Parameterize constructor of class Hotel
     *
     * @param hotels list of all hotels
     */
    public Hotels(List<HotelInfo> hotels) {
        this.hotels = hotels;
    }

    /**
     * gives the list of all the hotels.
     *
     * @return List of hotels.
     */
    public List<HotelInfo> getHotels() {
        return Collections.unmodifiableList(hotels);
    }

    /**
     * toString() method
     *
     * @return a String representing this Hotels
     */
    @Override
    public String toString() {
        return "Hotels{" + System.lineSeparator() +
                "hotels: " + hotels + System.lineSeparator() +
                "}";
    }
}
