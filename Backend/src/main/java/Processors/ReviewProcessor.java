package Processors;

import hotelapp.hotel.review.HotelReview;
import utils.Utils;

import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Review DB Handler.
 */
public class ReviewProcessor {
    private Connection dbcntx;

    public ReviewProcessor(Connection connection) {
        this.dbcntx = connection;
    }

    public boolean createReview(HotelReview hotelReview) {
        try {

            PreparedStatement sql;
            sql = dbcntx.prepareStatement(CREATE_REVIEW_SQL);
            sql.setString(1, hotelReview.getReviewId());
            sql.setInt(2, hotelReview.getHotelId());
            sql.setInt(3, hotelReview.getRatingOverall());
            sql.setString(4, hotelReview.getReviewText());
            sql.setString(5, hotelReview.getTitle());
            sql.setInt(6, hotelReview.getUserid());
            sql.setString(7, hotelReview.getReviewPostDate());
            if (hotelReview.getIsRecommended().equalsIgnoreCase("YES") ||
                    hotelReview.getIsRecommended().equalsIgnoreCase("true"))
                sql.setBoolean(8, true);
            else
                sql.setBoolean(8, false);
            sql.setString(9, hotelReview.getUser());
            sql.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteReview(String reviewId) {
        try {

            PreparedStatement sql;
            sql = dbcntx.prepareStatement(DELETE_REVIEW_SQL);
            sql.setString(1, reviewId);
            sql.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return false;
        }
    }

    public List<HotelReview> getReviews(int hotelId) {
        List<HotelReview> reviews = new ArrayList<>();
        HotelReview hotelReview;
        try {
            PreparedStatement sql;
            sql = dbcntx.prepareStatement(GET_REVIEW_LIST_SQL);
            sql.setInt(1, hotelId);
            ResultSet result = sql.executeQuery();
            while (result.next()) {
                hotelReview = new HotelReview();
                hotelReview.setReviewId(result.getString("reviewid"));
                hotelReview.setHotelId(result.getInt("hotelid"));
                hotelReview.setRatingOverall(result.getInt("rating"));
                hotelReview.setReviewText(result.getString("review_text"));
                hotelReview.setTitle(result.getString("review_title"));
                hotelReview.setUserid(result.getInt("userid"));
                hotelReview.setReviewPostDate(result.getString("review_post_date"));
                hotelReview.setIsRecommended(result.getString("is_recommended"));
                hotelReview.setUser(result.getString("username"));
                hotelReview.setHotelName(result.getString("hotel_name"));
                hotelReview.setId(result.getInt("id"));
                hotelReview.setLikedCount(result.getInt("liked_by"));
                reviews.add(hotelReview);
            }
            return reviews;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return reviews;
        }
    }

    public List<HotelReview> getReviewListByUser(int userId, int hotelid) {
        List<HotelReview> reviews = new ArrayList<>();
        HotelReview hotelReview;
        try {
            PreparedStatement sql;
            sql = dbcntx.prepareStatement(GET_REVIEW_LIST_BY_USER_ID_SQL);
            sql.setInt(1, userId);
            sql.setInt(2, hotelid);
            ResultSet result = sql.executeQuery();
            while (result.next()) {
                hotelReview = new HotelReview();
                hotelReview.setReviewId(result.getString("reviewid"));
                hotelReview.setHotelId(result.getInt("hotelid"));
                hotelReview.setRatingOverall(result.getInt("rating"));
                hotelReview.setReviewText(result.getString("review_text"));
                hotelReview.setTitle(result.getString("review_title"));
                hotelReview.setUserid(result.getInt("userid"));
                hotelReview.setReviewPostDate(result.getString("review_post_date"));
                hotelReview.setIsRecommended(result.getString("is_recommended"));
                hotelReview.setUser(result.getString("username"));
                hotelReview.setHotelName(result.getString("hotel_name"));
                hotelReview.setId(result.getInt("id"));
                hotelReview.setLikedCount(result.getInt("liked_by"));
                reviews.add(hotelReview);
            }
            return reviews;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return reviews;
        }
    }

    public HotelReview getReview(String reviewId) {
        HotelReview hotelReview;
        try {
            PreparedStatement sql;
            hotelReview = new HotelReview();
            sql = dbcntx.prepareStatement(GET_REVIEW_BY_ID_SQL);
            sql.setString(1, reviewId);
            ResultSet result = sql.executeQuery();
            while (result.next()) {
                hotelReview.setReviewId(result.getString("reviewid"));
                hotelReview.setHotelId(result.getInt("hotelid"));
                hotelReview.setRatingOverall(result.getInt("rating"));
                hotelReview.setReviewText(result.getString("review_text"));
                hotelReview.setTitle(result.getString("review_title"));
                hotelReview.setUserid(result.getInt("userid"));
                hotelReview.setReviewPostDate(result.getString("review_post_date"));
                hotelReview.setIsRecommended(result.getString("is_recommended"));
                hotelReview.setUser(result.getString("username"));
                hotelReview.setUser(result.getString("hotelName"));
            }
            return hotelReview;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return new HotelReview();
        }
    }

    public boolean updateReview(HotelReview hotelReview) {
        try {

            PreparedStatement sql;
            sql = dbcntx.prepareStatement(UPDATE_REVIEW_SQL);
            sql.setInt(1, hotelReview.getRatingOverall());
            sql.setString(2, hotelReview.getReviewText());
            sql.setString(3, hotelReview.getTitle());
            sql.setInt(4, hotelReview.getId());
            sql.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean reviewLike(String userId, String reviewId) {
        try {

            PreparedStatement sql;
            sql = dbcntx.prepareStatement(UPDATE_LIKED_REVIEW_SQL);
            sql.setString(1, userId);
            sql.setString(2, reviewId);
            sql.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean reviewUnlike(String userId, String reviewId) {
        try {

            PreparedStatement sql;
            sql = dbcntx.prepareStatement(DELETE_LIKED_REVIEW_SQL);
            sql.setString(1, userId);
            sql.setString(2, reviewId);
            sql.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return false;
        }
    }

    public Map<Integer, String> getHotelListByUserId(int userId) {
        Map<Integer, String> hotelMap = new HashMap<>();
        try {
            PreparedStatement sql;
            sql = dbcntx.prepareStatement(GET_HOTEL_LIST_BY_USER_ID_SQL);
            sql.setInt(1, userId);
            ResultSet result = sql.executeQuery();
            while (result.next()) {
                hotelMap.put(result.getInt("hotelid"), result.getString("hotel_name"));
            }
            return hotelMap;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return hotelMap;
        }
    }

    public List<Integer> getUserLikedReviews(int userId) {
        List<Integer> likedList = new ArrayList<>();
        try {
            PreparedStatement sql;
            sql = dbcntx.prepareStatement(GET_USER_LIKED_REVIEW_LIST_SQL);
            sql.setInt(1, userId);
            ResultSet result = sql.executeQuery();
            while (result.next()) {
                likedList.add(result.getInt("reviewid"));
            }
            return likedList;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return likedList;
        }
    }

    private static final String UPDATE_REVIEW_SQL = "UPDATE finalproject.hotel_review SET" +
            " rating=?," +
            " review_text=?," +
            " review_title=?" +
            " WHERE id=?";

    private static final String DELETE_REVIEW_SQL = "DELETE FROM finalproject.hotel_review WHERE id = ?";

    private static final String CREATE_REVIEW_SQL = "INSERT INTO finalproject.hotel_review(" +
            "reviewId," +
            "hotelId," +
            "rating," +
            "review_text," +
            "review_title," +
            "userid," +
            "review_post_date," +
            "is_recommended," +
            "username)" +
            "VALUES(?,?,?,?,?,?,?,?,?)";

    private static final String GET_REVIEW_LIST_SQL = "SELECT count(liked.reviewid) liked_by, review.*, " +
            " hotel_name FROM finalproject.hotel_review review" +
            " INNER JOIN finalproject.hotel hotel ON review.hotelid = hotel.hotelid" +
            " LEFT OUTER JOIN finalproject.review_like liked ON liked.reviewid = review.id" +
            " group by review.id " +
            " having review.hotelid = ?";

    private static final String GET_REVIEW_LIST_BY_USER_ID_SQL = "SELECT count(liked.reviewid) liked_by, review.*, " +
            "hotel_name FROM finalproject.hotel_review review" +
            " INNER JOIN finalproject.hotel hotel ON review.hotelid = hotel.hotelid " +
            "LEFT OUTER JOIN finalproject.review_like liked ON liked.reviewid = review.id " +
            "group by review.id " +
            "having review.userid = ? and review.hotelid = ?";

    private static final String GET_REVIEW_BY_ID_SQL = "SELECT * FROM finalproject.hotel_review WHERE reviewid = ?";

    private static final String UPDATE_LIKED_REVIEW_SQL = "INSERT INTO finalproject.review_like (" +
            "userid," +
            "reviewid)" +
            "VALUES (?,?)";

    private static final String DELETE_LIKED_REVIEW_SQL = "DELETE FROM finalproject.review_like" +
            " WHERE userid = ? AND reviewid = ?";

    private static final String GET_HOTEL_LIST_BY_USER_ID_SQL = "SELECT distinct hotel_name, hotel.hotelid" +
            " FROM finalproject.hotel_review review" +
            " INNER JOIN finalproject.hotel hotel ON review.hotelid = hotel.hotelid" +
            " WHERE review.userid = ?";

    private static final String GET_USER_LIKED_REVIEW_LIST_SQL = "SELECT reviewid FROM finalproject.review_like" +
            " WHERE userid= ?";

}
