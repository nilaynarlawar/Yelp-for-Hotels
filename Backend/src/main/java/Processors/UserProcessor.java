package Processors;


import hotelapp.hotel.HotelInfo;
import user.UserHistory;
import user.UserInfo;
import utils.Utils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserProcessor {
    private Connection dbcntx;
    private Random random;
    private static final int SESSION_TIMEOUT = 15;

    public UserProcessor(Connection connection) {
        this.dbcntx = connection;
        random = new Random(System.currentTimeMillis());
    }

    public boolean createUser(UserInfo userInfo) {
        try {

            byte[] saltBytes = new byte[16];
            random.nextBytes(saltBytes);
            String userSalt = getSalt(saltBytes, 32);
            String passhash = getHash(userInfo.getPassword(), userSalt);
            String date = getCurrentDate();
            PreparedStatement sql;
            sql = dbcntx.prepareStatement(CREATE_USER_SQL);
            sql.setString(1, userInfo.getUserName());
            sql.setString(2, passhash);
            sql.setString(3, userInfo.getAddress());
            sql.setString(4, userInfo.getCity());
            sql.setString(5, userInfo.getCountry());
            sql.setString(6, userInfo.getZipCode());
            sql.setString(7, userInfo.getEmailId());
            sql.setString(8, date);
            sql.setBoolean(9, userInfo.isAdmin());
            sql.setString(10, userSalt);
            sql.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Returns the hex encoding of a byte array.
     *
     * @param bytes  - byte array to encode
     * @param length - desired length of encoding
     * @return hex encoded byte array
     */
    public static String getSalt(byte[] bytes, int length) {
        BigInteger bigint = new BigInteger(1, bytes);
        String hex = String.format("%0" + length + "X", bigint);

        assert hex.length() == length;
        return hex;
    }


    /**
     * Calculates the hash of a password and salt using SHA-256.
     *
     * @param password - password to hash
     * @param salt     - salt associated with user
     * @return hashed password
     */
    public static String getHash(String password, String salt) {
        String salted = salt + password;
        String hashed = salted;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salted.getBytes());
            hashed = getSalt(md.digest(), 64);
        } catch (Exception ex) {
            System.out.println("Unable to properly hash password." + ex);
        }

        return hashed;
    }

    public String getCurrentDate() {
        String creationDate;
        String format = "yyyy-MM-dd hh:mm a";
        DateFormat formatter = new SimpleDateFormat(format);
        creationDate = formatter.format(Calendar.getInstance().getTime());
        return creationDate;
    }

    public UserInfo getUser(int userId) {
        UserInfo userInfo;
        try {
            PreparedStatement sql;
            userInfo = new UserInfo();
            sql = dbcntx.prepareStatement(GET_USER_INFO_BY_ID_SQL);
            sql.setInt(1, userId);
            ResultSet result = sql.executeQuery();
            while (result.next()) {
                userInfo.setUserId(result.getInt("userid"));
                userInfo.setUserName(result.getString("username"));
                userInfo.setPassword(result.getString("password"));
                userInfo.setAddress(result.getString("address"));
                userInfo.setCity(result.getString("city"));
                userInfo.setCountry(result.getString("country"));
                userInfo.setZipCode(result.getString("zip_code"));
                userInfo.setEmailId(result.getString("email_address"));
                userInfo.setLastLogin(result.getTimestamp("last_login"));
                userInfo.setCreationTime(result.getString("creation_time"));
                userInfo.setAdmin(result.getBoolean("is_admin"));
                userInfo.setSalt(result.getString("salt"));
            }
            return userInfo;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return new UserInfo();
        }
    }

    public String createUserSession(int userId) {
        try {
            UUID uuid = UUID.randomUUID();
            String sessionId = uuid.toString();

            PreparedStatement sql;
            sql = dbcntx.prepareStatement(CREATE_USER_SESSION_SQL);
            sql.setInt(1, userId);
            sql.setString(2, sessionId);
            sql.setTimestamp(3, Utils.getFutureTimestamp(SESSION_TIMEOUT));
            sql.executeUpdate();
            return sessionId;

        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public boolean deleteUserSession(int userId) {
        try {
            PreparedStatement sql;
            sql = dbcntx.prepareStatement(DELETE_USER_SESSION_SQL);
            sql.setInt(1, userId);
            sql.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean isUserSessionValid(int userId, String sessionId) {
        try {
            PreparedStatement sql;
            Timestamp sessiontime = null;
            sql = dbcntx.prepareStatement(GET_USER_SESSION_SQL);
            sql.setInt(1, userId);
            sql.setString(2, sessionId);
            ResultSet result = sql.executeQuery();
            while (result.next()) {
                sessiontime = result.getTimestamp("session_exp");
            }
            if (sessiontime != null)
                return !Utils.getCurrentSQLTimestamp().after(sessiontime);
            else {
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return false;
        }
    }

    public void updateSessionExp(int userId, String sessionId) {
        try {
            PreparedStatement sql;
            Timestamp sessiontime = null;
            sql = dbcntx.prepareStatement(SET_USER_SESSION_EXP_TIME_SQL);
            sql.setTimestamp(1, Utils.getFutureTimestamp(SESSION_TIMEOUT));
            sql.setInt(2, userId);
            sql.setString(3, sessionId);
            sql.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
        }
    }

    public boolean updateUserHistory(int userId, int hotelId) {
        try {
            PreparedStatement sql;
            sql = dbcntx.prepareStatement(CREATE_USER_HISTORY_SQL);
            sql.setInt(1, userId);
            sql.setInt(2, hotelId);
            sql.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean updateSaveHotel(int userId, int hotelId) {
        try {
            PreparedStatement sql;
            sql = dbcntx.prepareStatement(CREATE_USER_SAVED_HOTEL);
            sql.setInt(1, userId);
            sql.setInt(2, hotelId);
            sql.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteSaveHotel(int userId) {
        try {
            PreparedStatement sql;
            sql = dbcntx.prepareStatement(DELETE_USER_SAVED_HOTEL);
            sql.setInt(1, userId);
            sql.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteUserHistory(int userId) {
        try {

            PreparedStatement sql;
            sql = dbcntx.prepareStatement(DELETE_USER_HISTORY_SQL);
            sql.setInt(1, userId);
            sql.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return false;
        }
    }

    public UserInfo isValidUser(String userName, String password) {
        try {
            Pattern p = Pattern.compile("^(?=.*[\\w])(?=.*[\\d])(?=.*[\\W])[\\w\\W]{5,10}$");
            Matcher m = p.matcher(password);
            if (!m.matches()) {
                return new UserInfo();
            }
            PreparedStatement sql;
            UserInfo userInfo = new UserInfo();
            String userSalt = getSalt(userName);
            String passhash = getHash(password, userSalt);
            String dbPassword;
            sql = dbcntx.prepareStatement(VALID_USER_SQL);
            sql.setString(1, userName);
            ResultSet resultSet = sql.executeQuery();
            if (resultSet.next()) {
                userInfo.setUserId(resultSet.getInt("userid"));
                userInfo.setUserName(resultSet.getString("username"));
                userInfo.setPassword(resultSet.getString("password"));
                userInfo.setAddress(resultSet.getString("address"));
                userInfo.setCity(resultSet.getString("city"));
                userInfo.setCountry(resultSet.getString("country"));
                userInfo.setZipCode(resultSet.getString("zip_code"));
                userInfo.setEmailId(resultSet.getString("email_address"));
                userInfo.setLastLogin(resultSet.getTimestamp("last_login"));
                userInfo.setCreationTime(resultSet.getString("creation_time"));
                userInfo.setAdmin(resultSet.getBoolean("is_admin"));
                userInfo.setSalt(resultSet.getString("salt"));

                dbPassword = resultSet.getString("password");

                if (dbPassword.equals(passhash)) {
                    return userInfo;
                } else {
                    return userInfo;
                }
            } else {
                return userInfo;
            }
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return new UserInfo();
        }
    }

    public List<UserHistory> getUserHistory(int userId) {
        List<UserHistory> history = new ArrayList<>();
        UserHistory userHistory;
        try {
            PreparedStatement sql;
            sql = dbcntx.prepareStatement(GET_USER_HISTORY);
            sql.setInt(1, userId);
            ResultSet result = sql.executeQuery();
            while (result.next()) {
                userHistory = new UserHistory();
                userHistory.setHotelName(result.getString("hotel_name"));
                userHistory.setExpediaLink(result.getString("expedia_link"));
                history.add(userHistory);
            }
            return history;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return history;
        }
    }

    public List<UserHistory> getSavedHotel(int userId) {
        List<UserHistory> history = new ArrayList<>();
        UserHistory userHistory;
        try {
            PreparedStatement sql;
            sql = dbcntx.prepareStatement(GET_USER_SAVED_HOTEL);
            sql.setInt(1, userId);
            ResultSet result = sql.executeQuery();
            while (result.next()) {
                userHistory = new UserHistory();
                userHistory.setHotelName(result.getString("hotel_name"));
                userHistory.setCity(result.getString("city"));
                userHistory.setAddress(result.getString("address"));
                userHistory.setHotelId(result.getString("hotelid"));
                history.add(userHistory);
            }
            return history;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return history;
        }
    }

    public void updateLoginTime(int userId, Timestamp currentSQLTimestamp) {
        PreparedStatement sql;
        try {
            sql = dbcntx.prepareStatement(UPDATE_USER_LAST_LOGIN_SQL);
            sql.setTimestamp(1, currentSQLTimestamp);
            sql.setInt(2, userId);
            sql.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private String getSalt(String userName) {
        String salt = null;
        try {
            PreparedStatement sql;
            sql = dbcntx.prepareStatement(GET_USER_SALT);
            sql.setString(1, userName);
            ResultSet resultSet = sql.executeQuery();
            if (resultSet.next()) {
                salt = resultSet.getString("salt");
                return salt;
            } else {
                return salt;
            }

        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return salt;
        }
    }

    private static final String CREATE_USER_SQL = "INSERT INTO finalproject.user(" +
            "username," +
            "password," +
            "address," +
            "city," +
            "country," +
            "zip_code," +
            "email_address," +
            "creation_time," +
            "is_admin," +
            "salt)" +
            " VALUES(?,?,?,?,?,?,?,?,?,?)";

    private static final String CREATE_USER_SESSION_SQL = "INSERT INTO finalproject.user_session(" +
            "userid," +
            "session_id," +
            "session_exp)" +
            "VALUES(?,?,?)";

    private static final String DELETE_USER_SESSION_SQL = "DELETE FROM finalproject.user_session WHERE userid =?";

    private static final String CREATE_USER_HISTORY_SQL = "INSERT INTO finalproject.user_history(" +
            "userid," +
            "hotelid)" +
            "VALUES(?,?)";

    private static final String DELETE_USER_HISTORY_SQL = "DELETE FROM finalproject.user_history WHERE userid =?";

    private static final String DELETE_USER_SAVED_HOTEL = "DELETE FROM finalproject.user_hotels WHERE userid =?";

    private static final String VALID_USER_SQL = "SELECT * FROM finalproject.user WHERE email_address = ?";

    private static final String GET_USER_INFO_BY_ID_SQL = "SELECT * FROM finalproject.user where userid = ?";

    private static final String GET_USER_SALT = "SELECT salt FROM finalproject.user where email_address = ?";

    private static final String GET_USER_HISTORY = "SELECT * FROM finalproject.user_history " +
            "uh INNER JOIN finalproject.hotel h ON uh.hotelid = h.hotelid \n" +
            "WHERE uh.userid = ?";

    private static final String CREATE_USER_SAVED_HOTEL = "INSERT INTO finalproject.user_hotels(" +
            "userid," +
            "hotelid)" +
            "VALUES(?,?)";

    private static final String GET_USER_SAVED_HOTEL = "SELECT * FROM finalproject.user_hotels" +
            " uh INNER JOIN finalproject.hotel h ON uh.hotelid = h.hotelid" +
            " WHERE uh.userid = ?";

    private static final String GET_USER_SESSION_SQL = "SELECT * FROM finalproject.user_session WHERE userid = ? " +
            " AND session_id = ?";

    private static final String SET_USER_SESSION_EXP_TIME_SQL = "UPDATE finalproject.user_session SET session_exp = ?" +
            " WHERE userid = ? AND session_id = ?";


    private static final String UPDATE_USER_LAST_LOGIN_SQL = "UPDATE finalproject.user SET last_login = ?" +
            " WHERE userid = ?";
}
