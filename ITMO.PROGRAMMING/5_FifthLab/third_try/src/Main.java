
import command.*;
import utility.*;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Help.commands.add(new Add(null, null));
            Help.commands.add(new AddIfMax(null, null));
            Help.commands.add(new AverageOfPrice(null));
            Help.commands.add(new Clear(null));
            Help.commands.add(new ExecuteScript(null, null, null));
            Help.commands.add(new Exit());
            Help.commands.add(new Help(null));
            Help.commands.add(new Info(null));
            Help.commands.add(new Insert(-1, null, null));
            Help.commands.add(new PrintFieldAscendingType(null));
            Help.commands.add(new PrintFieldDescendingType(null));
            Help.commands.add(new Reorder(null));
            Help.commands.add(new RemoveById(-1,null));
            Help.commands.add(new Save(null));
            Help.commands.add(new Show(null));
            Help.commands.add(new Update(-1, null, null));

            Scanner sc = new Scanner(System.in);
            System.out.print("Введите имя переменной окружения в которой расположен путь до файла сохранения(оставьте поле пустым если хотите использовать значение по умолчанию): ");
            String envVarName = sc.nextLine();
            try {
                File save = new File(envVarName.isEmpty() ? System.getenv("FIFTH_LAB_SAVING_FILE_PATH") : System.getenv(envVarName));
                Reader xmlReader = new XMLReader(save);
                xmlReader.read();
            }catch (NullPointerException e){
                e.printStackTrace();
                System.out.println("Переменная окружения с таким именем не найдена. Коллекция не загружена.");
            }

            CommandHandler invoker = new CommandHandler();
            TextUI textUI = new TextUI();
            Parser cParser = new ConsoleParser(textUI, invoker);
            cParser.read();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
}
