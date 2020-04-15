package user;

import com.google.gson.annotations.Expose;

public class UserHistory {
    @Expose(serialize = true)
    private String hotelName;
    @Expose(serialize = true)
    private String expediaLink;
    @Expose(serialize = true)
    private String hotelId;
    @Expose(serialize = true)
    private String address;
    @Expose(serialize = true)
    private String city;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getExpediaLink() {
        return expediaLink;
    }

    public void setExpediaLink(String expediaLink) {
        this.expediaLink = expediaLink;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "UserHistory{" +
                "hotelName='" + hotelName + '\'' +
                ", expediaLink='" + expediaLink + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
