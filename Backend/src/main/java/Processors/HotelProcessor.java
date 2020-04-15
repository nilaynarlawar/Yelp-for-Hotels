package Processors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import hotelapp.hotel.HotelGeoCoordinates;
import hotelapp.hotel.HotelInfo;
import hotelapp.hotel.attractions.TouristAttractions;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DB connection related hotel operations.
 */
public class HotelProcessor {
    private Connection dbcntx;
    private static final String host = "maps.googleapis.com";
    private static final String path = "/maps/api/place/textsearch/json";
    private static final String CONFIG_FILE_PATH = "C:\\input\\config.json";
    private Map<Integer, Double> ratingMap;
    URL url;
    String response;
    Gson gson = new Gson();
    HotelInfo hotelInfo;

    public HotelProcessor(Connection connection) {
        this.dbcntx = connection;
        fetchOverallRating();
    }

    public List<HotelInfo> getHotelInfo(String city, String hotelName) {
        List<HotelInfo> hotels = new ArrayList<>();
        HotelInfo hotel;
        HotelGeoCoordinates hotelGeoCoordinates;
        try {
            PreparedStatement sql;
            String query;
            if (hotelName == null || hotelName.isEmpty()) {
                query = MessageFormat.format(GET_HOTEL_LIST_SQL, "1=1");
                sql = dbcntx.prepareStatement(query);
                sql.setString(1, city);
            } else {
                query = MessageFormat.format(GET_HOTEL_LIST_SQL, "hotel_name like ?");
                sql = dbcntx.prepareStatement(query);
                sql.setString(1, city);
                sql.setString(2, "%" + hotelName + "%");
            }
            ResultSet result = sql.executeQuery();
            while (result.next()) {
                hotel = new HotelInfo();
                hotelGeoCoordinates = new HotelGeoCoordinates();
                hotel.setHotelId(result.getInt("hotelid"));
                hotel.setHotelName(result.getString("hotel_name"));
                hotelGeoCoordinates.setLatitude(result.getDouble("lat"));
                hotelGeoCoordinates.setLongitude(result.getDouble("lng"));
                hotel.setHotelGeoCoordinates(hotelGeoCoordinates);
                hotel.setCity(result.getString("city"));
                hotel.setAddress(result.getString("address"));
                hotel.setCountry(result.getString("country"));
                hotel.setState(result.getString("state"));
                hotel.setExpediaLink(result.getString("expedia_link"));
                hotel.setOverall_rating(ratingMap.containsKey(result.getInt("hotelid")) ?
                        ratingMap.get(result.getInt("hotelid")) : 0);
                hotel.setImagePath(result.getString("image_path"));
                hotels.add(hotel);
            }
            return hotels;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return hotels;
        }
    }

    public HotelInfo getHotelInfoById(int hotelId) {
        HotelInfo hotel;
        HotelGeoCoordinates hotelGeoCoordinates;
        try {
            PreparedStatement sql;
            sql = dbcntx.prepareStatement(GET_HOTEL_INFO_BY_ID_SQL);
            sql.setInt(1, hotelId);
            ResultSet result = sql.executeQuery();
            hotel = new HotelInfo();
            hotelGeoCoordinates = new HotelGeoCoordinates();
            while (result.next()) {
                hotel.setHotelId(result.getInt("hotelid"));
                hotel.setHotelName(result.getString("hotel_name"));
                hotelGeoCoordinates.setLatitude(result.getDouble("lat"));
                hotelGeoCoordinates.setLongitude(result.getDouble("lng"));
                hotel.setHotelGeoCoordinates(hotelGeoCoordinates);
                hotel.setCity(result.getString("city"));
                hotel.setAddress(result.getString("address"));
                hotel.setCountry(result.getString("country"));
                hotel.setState(result.getString("state"));
                hotel.setExpediaLink(result.getString("expedia_link"));
                hotel.setOverall_rating(ratingMap.containsKey(result.getInt("hotelid")) ?
                        ratingMap.get(result.getInt("hotelid")) : 0);
                hotel.setImagePath(result.getString("image_path"));
            }
            return hotel;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return new HotelInfo();
        }
    }

    public List<String> getCities() {
        List<String> cities = new ArrayList<>();
        try {
            PreparedStatement sql;
            sql = dbcntx.prepareStatement(GET_CITY_LIST_SQL);
            ResultSet result = sql.executeQuery();
            while (result.next()) {
                cities.add(result.getString("city"));
            }
            return cities;
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
            return cities;
        }
    }

