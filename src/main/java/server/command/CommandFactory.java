package server.command;

import server.command.impl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandFactory {
    private static final Map<String, Command> commands = new HashMap<>();
    private static final CommandFactory instance = new CommandFactory();

    private CommandFactory() {
        commands.put(CommandsNames.ERROR_COMMAND, new ErrorCommand());
        commands.put(CommandsNames.HOME_COMMAND, new HomeCommand());
        commands.put(CommandsNames.PROFILE_COMMAND, new ProfileCommand());
        commands.put(CommandsNames.SIGNUP_COMMAND, new SignupCommand());
        commands.put(CommandsNames.SIGNIN_COMMAND, new SigninCommand());
        commands.put(CommandsNames.CATALOG_COMMAND, new CatalogCommand());
        commands.put(CommandsNames.OPEN_SIGNIN_COMMAND, new OpenSigninCommand());
        commands.put(CommandsNames.SIGNOUT_COMMAND, new SignoutCommand());
        commands.put(CommandsNames.OPEN_SIGNUP_COMMAND, new OpenSignupCommand());
        commands.put(CommandsNames.ORDERS_COMMAND, new ViewOrdersCommand());
        commands.put(CommandsNames.REMOVE_USER_ORDER_COMMAND, new RemoveUserOrderCommand());
        commands.put(CommandsNames.ADD_APARTMENT_COMMAND, new AddApartmentCommand());
        commands.put(CommandsNames.CONFIRM_ADD_APARTMENT_COMMAND, new ConfirmAddApartmentCommand());
        commands.put(CommandsNames.USER_ORDERS_COMMAND, new ShowUserOrdersCommand());
        commands.put(CommandsNames.CONFIRM_USER_ORDER_COMMAND, new ConfirmUserOrderCommand());
        commands.put(CommandsNames.ADD_TO_ORDER_COMMAND,new AddToOrderCommand());
        commands.put(CommandsNames.CONFIRM_ORDER_COMMAND, new ConfirmOrderCommand());
        commands.put(CommandsNames.CHANGE_APARTMENT_STATUS_COMMAND, new ChangeApartmentStatusCommand());
        commands.put(CommandsNames.CONFIRM_CHANGE_APARTMENT_STATUS_COMMAND, new ConfirmChangeApartmentStatusCommand());
    }

    public static CommandFactory getInstance() {
        return CommandFactory.instance;
    }

    public Command getCommand(String name) {
        return Optional.ofNullable(commands.get(name)).orElse(commands.get(CommandsNames.HOME_COMMAND));
    }
}
