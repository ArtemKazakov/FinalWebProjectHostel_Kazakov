package com.epam.hostel.command.impl.general;

import com.epam.hostel.command.Command;
import com.epam.hostel.command.util.LanguageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ASUS on 02.11.2016.
 */
public class ErrorCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/error.jsp";

    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