    public TouristAttractions fetchAttractions(int radiusInMiles, int hotelId) {
        try {
            double radiusInMeter = getRadiusInMeter(radiusInMiles);
            hotelInfo = getHotelInfoById(hotelId);
            url = new URL(getUrl(path, host, hotelInfo.getCity(), hotelInfo.getHotelGeoCoordinates(), radiusInMeter));
            response = getServerResponse(url); // Make socket connection and get response from google api.
            response = getHeaderLessResponse(response); // remove the header part from the response JSON
            TouristAttractions touristAttractions = gson.fromJson(response, TouristAttractions.class);
            return touristAttractions;

        } catch (IllegalArgumentException | MalformedURLException e) {
            System.out.println("Exception occurred in thread termination by giving message : " + e.getMessage());
            return new TouristAttractions(new ArrayList<>());
        }
    }

    private static final String GET_HOTEL_LIST_SQL = "SELECT * FROM finalproject.hotel WHERE city = ?" +
            " AND {0}";

    private static final String GET_HOTEL_INFO_BY_ID_SQL = "SELECT * FROM finalproject.hotel WHERE hotelid = ?";

    private static final String GET_CITY_LIST_SQL = "SELECT DISTINCT city FROM finalproject.hotel";

    private static final String GET_HOTEL_OVERALL_RATING = "SELECT avg(rating), hotelid from finalproject.hotel_review " +
            " group by hotelid ";

    private static String getRequest(String host, String pathResourceQuery) {
        String request = "GET " + pathResourceQuery + " HTTP/1.1" + System.lineSeparator()
                + "Host: " + host + System.lineSeparator()
                + "Connection: close" + System.lineSeparator()
                + System.lineSeparator();
        return request;
    }

    private static String getUrl(String path, String host, String city, HotelGeoCoordinates hotelGeoCoordinates,
                                 double radiusInMiles) {
        String apiKey = getApiKey();
        String url = "https://" + host + path + "?query=tourist%20attractions+in+" +
                city.replace(" ", "%20") + "&location=" +
                hotelGeoCoordinates.getLatitude() + "," + hotelGeoCoordinates.getLongitude() +
                "&radius=" + radiusInMiles + "&key=" + apiKey + "&format=json";
        return url;
    }

    private static String getServerResponse(URL url) {
        String response = "";
        PrintWriter outputDataStream = null;
        BufferedReader inputDataStream = null;
        SSLSocket socket = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            socket = (SSLSocket) factory.createSocket(url.getHost(), 443);
            outputDataStream = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            String request = getRequest(url.getHost(), url.getPath() + "?" + url.getQuery());
            outputDataStream.println(request);
            outputDataStream.flush();
            inputDataStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line = inputDataStream.readLine()) != null) {
                stringBuffer.append(line);
            }
            response = stringBuffer.toString();
        } catch (IOException e) {
            System.out.println(
                    "An IOException occured while writing to the socket stream or reading from the stream: " + e);
        } finally {
            try {
                outputDataStream.close();
                inputDataStream.close();
                socket.close();
            } catch (IOException e) {
                System.out.println("An exception occured while trying to close the streams or the socket: " + e);
            }
            return response;
        }
    }

    private static String getHeaderLessResponse(String response) {
        String patternString = "(.*?)\\{";
        String group1 = "";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(response);
        if (matcher.find())
            group1 = matcher.group(1);
        return response.replace(group1, "");
    }

    private static String getApiKey() {
        String apiKey = "";
        try (JsonReader jsonReader = new JsonReader(new FileReader(CONFIG_FILE_PATH))) {
            jsonReader.beginObject();

            while (jsonReader.hasNext()) {

                String name = jsonReader.nextName();
                if (name.equals("apikey")) {
                    apiKey = jsonReader.nextString();
                    return apiKey;
                }
            }
            jsonReader.endObject();
        } catch (IOException e) {
            System.out.println("Could not read from file");
        }
        return apiKey;
    }

    private double getRadiusInMeter(int radiusInMiles) {
        if (radiusInMiles < 0) {
            throw new IllegalArgumentException("In valid Miles value");
        }
        return (radiusInMiles * 1609.3444);
    }

    private void fetchOverallRating() {
        ratingMap = new HashMap<>();
        try {
            PreparedStatement sql;
            sql = dbcntx.prepareStatement(GET_HOTEL_OVERALL_RATING);
            ResultSet result = sql.executeQuery();
            while (result.next()) {
                if (ratingMap.containsKey(result.getInt("hotelid"))) {
                    ratingMap.replace(result.getInt("hotelid"), result.getDouble("avg(rating)"));
                } else {
                    ratingMap.put(result.getInt("hotelid"), result.getDouble("avg(rating)"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
        }
    }


}
