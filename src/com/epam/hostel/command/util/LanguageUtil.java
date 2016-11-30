package com.epam.hostel.command.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LanguageUtil {
    private static final String SESSION_LANGUAGE_ID = "languageId";

    private static final String DEFAULT_LANGUAGE_ID = "EN";

    public static String getLanguageId(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        String sessionLanguageId = (session == null) ? DEFAULT_LANGUAGE_ID : (String) session.getAttribute(SESSION_LANGUAGE_ID);
        String languageId = (sessionLanguageId == null) ? DEFAULT_LANGUAGE_ID : sessionLanguageId;
        return languageId;
    }
}
