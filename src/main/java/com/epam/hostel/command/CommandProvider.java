package com.epam.hostel.command;

import com.epam.hostel.controller.exception.CommandNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains and gives all commands in system.
 */
public class CommandProvider {
    private static final CommandProvider INSTANCE = new CommandProvider();
    private Map<String, Command> commands;

    private CommandProvider(){
        commands = new HashMap<>();
    }

    /**
     * Returns {@link CommandProvider} instance
     * @return CommandManager instance
     */
    public static CommandProvider getInstance(){
        return INSTANCE;
    }

    /**
     * Adds command to the system
     * @param commandName name of command
     * @param command {@link Command} implementation,
     *        which will serve command with name
     *        {@code commandName}
     */
    public void addCommand(String commandName, Command command){
        commands.put(commandName, command);
    }

    /**
     * Returns {@link Command} by given name
     * @param commandName command name
     * @return command by given {@code command} name or {@code null}
     */
    public Command getCommand(String commandName) throws CommandNotFoundException {
        Command command = commands.get(commandName);
        if(command != null) {
            return command;
        } else{
            throw new CommandNotFoundException("Wrong command name");
        }
    }
}
