package com.epam.hostel.command.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by ASUS on 18.11.2016.
 */
public class QueryUtil {
    private static final String SESSION_PREV_QUERY = "prevQuery";

    private static final char QUERY_SEPARATOR = '?';

    public static void saveCurrentQueryToSession(HttpServletRequest request){
        HttpSession session = request.getSession(true);

        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();

        if(queryString == null){
            session.setAttribute(SESSION_PREV_QUERY, requestURI);
        }
        else {
            session.setAttribute(SESSION_PREV_QUERY, requestURI + QUERY_SEPARATOR + queryString);
        }
    }
}
