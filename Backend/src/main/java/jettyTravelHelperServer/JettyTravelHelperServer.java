package jettyTravelHelperServer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class uses Jetty & servlets to implement server serving hotel and review info
 */
public class JettyTravelHelperServer {

    public static void main(String[] args) {
        JettyTravelHelperServer jettyTravelHelperServer = new JettyTravelHelperServer();
        jettyTravelHelperServer.HotelServer();
    }

    /**
     * This method handle the http request on specific port and forward it to specific servlet class.
     */
    public void HotelServer() {
        Connection dbConnection = null;
        try {

            String uri = "jdbc:mysql://localhost:3306/finalproject?serverTimezone=UTC";
            dbConnection = DriverManager.getConnection(uri, "root",
                    "root");

            Server server = new Server(8090);
            ServletHandler handler = new ServletHandler();
            handler.addServletWithMapping(new ServletHolder
                    (new LoadDataServlet(dbConnection)), "/hotelapp/loadData");
            handler.addServletWithMapping(new ServletHolder
                    (new UserServlet(dbConnection)), "/hotelapp/user/*");
            handler.addServletWithMapping(new ServletHolder
                    (new HotelServlet(dbConnection)), "/hotelapp/hotel/*");
            handler.addServletWithMapping(new ServletHolder
                    (new ReviewServlet(dbConnection)), "/hotelapp/review/*");
            server.setHandler(handler);
            server.start();
            server.join();
        } catch (Exception ex) {
            System.out.println("Received Exception in Hotel Server by giving error message as" + ex.getMessage());
        } finally {
            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Load properties file
     *
     * @param configPath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    private Properties loadConfig(String configPath) throws FileNotFoundException, IOException {
        Properties config = new Properties();
        config.load(new FileReader(configPath));

        return config;
    }
}
