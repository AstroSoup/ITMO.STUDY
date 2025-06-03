package client;

import client.UI.AuthFrame;
import client.UI.MainFrame;
import client.clientsideCommand.*;
import client.utility.ClientCommandHandler;
import client.utility.NetworkHandler;
import client.utility.RequestSerializer;
import shared.command.*;
import client.parsers.*;
import client.utility.TextUI;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static ClientCommandHandler invoker = new ClientCommandHandler();
    public static MainFrame mainframe;
    public static void main(String[] args) throws IOException {


        Help.commands = new HashSet<>(Arrays.asList(new Add(), new AddIfMax(), new AverageOfPrice(), new Clear(), new ExecuteScript(), new Exit(), new Help(), new Info(), new Insert(), new PrintFieldAscendingType(), new PrintFieldDescendingType(), new RemoveById(), new Reorder(), new Show(), new Update()));
        Scanner sc = new Scanner(System.in);


        String host = "";
        int port;
        if (args.length >= 1 && args[0].equals("cli")) {
            TextUI textUI = new TextUI();
            textUI.output("Введите имя хоста(оставьте пустым для значения по умолчанию): ");
            host = sc.nextLine();
            do {
                textUI.output("Введите порт(число от 0 до 65535): ");
                String line = sc.nextLine();
                if (!line.isBlank()) port = Integer.parseInt(line);
                else port = -1;
            } while (port > 65535 || port < 0);

            textUI.output("Программа загружена успешно. Введите команду: ");
            NetworkHandler networkHandler = new NetworkHandler(host, port);
            invoker = new ClientCommandHandler();
            invoker.setNetworkHandler(networkHandler);
            Parser cParser = new ConsoleParser(textUI, invoker);
            cParser.read();

        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    AuthFrame frame = new AuthFrame();
                    frame.setVisible(true);
                }
            });
            port = 3333;
            NetworkHandler networkHandler = new NetworkHandler(host, port);
            networkHandler.openConnection();
            invoker = new ClientCommandHandler();
            invoker.setNetworkHandler(networkHandler);
            networkHandler.setHandler(invoker);
        }



    }
    public static void openMainFrame(String username) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainframe = new MainFrame(username);
            Thread polling = new Thread(new Runnable() {
                public void run() {
                    try {
                        ClientCommandHandler cch = new ClientCommandHandler();
                        NetworkHandler nh =  new NetworkHandler("", 3333);
                        nh.openConnection();
                        cch.setNetworkHandler(nh);
                        while (true) {
                            cch.invoke(new LongPoll());
                            while (cch.getFeedback().isEmpty()) {
                                wait();
                            }
                            mainframe.setTable(new RequestSerializer().deserialize(cch.getFeedback()));
                            cch.setFeedback("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            mainframe.setVisible(true);
        });
    }
}
