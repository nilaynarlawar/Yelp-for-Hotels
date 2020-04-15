package jettyTravelHelperServer;

import Processors.LoadHotelData;
import Processors.ReviewProcessor;
import Processors.UserProcessor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hotelapp.hotel.review.HotelReview;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Review related operation servlet.
 */
public class ReviewServlet extends HotelAppServletBase {

    Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public ReviewServlet(Connection connection) {
        super(connection);
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) {
        ReviewProcessor reviewProcessor = new ReviewProcessor(dbcntx);
        reviewGETRequestHandler(request, response, reviewProcessor);
    }

    private void reviewGETRequestHandler(HttpServletRequest request, HttpServletResponse response,
                                         ReviewProcessor reviewProcessor) {
        try {
            PrintWriter out = response.getWriter();
            if (request.getRequestURI().contains("review/getReview") &&
                    request.getParameter("reviewid") != null) {
                HotelReview hotelReview = reviewProcessor.getReview(request.getParameter("reviewid"));
                if (hotelReview != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(gson.toJson(hotelReview));
                } else {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    out.println("No Data Found");
                }
            } else if (request.getRequestURI().contains("review/getReviewsForUser") &&
                    request.getParameter("userid") != null &&
                    request.getParameter("hotelid") != null) {
                List<HotelReview> reviews = reviewProcessor.getReviewListByUser(
                        Integer.parseInt(request.getParameter("userid")), Integer.parseInt(request.getParameter("hotelid")));
                if (reviews != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(gson.toJson(reviews));
                } else {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    out.println("No Data Found");
                }
            } else if (request.getRequestURI().contains("review/getReviewList") &&
                    request.getParameter("hotelid") != null) {
                List<HotelReview> reviews = reviewProcessor.getReviews(
                        Integer.parseInt(request.getParameter("hotelid")));
                if (reviews != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(gson.toJson(reviews));
                } else {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    out.println("No Data Found");
                }
            } else if (request.getRequestURI().contains("review/getHotelListByUserId") &&
                    request.getParameter("userid") != null) {
                Map<Integer, String> hotelMap = reviewProcessor.getHotelListByUserId(
                        Integer.parseInt(request.getParameter("userid")));
                if (!hotelMap.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(gson.toJson(hotelMap));
                } else {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    out.println("No Data Found");
                }
            } else if (request.getRequestURI().contains("review/getUserLikedReviews") &&
                    request.getParameter("userid") != null) {
                List<Integer> likedList = reviewProcessor.getUserLikedReviews(
                        Integer.parseInt(request.getParameter("userid")));
                if (likedList != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(gson.toJson(likedList));
                } else {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    out.println("No Data Found");
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
        ReviewProcessor reviewProcessor = new ReviewProcessor(dbcntx);
        reviewPOSTRequestHandler(request, response, reviewProcessor);
    }

    private void reviewPOSTRequestHandler(HttpServletRequest request, HttpServletResponse response,
                                          ReviewProcessor reviewProcessor) {
        try {
            PrintWriter out = response.getWriter();
            boolean result;
            if (request.getRequestURI().contains("review/createReview")) {
                HotelReview hotelReview = getReviewObj(request);
                result = reviewProcessor.createReview(hotelReview);
                if (result) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(true);
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(false);
                }
            } else if (request.getRequestURI().contains("review/deleteReview") &&
                    request.getParameter("id") != null) {
                result = reviewProcessor.deleteReview(request.getParameter("id"));
                if (result) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(true);
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(false);
                }
            } else if (request.getRequestURI().contains("review/updateReview")) {
                HotelReview hotelReview = getReviewObj(request);
                result = reviewProcessor.updateReview(hotelReview);
                if (result) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(true);
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(false);
                }
            } else if (request.getRequestURI().contains("review/reviewLike") &&
                    request.getParameter("userId") != null && request.getParameter("reviewId") != null) {
                result = reviewProcessor.reviewLike(request.getParameter("userId"),
                        request.getParameter("reviewId"));
                if (result) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(true);
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(false);
                }
            } else if (request.getRequestURI().contains("review/reviewUnlike") &&
                    request.getParameter("userId") != null && request.getParameter("reviewId") != null) {
                result = reviewProcessor.reviewUnlike(request.getParameter("userId"),
                        request.getParameter("reviewId"));
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

    private HotelReview getReviewObj(HttpServletRequest request) {
        HotelReview hotelReview = new HotelReview();
        hotelReview.setReviewId(request.getParameter("reviewId"));
        hotelReview.setHotelId(request.getParameter("hotelId") == null ? 0 :
                Integer.parseInt(request.getParameter("hotelId")));
        hotelReview.setRatingOverall(Integer.parseInt(request.getParameter("rating")));
        hotelReview.setReviewText(request.getParameter("reviewText"));
        hotelReview.setTitle(request.getParameter("title"));
        hotelReview.setUserid(request.getParameter("userid") == null ? 0 :
                Integer.parseInt(request.getParameter("userid")));
        hotelReview.setReviewPostDate(request.getParameter("reviewPostDate"));
        hotelReview.setIsRecommended(request.getParameter("isRecommended") == null ? "NO" :
                request.getParameter("isRecommended"));
        hotelReview.setUser(request.getParameter("user"));
        hotelReview.setId(request.getParameter("id") == null ? 0 : Integer.parseInt(request.getParameter("id")));
        return hotelReview;
    }

}
