package user;

import com.google.gson.annotations.Expose;

import java.sql.Time;
import java.sql.Timestamp;

public class UserInfo {
    @Expose(serialize = true)
    private String userName;
    @Expose(serialize = true)
    private String password;
    @Expose(serialize = true)
    private String address;
    @Expose(serialize = true)
    private String city;
    @Expose(serialize = true)
    private String country;
    @Expose(serialize = true)
    private String zipCode;
    @Expose(serialize = true)
    private String emailId;
    @Expose(serialize = true)
    private Timestamp lastLogin;
    @Expose(serialize = true)
    private String creationTime;
    @Expose(serialize = true)
    private boolean isAdmin;
    @Expose(serialize = true)
    private String salt;
    @Expose(serialize = true)
    private int userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", emailId='" + emailId + '\'' +
                ", lastLogin='" + lastLogin + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", isAdmin=" + isAdmin +
                ", salt='" + salt + '\'' +
                ", userId=" + userId +
                '}';
    }
}
