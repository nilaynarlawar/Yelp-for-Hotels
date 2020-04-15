package jettyTravelHelperServer;


import Processors.LoadHotelData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;


/**
 * This class uses Jetty & Httpservlets to implement Load Hotel data.
 */
public class LoadDataServlet extends HotelAppServletBase {

    public LoadDataServlet(Connection connection) {
        super(connection);
    }

    /**
     * A method that gets executed when the get request is sent to the LoadDataServlet
     */
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) {
        String url = request.getRequestURI();
        if (url.contains("loadData")) {
            LoadHotelData loadHotelData = new LoadHotelData(dbcntx);
            loadHotelData.loadData();
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
