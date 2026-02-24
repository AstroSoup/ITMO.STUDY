package shared.command;

import shared.CommandHandler;

/**
 * Абстрактный класс для классов команд.
 *
 * @author AstroSoup
 */
public abstract class Command {

    CommandHandler invoker;

    public void setInvoker(CommandHandler invoker) {
        this.invoker = invoker;
    }
}
