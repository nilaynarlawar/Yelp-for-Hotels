package jettyTravelHelperServer;

import Processors.LoadHotelData;
import Processors.UserProcessor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import user.UserHistory;
import user.UserInfo;
import utils.Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * User related operation servlet
 */
public class UserServlet extends HotelAppServletBase {

    Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public UserServlet(Connection connection) {
        super(connection);
    }

    /**
     * All Get request.
     *
     * @param request
     * @param response
     */
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) {
        UserProcessor userProcessor = new UserProcessor(dbcntx);
        userGETRequestHandler(request, response, userProcessor);
    }

    /**
     * User Get Request Handler.
     *
     * @param request
     * @param response
     * @param userProcessor
     */
    private void userGETRequestHandler(HttpServletRequest request, HttpServletResponse response,
                                       UserProcessor userProcessor) {
        try {
            PrintWriter out = response.getWriter();
            if (request.getRequestURI().contains("user/getUser") && request.getParameter("userId") != null) {
                UserInfo user = userProcessor.getUser(Integer.parseInt(request.getParameter("userId")));
                if (user != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(gson.toJson(user));
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(false);
                }
            } else if (request.getRequestURI().contains("user/getHistory") && request.getParameter("userId") != null) {

                List<UserHistory> history = userProcessor.getUserHistory(
                        Integer.valueOf(request.getParameter("userId")));
                if (history != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(gson.toJson(history));
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(false);
                }
            } else if (request.getRequestURI().contains("user/getSavedHotel") && request.getParameter("userId") != null) {

                List<UserHistory> history = userProcessor.getSavedHotel(
                        Integer.valueOf(request.getParameter("userId")));
                if (history != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(gson.toJson(history));
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

    /**
     * User Post request.
     *
     * @param request
     * @param response
     */
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response) {
        UserProcessor userProcessor = new UserProcessor(dbcntx);
        userPOSTRequestHandler(request, userProcessor, response);
    }

    private void userPOSTRequestHandler(HttpServletRequest request, UserProcessor userProcessor,
                                        HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            boolean result;
            if (request.getRequestURI().contains("user/create")) {
                UserInfo userInfo = getUserObj(request);
                result = userProcessor.createUser(userInfo);
                if (result) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(true);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                    out.println(false);
                }
            } else if (request.getRequestURI().contains("user/validateUser")) {
                UserInfo userInfo = userProcessor.isValidUser(request.getParameter("userName"),
                        request.getParameter("password"));
                if (userInfo.getUserId() > 0) {
                    userProcessor.deleteUserSession(userInfo.getUserId());
                    String Session = userProcessor.createUserSession(userInfo.getUserId());
                    userProcessor.updateLoginTime(userInfo.getUserId(), Utils.getCurrentSQLTimestamp());
                    ZonedDateTime zdt = Utils.toZDT(userInfo.getLastLogin());
                    Cookie cookie = setCookie(Session, HotelAppServletBase.SESSION_ID);
                    Cookie cookieUserID = setCookie(String.valueOf(userInfo.getUserId()), HotelAppServletBase.USER_ID);
                    response.addCookie(cookie);
                    response.addCookie(cookieUserID);
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(gson.toJson(userInfo));
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(gson.toJson(userInfo));
                }
            } else if (request.getRequestURI().contains("user/deleteSession")
                    && request.getParameter("userId") != null) {
                result = userProcessor.deleteUserSession(Integer.parseInt(request.getParameter("userId")));
                HotelAppServletBase.resetCookie(response);
                if (result) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(true);
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(false);
                }
            } else if (request.getRequestURI().contains("user/updateUserHistory") &&
                    request.getParameter("userId") != null &&
                    request.getParameter("hotelId") != null) {
                result = userProcessor.updateUserHistory(Integer.parseInt(request.getParameter("userId")),
                        Integer.parseInt(request.getParameter("hotelId")));
                if (result) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(true);
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(false);
                }
            } else if (request.getRequestURI().contains("user/updateSaveHotel") &&
                    request.getParameter("userId") != null &&
                    request.getParameter("hotelId") != null) {
                result = userProcessor.updateSaveHotel(Integer.parseInt(request.getParameter("userId")),
                        Integer.parseInt(request.getParameter("hotelId")));
                if (result) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(true);
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(false);
                }
            } else if (request.getRequestURI().contains("user/deleteSaveHotel") &&
                    request.getParameter("userId") != null) {
                result = userProcessor.deleteSaveHotel(Integer.parseInt(request.getParameter("userId")));
                if (result) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(true);
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(false);
                }
            } else if (request.getRequestURI().contains("user/deleteUserHistory") &&
                    request.getParameter("userId") != null) {
                result = userProcessor.deleteUserHistory(Integer.parseInt(request.getParameter("userId")));
                if (result) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(true);
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

    private Cookie setCookie(String session, String name) {
        Cookie cookie = new Cookie(name, session);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(15 * 60);
        return cookie;
    }


    private UserInfo getUserObj(HttpServletRequest request) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(request.getParameter("userName"));
        userInfo.setPassword(request.getParameter("password"));
        userInfo.setAddress(request.getParameter("address"));
        userInfo.setCity(request.getParameter("city"));
        userInfo.setCountry(request.getParameter("country"));
        userInfo.setZipCode(request.getParameter("zipCode"));
        userInfo.setEmailId(request.getParameter("emailId"));
        userInfo.setCreationTime(request.getParameter("creationTime"));
        userInfo.setAdmin(Boolean.parseBoolean(request.getParameter("isAdmin")));
        userInfo.setSalt(request.getParameter("salt"));
        return userInfo;
    }
}
