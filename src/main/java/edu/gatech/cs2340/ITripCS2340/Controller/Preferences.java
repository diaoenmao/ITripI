package edu.gatech.cs2340.ITripCS2340.Controller;

import edu.gatech.cs2340.ITripCS2340.Model.Hash;
import edu.gatech.cs2340.ITripCS2340.Model.Password;
import edu.gatech.cs2340.ITripCS2340.Model.Username;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.simple.JSONArray;

/**
 * Handles Login.jsp
 * Created by Jonathan on 6/11/2014.
 * @author Jonathan
 * @version 1.0
 */

@WebServlet("/Preferences/")
public class Preferences extends SharedServletMethods {
    private Hash table;

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {doGet(request, response);
    }
    /**
     * Handles Login.jsp
     * @param request  HTTP request
     * @param response HTTP response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        try {
            table = new Hash(getServletContext().getRealPath("/"));
        } catch (IOException e) {
            System.out.println("Path Error");
        }

        Username user = new Username(
                getStringParameterSafely(request,
                        JSPStringConstants.USERNAME_PARAM));

        String location = getStringParameterSafely(request,
                JSPStringConstants.CENTRAL_LOCATION);

        String interestPlace = getStringParameterSafely(request,
                JSPStringConstants.INTEREST_PLACE);

        String distance = getStringParameterSafely(request,
                JSPStringConstants.DISTANCE);

        YelpAPI yelp = new YelpAPI(JSPStringConstants.YELP_API_KEY, JSPStringConstants.YELP_API_SECRET,
                JSPStringConstants.YELP_TOKEN, JSPStringConstants.YELP_TOKEN_SECRET);

        JSONArray businesses = yelp.queryAPI(interestPlace,location,distance);
        request.setAttribute(JSPStringConstants.BUSINESSES, businesses.toString());

        goToFileWithUser(request, response, user,
                JSPStringConstants.MAP_JSP);
    }
}