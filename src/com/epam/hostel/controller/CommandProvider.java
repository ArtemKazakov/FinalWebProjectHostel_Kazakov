package com.epam.hostel.controller;

import com.epam.hostel.command.Command;
import com.epam.hostel.command.impl.general.ErrorCommand;
import com.epam.hostel.command.impl.general.LogInCommand;
import com.epam.hostel.command.impl.general.LogOutCommand;
import com.epam.hostel.command.impl.general.RegistrationCommand;
import com.epam.hostel.command.impl.room.ViewRoomsListCommand;
import com.epam.hostel.command.impl.user.EditUserCommand;
import com.epam.hostel.command.impl.user.ViewUserCommand;
import com.epam.hostel.controller.exception.CommandNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

	private static final String LOG_IN_CMD = "log_in";
	private static final String LOG_OUT_CMD = "log_out";
	private static final String REGISTRATION_CMD ="registration";
	private static final String VIEW_ROOMS_LIST_CMD ="viewRoomsList";
	private static final String ERROR_CMD = "error";
	private static final String VIEW_USER_CMD = "viewUser";
	private static final String EDIT_USER_CMD = "editUser";

	private static final CommandProvider INSTANCE = new CommandProvider();

	private Map<String, Command> commands = new HashMap<String, Command>();
	
	private CommandProvider() {
		commands.put(LOG_IN_CMD, new LogInCommand());
		commands.put(LOG_OUT_CMD, new LogOutCommand());
		commands.put(REGISTRATION_CMD, new RegistrationCommand());
		commands.put(VIEW_ROOMS_LIST_CMD, new ViewRoomsListCommand());
		commands.put(ERROR_CMD, new ErrorCommand());
		commands.put(VIEW_USER_CMD, new ViewUserCommand());
		commands.put(EDIT_USER_CMD, new EditUserCommand());
	}

	
	public Command getCommand(String commandName) throws CommandNotFoundException{
		Command command = commands.get(commandName);
		if(command != null) {
			return command;
		} else{
			throw new CommandNotFoundException("Wrong command name");
		}
	}

	public static CommandProvider getInstance() {
		return INSTANCE;
	}

}
