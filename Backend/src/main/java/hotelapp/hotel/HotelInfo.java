package hotelapp.hotel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class stores an details of Hotel-s.
 * Used while parsing a json file that contains info about several hotels.
 */
public class HotelInfo {
    @Expose(serialize = true)
    @SerializedName(value = "hotelName", alternate = "f")
    private String hotelName;
    @Expose(serialize = true)
    @SerializedName(value = "hotelId", alternate = "id")
    private int hotelId;
    @Expose(serialize = true)
    @SerializedName("ll")
    private HotelGeoCoordinates hotelGeoCoordinates;
    @Expose(serialize = true)
    @SerializedName(value = "address", alternate = "ad")
    private String address;
    @Expose(serialize = true)
    @SerializedName(value = "city", alternate = "ci")
    private String city;
    @Expose(serialize = true)
    @SerializedName(value = "state", alternate = "pr")
    private String state;
    @Expose(serialize = true)
    @SerializedName(value = "country", alternate = "c")
    private String country;
    @Expose(serialize = true)
    @SerializedName("expedia_link")
    private String expediaLink;
    @Expose(serialize = true)
    private double overall_rating;
    @Expose(serialize = true)
    @SerializedName("image_path")
    private String imagePath;

    /**
     * Parameterize constructor for HotelInfo.
     *  @param hotelName      - name of hotel.
     * @param hotelId        - hotel's id
     * @param geoCoordinates - hotel geographical coordinates.
     * @param address        - address of the hotel
     * @param city           - city name
     * @param state          - state name.
     * @param country        - country name
     */
    public HotelInfo(String hotelName, int hotelId, HotelGeoCoordinates geoCoordinates, String address, String city,
                     String state, String country) {
        this.hotelName = hotelName;
        this.hotelId = hotelId;
        this.address = address;
        this.hotelGeoCoordinates = geoCoordinates;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public HotelInfo(){}

    /**
     * give the object of hotel's geo-coordinates class
     *
     * @return object of hotel geo-coordinate.
     */
    public HotelGeoCoordinates getHotelGeoCoordinates() {
        return hotelGeoCoordinates;
    }

    /**
     * gives the address of the hotel
     *
     * @return - address
     */
    public String getAddress() {
        return address;
    }

    /**
     * gives the city name where the hotel is located
     *
     * @return - city name
     */
    public String getCity() {
        return city;
    }

    /**
     * gives the state name where the hotel is located
     *
     * @return - state name
     */
    public String getState() {
        return state;
    }

    /**
     * gives the hotel name
     *
     * @return - hotel name
     */
    public String getHotelName() {
        return hotelName;
    }

    /**
     * give the hotel id
     *
     * @return hotel id
     */
    public int getHotelId() {
        return hotelId;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public void setHotelGeoCoordinates(HotelGeoCoordinates hotelGeoCoordinates) {
        this.hotelGeoCoordinates = hotelGeoCoordinates;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getExpediaLink() {
        return expediaLink;
    }

    public void setExpediaLink(String expediaLink) {
        this.expediaLink = expediaLink;
    }

    public double getOverall_rating() {
        return overall_rating;
    }

    public void setOverall_rating(double overall_rating) {
        this.overall_rating = overall_rating;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * toString() method
     *
     * @return a String representing this HotelInfo
     */
    @Override
    public String toString() {
        return "HotelInfo{" +
                "hotelName='" + hotelName + '\'' +
                ", hotelId=" + hotelId +
                ", hotelGeoCoordinates=" + hotelGeoCoordinates +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", expediaLink='" + expediaLink + '\'' +
                ", overall_rating=" + overall_rating +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
