import command.*;

import utility.*;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        Help.commands = new HashSet<>(Arrays.asList(new Add(), new AddIfMax(), new AverageOfPrice(), new Clear(), new ExecuteScript(), new Exit(), new Help(), new Info(), new Insert(), new PrintFieldAscendingType(), new PrintFieldDescendingType(), new RemoveById(), new Reorder(), new Save(), new Show(), new Update()));
        File save = new File(System.getenv("FIFTH_LAB_SAVING_FILE_PATH"));
        Reader xmlReader = new XMLReader(save);
        xmlReader.read();
        CommandHandler invoker = new CommandHandler();
        TextUI textUI = new TextUI();
        Parser cParser = new ConsoleParser(textUI, invoker);
        cParser.read();
    }
}
