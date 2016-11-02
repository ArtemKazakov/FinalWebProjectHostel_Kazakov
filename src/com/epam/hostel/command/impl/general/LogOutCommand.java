package com.epam.hostel.command.impl.general;

import com.epam.hostel.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ASUS on 02.11.2016.
 */
public class LogOutCommand implements Command {

    private static final String JSP_PAGE_PATH = "index.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        response.sendRedirect(JSP_PAGE_PATH);
    }
}
