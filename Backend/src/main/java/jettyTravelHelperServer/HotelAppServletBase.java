package jettyTravelHelperServer;

import Processors.UserProcessor;
import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * HotelAppBase servlet which will handle session management.
 */
public class HotelAppServletBase extends HttpServlet {
    public static String SESSION_ID = "sessionId";
    public static final String USER_ID = "userId";

    Connection dbcntx;

    public HotelAppServletBase(Connection connection) {
        this.dbcntx = connection;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        String sessionId = null;
        String userId = null;
        boolean isValid = false;
        UserProcessor userProcessor = new UserProcessor(dbcntx);
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(SESSION_ID)) {
                    sessionId = cookie.getValue();
                } else if (cookie.getName().equals(USER_ID)) {
                    userId = cookie.getValue();
                }
            }
        }
        if (sessionId != null && userId != null) {
            isValid = userProcessor.isUserSessionValid(Integer.valueOf(userId), sessionId);
        }
        if (!isValid && !isLoginRequest(req)) {
            resetCookie(resp);
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            if (!isLoginRequest(req)) {
                userProcessor.updateSessionExp(Integer.valueOf(userId), sessionId);
            }
            super.service(req, resp);
        }
    }

    /**
     * Reset Cookie
     *
     * @param response
     */
    public static void resetCookie(HttpServletResponse response) {
        Cookie cookie = setCookie(null, HotelAppServletBase.SESSION_ID);
        Cookie cookieUserID = setCookie(null, HotelAppServletBase.USER_ID);
        response.addCookie(cookie);
        response.addCookie(cookieUserID);
    }

    private static Cookie setCookie(String session, String name) {
        Cookie cookie = new Cookie(name, session);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        return cookie;
    }

    private boolean isLoginRequest(HttpServletRequest req) {
        if (req.getRequestURI().contains("user/validateUser") || req.getRequestURI().contains("user/create") ||
                req.getRequestURI().contains("loadData")) {
            return true;
        } else {
            return false;
        }
    }
}
