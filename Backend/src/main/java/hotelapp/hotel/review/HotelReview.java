package hotelapp.hotel.review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class stores details of Hotel-s reviews.
 * Used while parsing a json file that contains info about several hotels reviews.
 */
public class HotelReview implements Comparable<HotelReview> {
    @Expose(serialize = true)
    private int hotelId;
    @Expose(serialize = true)
    private String reviewId;
    @Expose(serialize = true)
    private int ratingOverall;
    @Expose(serialize = true)
    private String title;
    @Expose(serialize = true)
    private String reviewText;
    @Expose(serialize = true)
    @SerializedName(value = "user", alternate = "userNickname")
    private String user;
    @Expose(serialize = true)
    @SerializedName("reviewSubmissionTime")
    private String reviewPostDate;
    @Expose(serialize = true)
    private String isRecommended;
    @Expose(serialize = true)
    private int userid;
    @Expose(serialize = true)
    private String hotelName;
    @Expose(serialize = true)
    private int likedCount;
    @Expose(serialize = true)
    private int id;


    /**
     * Parameterize constructor for HotelReview class
     *
     * @param hotelId        - the id of the hotel
     * @param reviewId       - the id of the review
     * @param ratingOverall  - the rating of the hotel
     * @param title          -  the title of the review
     * @param reviewText     - the review text of the hotel
     * @param user           - the name of user who posted the review
     * @param reviewPostDate - the date on which review was posted
     * @param isRecommended  - the hotel was recommended by user or not -YES or NO.
     */
    public HotelReview(int hotelId, String reviewId, int ratingOverall, String title,
                       String reviewText, String user, String reviewPostDate, String isRecommended) {
        this.hotelId = hotelId;
        this.reviewId = reviewId;
        this.ratingOverall = ratingOverall;
        this.title = title;
        this.reviewText = reviewText;
        this.user = user;
        this.reviewPostDate = reviewPostDate;
        this.isRecommended = isRecommended;
    }

    public HotelReview(){}

    /**
     * Give the hotel id
     *
     * @return id of hotel
     */
    public int getHotelId() {
        return hotelId;
    }

    /**
     * Gives the rating for the specific hotel
     *
     * @return rating of the hotel
     */
    public int getRatingOverall() {
        return ratingOverall;
    }

    /**
     * Gives the title of of your review.
     *
     * @return review title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gives the review text posted by the user for specific hotel.
     *
     * @return review text of hotel.
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     * Gives the answer, that user recommend the specific hotel or not.
     * if user likes the hotel this will return YES otherwise NO as answer.
     *
     * @return YES or NO
     */
    public String getIsRecommended() {
        return isRecommended;
    }

    /**
     * Gives the id specific to review.
     *
     * @return review id.
     */
    public String getReviewId() {
        return reviewId;
    }

    /**
     * Gives the name of a user who posted the review for specific hotel.
     *
     * @return user name.
     */
    public String getUser() {
        return user.isEmpty() ? "Anonymous" : user;
    }

    /**
     * Gives the date on which review was posted.
     *
     * @return review posted date.
     */
    public String getReviewPostDate() {
        return reviewPostDate;
    }

    /**
     * Format the date(String) to specified format like "yyyy-MM-dd'T'HH:mm:ss"
     *
     * @return formated date.
     */
    public Date getFormattedDate() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            return format.parse(this.getReviewPostDate());
        } catch (ParseException e) {
            System.out.println("Invalid Date, error occurred during parsing the date as: " + e.getMessage());
            return null;
        }
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public void setRatingOverall(int ratingOverall) {
        this.ratingOverall = ratingOverall;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setReviewPostDate(String reviewPostDate) {
        this.reviewPostDate = reviewPostDate;
    }

    public void setIsRecommended(String isRecommended) {
        this.isRecommended = isRecommended;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(int likedCount) {
        this.likedCount = likedCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "HotelReview{" +
                "hotelId=" + hotelId +
                ", reviewId='" + reviewId + '\'' +
                ", ratingOverall=" + ratingOverall +
                ", title='" + title + '\'' +
                ", reviewText='" + reviewText + '\'' +
                ", user='" + user + '\'' +
                ", reviewPostDate='" + reviewPostDate + '\'' +
                ", isRecommended='" + isRecommended + '\'' +
                ", userid=" + userid +
                ", hotelName='" + hotelName + '\'' +
                '}';
    }

    /**
     * Comparator which compare the two reviews on the basis of below attributes:
     * 1] check review post date, if it equals then,
     * 2] check user name who post the review, if it equal then,
     * 3] check review id.
     * and sort the review according to latest review first.
     *
     * @param hr HotelReview object
     * @return Integer value as per comparison result.(0 , 1 or -1)
     */
    @Override
    public int compareTo(HotelReview hr) {
        if (this.getReviewPostDate() != null && hr.getReviewPostDate() != null &&
                !this.getReviewPostDate().equals(hr.getReviewPostDate())) {
            Date date1, date2;
            date1 = this.getFormattedDate();
            date2 = hr.getFormattedDate();
            if (date1 != date2)
                return date1.compareTo(date2) * -1;
        } else if (this.getUser() != null && hr.getUser() != null &&
                !this.getUser().equals(hr.getUser())) {
            return (this.getUser().compareTo(hr.getUser())) * -1;
        } else if (this.getReviewId() != null && hr.getReviewId() != null) {
            return this.getReviewId().compareTo(hr.getReviewId()) * -1;
        }
        return 0;
    }
}
