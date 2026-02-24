package command;

import exceptions.NotEnoughRightsToReadException;
import exceptions.ScriptRecursionException;
import utility.*;
import utility.CommandHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Класс команды для запуска скрипта с командами.
 *
 * @author AstroSoup
 */
public class ExecuteScript extends UsableCommand {

    final private String filePath;
    final private TextUI textUI;
    final private CommandHandler invoker;

    final private static ArrayList<File> openedFiles = new ArrayList<>();

    /**
     * Конструктор
     *
     * @param textUI   Объект для вывода результата выполнения команд скрипта
     * @param filePath Путь до файла скрипта
     * @param invoker  Объект CommandHandler в который будет записан результат выполнения команды
     */
    public ExecuteScript(String filePath, TextUI textUI, CommandHandler invoker) {
        super("Execute_script", "Команда для запуска скрипта с командами. Путь до файла скрипта необходимо указать на той же строке, что и имя команды");
        this.textUI = textUI;
        this.filePath = filePath;
        this.invoker = invoker;
    }

    public ExecuteScript() {
        this(null, null, null);
    }

    public void execute() {
        try {
            File file = new File(filePath);
            if (openedFiles.contains(file)) {
                throw new ScriptRecursionException();
            }
            if (!file.exists()) throw new FileNotFoundException();
            if (!file.canRead()) throw new NotEnoughRightsToReadException();
            openedFiles.add(file);
            Parser parser = new FileParser(textUI, file, invoker);
            parser.read();
            openedFiles.remove(file);
            invoker.setFeedback("Выполнение скрипта завершено.");
        } catch (FileNotFoundException e) {
            invoker.setFeedback("Файл не найден.");
        } catch (ScriptRecursionException | NotEnoughRightsToReadException e) {
            invoker.setFeedback(e.getMessage());
        }
    }
}
