package hotelapp.hotel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class stores an Geographic coordinates of Hotel-s.
 * Used while parsing a json file that contains info about hotels.
 */
public class HotelGeoCoordinates {
    @Expose(serialize = true)
    @SerializedName("lat")
    private double latitude;
    @Expose(serialize = true)
    @SerializedName("lng")
    private double longitude;

    /**
     * Constructor for HotelGeoCoordinates
     *
     * @param latitude  latitude of hotel
     * @param longitude longitude of hotel
     */
    public HotelGeoCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public HotelGeoCoordinates() {}


    /**
     * Gives the Latitude of the hotel
     *
     * @return Latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Gives the Longitude of the hotel
     *
     * @return Longitude
     */
    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * toString() method
     *
     * @return a String representing this HotelGeoCoordinates
     */
    @Override
    public String toString() {
        return "Geo-Coordinate{" + System.lineSeparator() +
                "latitude: " + latitude + ", " + System.lineSeparator() +
                "longitude: " + longitude + System.lineSeparator() +
                "}";
    }
}
