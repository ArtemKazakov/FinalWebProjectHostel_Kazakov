package com.epam.hostel.controller;

import com.epam.hostel.command.Command;
import com.epam.hostel.controller.exception.CommandNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ASUS on 25.10.2016.
 */
public final class Controller extends HttpServlet {

    private final CommandProvider provider = CommandProvider.getInstance();
    private static final String COMMAND = "command";

    private static final int PAGE_NOT_FOUND_ERROR = 404;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND);
        try {
            Command command = provider.getCommand(commandName);
            command.execute(request, response);
        } catch (CommandNotFoundException e){
            response.sendError(PAGE_NOT_FOUND_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String commandName = request.getParameter(COMMAND);
        try {
            Command command = provider.getCommand(commandName);
            command.execute(request, response);
        } catch (CommandNotFoundException e){
            response.sendError(PAGE_NOT_FOUND_ERROR);
        }
    }

}
