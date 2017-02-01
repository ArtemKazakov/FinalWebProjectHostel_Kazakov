package com.epam.hostel.controller;

import com.epam.hostel.command.Command;
import com.epam.hostel.command.CommandProvider;
import com.epam.hostel.controller.exception.CommandNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Front-controller of the whole web-application.
 */
public final class Controller extends HttpServlet {

    private final CommandProvider provider = CommandProvider.getInstance();
    private static final String COMMAND = "command";

    private static final int PAGE_NOT_FOUND_ERROR = 404;

    /**
     * Services a GET-requests.
     *
     * @param request a request object
     * @param response a response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.process(request, response);
    }

    /**
     * Services a POST-requests.
     *
     * @param request a request object
     * @param response a response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        this.process(request, response);
    }

    /**
     * Services requests.
     * @param request a request object
     * @param response a response object
     * @throws ServletException
     * @throws IOException
     */
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            String commandName = request.getParameter(COMMAND);
            Command command = provider.getCommand(commandName);
            command.execute(request, response);
        } catch (CommandNotFoundException e){
            response.sendError(PAGE_NOT_FOUND_ERROR);
        }
    }
}
