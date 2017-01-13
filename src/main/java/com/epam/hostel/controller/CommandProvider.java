package com.epam.hostel.controller;

import com.epam.hostel.command.Command;
import com.epam.hostel.command.impl.discount.AddDiscountCommand;
import com.epam.hostel.command.impl.discount.DeleteDiscountCommand;
import com.epam.hostel.command.impl.discount.EditDiscountCommand;
import com.epam.hostel.command.impl.general.*;
import com.epam.hostel.command.impl.rentalrequest.*;
import com.epam.hostel.command.impl.room.*;
import com.epam.hostel.command.impl.schedulerecord.ViewScheduleRecordsListCommand;
import com.epam.hostel.command.impl.user.*;
import com.epam.hostel.controller.exception.CommandNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

	private static final String DEFAULT_COMMAND_NAME = "mainPage";


	private static final String VIEW_MAIN_PAGE_CMD = "mainPage";
	private static final String LOG_IN_CMD = "log_in";
	private static final String LOG_OUT_CMD = "log_out";
	private static final String REGISTRATION_CMD ="registration";
	private static final String VIEW_ROOMS_LIST_CMD ="viewRoomsList";
	private static final String ERROR_CMD = "error";
	private static final String USER_ACCOUNT_CMD = "userAccount";
	private static final String EDIT_USER_CMD = "editUser";
	private static final String VIEW_USERS_LIST_CMD = "viewUsersList";
	private static final String VIEW_USER_CMD = "viewUser";
	private static final String CHANGE_LNG_CMD = "changeLanguage";
	private static final String ADD_DISCOUNT_CMD = "addDiscount";
	private static final String FIND_SUITABLE_ROOMS_CMD = "findSuitableRooms";
	private static final String MAKE_RENTAL_REQUEST_CMD = "makeRentalRequest";
	private static final String BAN_USER_CMD = "banUser";
	private static final String DELETE_USER_CMD = "deleteUser";
	private static final String DELETE_DISCOUNT_CMD = "deleteDiscount";
	private static final String EDIT_DISCOUNT_CMD = "editDiscount";
	private static final String ADD_ROOM_CMD = "addRoom";
	private static final String EDIT_ROOM_CMD = "editRoom";
	private static final String DELETE_ROOM_CMD = "deleteRoom";
	private static final String VIEW_RENTAL_REQUESTS_LIST_CMD = "viewRequestsList";
	private static final String VIEW_SCHEDULE_RECORDS_LIST_CMD = "viewScheduleRecords";
	private static final String VIEW_RENTAL_REQUEST_CMD = "viewRequest";
	private static final String EDIT_RENTAL_REQUEST_CMD = "editRequest";
	private static final String DELETE_RENTAL_REQUEST_CMD = "deleteRequest";

	private static final CommandProvider INSTANCE = new CommandProvider();

	private Map<String, Command> commands = new HashMap<String, Command>();
	
	private CommandProvider() {
		commands.put(VIEW_MAIN_PAGE_CMD, new ViewMainPageCommand());
		commands.put(LOG_IN_CMD, new LogInCommand());
		commands.put(LOG_OUT_CMD, new LogOutCommand());
		commands.put(REGISTRATION_CMD, new RegistrationCommand());
		commands.put(VIEW_ROOMS_LIST_CMD, new ViewRoomsListCommand());
		commands.put(ERROR_CMD, new ErrorCommand());
		commands.put(USER_ACCOUNT_CMD, new UserAccountCommand());
		commands.put(EDIT_USER_CMD, new EditUserCommand());
		commands.put(VIEW_USERS_LIST_CMD, new ViewUsersListCommand());
		commands.put(VIEW_USER_CMD, new ViewUserCommand());
		commands.put(CHANGE_LNG_CMD, new ChangeLanguageCommand());
		commands.put(ADD_DISCOUNT_CMD, new AddDiscountCommand());
		commands.put(FIND_SUITABLE_ROOMS_CMD, new FindSuitableRoomsCommand());
		commands.put(MAKE_RENTAL_REQUEST_CMD, new MakeRentalRequestCommand());
		commands.put(BAN_USER_CMD, new BanUserCommand());
		commands.put(DELETE_USER_CMD, new DeleteUserCommand());
		commands.put(DELETE_DISCOUNT_CMD, new DeleteDiscountCommand());
		commands.put(EDIT_DISCOUNT_CMD, new EditDiscountCommand());
		commands.put(ADD_ROOM_CMD, new AddRoomCommand());
		commands.put(EDIT_ROOM_CMD, new EditRoomCommand());
		commands.put(DELETE_ROOM_CMD, new DeleteRoomCommand());
		commands.put(VIEW_RENTAL_REQUESTS_LIST_CMD, new ViewRentalRequestsListCommand());
		commands.put(VIEW_SCHEDULE_RECORDS_LIST_CMD, new ViewScheduleRecordsListCommand());
		commands.put(VIEW_RENTAL_REQUEST_CMD, new ViewRentalRequestCommand());
		commands.put(EDIT_RENTAL_REQUEST_CMD, new EditRentalRequestCommand());
		commands.put(DELETE_RENTAL_REQUEST_CMD, new DeleteRentalRequestCommand());
	}

	
	public Command getCommand(String commandName) throws CommandNotFoundException{
		if(commandName == null){
			return commands.get(DEFAULT_COMMAND_NAME);
		}

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
