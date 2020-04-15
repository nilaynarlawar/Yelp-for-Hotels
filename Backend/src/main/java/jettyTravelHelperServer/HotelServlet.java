package jettyTravelHelperServer;

import Processors.HotelProcessor;
import Processors.LoadHotelData;
import Processors.ReviewProcessor;
import Processors.UserProcessor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hotelapp.hotel.HotelInfo;
import hotelapp.hotel.attractions.TouristAttractions;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Hotel releated request handler servlet.
 */
public class HotelServlet extends HotelAppServletBase {

    Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public HotelServlet(Connection connection) {
        super(connection);
    }


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) {
        HotelProcessor hotelProcessor = new HotelProcessor(dbcntx);
        hotelGETRequestHandler(request, response, hotelProcessor);

    }

    private void hotelGETRequestHandler(HttpServletRequest request, HttpServletResponse response,
                                        HotelProcessor hotelProcessor) {
        try {
            PrintWriter out = response.getWriter();
            String hotelName;
            hotelName = request.getParameter("hotelName");
            if (request.getRequestURI().contains("hotel/getHotelInfo") &&
                    request.getParameter("city") != null) {
                List<HotelInfo> hotels = hotelProcessor.getHotelInfo(request.getParameter("city"), hotelName);
                if (hotels != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(gson.toJson(hotels));
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(false);
                }
            } else if (request.getRequestURI().contains("hotel/getHotelInfoById") &&
                    request.getParameter("hotelid") != null) {
                HotelInfo hotel = hotelProcessor.getHotelInfoById(
                        Integer.parseInt(request.getParameter("hotelid")));
                if (hotel != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(gson.toJson(hotel));
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(false);
                }
            } else if (request.getRequestURI().contains("hotel/getHotelCity")) {
                List<String> cities;
                cities = hotelProcessor.getCities();
                if (cities != null) {
                    out.println(gson.toJson(cities));
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(false);
                }
            } else if (request.getRequestURI().contains("hotel/getAttractions") &&
                    request.getParameter("hotelid") != null &&
                    request.getParameter("radius") != null) {
                TouristAttractions touristAttractions = hotelProcessor.fetchAttractions(
                        Integer.parseInt(request.getParameter("radius")),
                        Integer.parseInt(request.getParameter("hotelid")));
                if (touristAttractions != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(gson.toJson(touristAttractions));
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(false);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            }
        } catch (IOException e) {
            System.out.println("Error Occured while getting response writer as, " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }

    }


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response) {

    }
}
