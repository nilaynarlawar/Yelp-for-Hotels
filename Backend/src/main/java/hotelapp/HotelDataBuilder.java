package hotelapp;

import com.google.gson.Gson;
import hotelapp.hotel.HotelData;
import hotelapp.hotel.HotelInfo;
import hotelapp.hotel.Hotels;
import hotelapp.hotel.review.HotelReview;
import hotelapp.hotel.review.Reviews;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * Class HotelDataBuilder. Loads hotel info from input files to ThreadSafeHotelData (using multithreading).
 */
public class HotelDataBuilder {
    Connection dbcntx;
    HotelData hdata;

    /**
     * Constructor for class HotelDataBuilder.
     */
    public HotelDataBuilder(Connection connection) {
        this.dbcntx = connection;
        hdata = new HotelData(dbcntx);
    }

    /**
     * Read the json file with information about the hotels and load it into the
     * appropriate data structure(s).
     *
     * @param jsonFilename hotel information file name.
     */
    public boolean loadHotelInfo(String jsonFilename) {
        Gson gson = new Gson();
        boolean result = false;
        try (FileReader fileReader = new FileReader(jsonFilename)) {
            Hotels hotels = gson.fromJson(fileReader, Hotels.class);
            for (HotelInfo hotelInfo : hotels.getHotels()) {
                result = hdata.addHotel(String.valueOf(hotelInfo.getHotelId()),
                        hotelInfo.getHotelName(),
                        hotelInfo.getCity(),
                        hotelInfo.getState(),
                        hotelInfo.getAddress(),
                        hotelInfo.getCountry(),
                        hotelInfo.getHotelGeoCoordinates().getLatitude(),
                        hotelInfo.getHotelGeoCoordinates().getLongitude());
            }
            return result;
        } catch (IOException e) {
            System.out.println("Exception occurred while parsing hotel json file. Exception Details: "
                    + e.getMessage());
            return result;
        }
    }

    /**
     * Loads reviews from json files. Recursively processes subfolders.
     * Each json file with reviews should be processed concurrently (you need to create a new runnable job for each
     * json file that you encounter)
     *
     * @param dir directory of files
     */
    public boolean loadReviews(Path dir) {
        File directory = new File(dir.toString());
        File[] listOfFiles = directory.listFiles();
        FileReader fileReader;
        Gson gson = new Gson();
        boolean isRecommended;
        boolean result;
        try {
            if (listOfFiles == null) {
                return false;
            }
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    fileReader = new FileReader(file);
                    Reviews reviews = gson.fromJson(fileReader, Reviews.class);
                    for (HotelReview review : reviews.getHotelReviews()) {
                        isRecommended = review.getIsRecommended().equals("YES");
                        result = hdata.addReview(String.valueOf(review.getHotelId()),
                                String.valueOf(review.getReviewId()),
                                review.getRatingOverall(),
                                review.getTitle(),
                                review.getReviewText(),
                                isRecommended,
                                review.getReviewPostDate(),
                                review.getUser());
                        if (!result) {
                            return false;
                        }
                    }
                } else if (file.isDirectory()) {
                    loadReviews(Paths.get(file.getPath()));
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
