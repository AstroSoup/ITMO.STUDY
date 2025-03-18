package command;

import utility.CommandHandler;

import java.util.HashSet;

/**
 * Класс команды для вывода справки по командам.
 *
 * @author AstroSoup
 */
public class Help extends UsableCommand {

    final private CommandHandler invoker;
    public static HashSet<UsableCommand> commands;

    /**
     * Конструктор
     *
     * @param invoker Объект CommandHandler в который будет записан результат выполнения команды
     */
    public Help(CommandHandler invoker) {
        super("Help", "Команда для вывода справки по командам");
        this.invoker = invoker;
    }

    public Help() {
        this(null);
    }

    public void execute() {

        StringBuilder output = new StringBuilder();
        for (UsableCommand command : commands) {
            output.append(command.getName()).append(" : ").append(command.getDescription()).append("\n");
        }

        invoker.setFeedback(output.toString());
    }
}
