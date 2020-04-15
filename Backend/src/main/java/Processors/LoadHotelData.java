package Processors;

import hotelapp.HotelDataBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

import static jettyTravelHelperServer.Constants.*;

/**
 * Load hotel info DB handler
 */
public class LoadHotelData {
    private Connection dbcntx;

    public LoadHotelData(Connection connection) {
        this.dbcntx = connection;

    }

    public boolean loadData() {
        HotelDataBuilder hotelDataBuilder = new HotelDataBuilder(dbcntx);
        hotelDataBuilder.loadHotelInfo(HOTEL_FILE_PATH);
        Path reviewPath = Paths.get(HOTEL_REVIEW_FILE_PATH);
        hotelDataBuilder.loadReviews(reviewPath);
        return true;
    }
}
