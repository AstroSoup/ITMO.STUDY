package client;

import client.clientsideCommand.*;
import client.utility.ClientCommandHandler;
import client.utility.NetworkHandler;
import shared.command.*;
import client.parsers.*;
import client.utility.TextUI;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Help.commands = new HashSet<>(Arrays.asList(new Add(), new AddIfMax(), new AverageOfPrice(), new Clear(), new ExecuteScript(), new Exit(), new Help(), new Info(), new Insert(), new PrintFieldAscendingType(), new PrintFieldDescendingType(), new RemoveById(), new Reorder(), new Show(), new Update()));
        Scanner sc = new Scanner(System.in);


        TextUI textUI = new TextUI();
        textUI.output("Введите имя хоста(оставьте пустым для значения по умолчанию): ");
        String host = sc.nextLine();
        int port;
        do {
            textUI.output("Введите порт(число от 0 до 65535): ");
            String line = sc.nextLine();
            if (!line.isBlank()) port = Integer.parseInt(line);
            else port = -1;
        } while (port > 65535 || port < 0);

        NetworkHandler networkHandler = new NetworkHandler(host, port);

        ClientCommandHandler invoker = new ClientCommandHandler(networkHandler);
        textUI.output("Программа загружена успешно. Введите команду: ");
        Parser cParser = new ConsoleParser(textUI, invoker);
        cParser.read();

    }
}
