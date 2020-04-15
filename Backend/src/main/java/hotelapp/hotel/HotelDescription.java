package hotelapp.hotel;

/**
 * This class is responsible for providing data related to hotel description.
 */
public class HotelDescription {
    private String propertyHeader;
    private String areaHeader;
    private String propertyDescription;
    private String areaDescription;

    /**
     * Parameterize constructor for HotelDescription class
     *
     * @param propertyDescription hotel property description
     * @param areaDescription     hotel area description
     */
    public HotelDescription(String propertyHeader, String areaHeader, String propertyDescription, String areaDescription) {
        this.propertyHeader = propertyHeader;
        this.areaHeader = areaHeader;
        this.propertyDescription = propertyDescription;
        this.areaDescription = areaDescription;
    }

    /**
     * Return the property description of hotel.
     *
     * @return hotel property description
     */
    public String getPropertyDescription() {
        return propertyDescription;
    }

    /**
     * Returns the area description of hotel
     *
     * @return hotel area description.
     */
    public String getAreaDescription() {
        return areaDescription;
    }

    /**
     * Returns the header above the property description
     *
     * @return header text
     */
    public String getPropertyHeader() {
        return propertyHeader;
    }

    /**
     * Returns the header above the area description
     *
     * @return header text
     */
    public String getAreaHeader() {
        return areaHeader;
    }

    /**
     * toString() method
     *
     * @return a String representing this HotelDescription
     */
    @Override
    public String toString() {
        return "HotelDescription{" + System.lineSeparator() +
                "propertyHeader=" + propertyHeader + System.lineSeparator() +
                "areaHeader=" + areaHeader + System.lineSeparator() +
                "propertyDescription=" + propertyDescription + System.lineSeparator() +
                "areaDescription=" + areaDescription + System.lineSeparator() +
                "}";
    }
}
