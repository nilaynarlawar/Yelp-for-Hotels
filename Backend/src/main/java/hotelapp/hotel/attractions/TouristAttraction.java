package hotelapp.hotel.attractions;

import com.google.gson.annotations.SerializedName;

/**
 * This is a TouristAttraction class which have data related to attraction points.
 */
public class TouristAttraction {
    private String id;
    private String name;
    private double rating;
    @SerializedName("formatted_address")
    private String address;

    public TouristAttraction() {
    }

    /**
     * This method returns the attraction ID.
     *
     * @return attarction ID
     */
    public String getId() {
        return id;
    }

    /**
     * This method returns the attraction place name.
     *
     * @return name of attraction
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns rating of the attraction place
     *
     * @return rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * This method returns address of the attraction place.
     *
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * toString() method
     *
     * @return a String representing this TouristAttraction
     */
    @Override
    public String toString() {
        return "TouristAttraction{" +
                "id: " + id +
                ", name: " + name +
                ", rating: " + rating +
                ", address: " + address +
                "}" + System.lineSeparator();
    }
}
