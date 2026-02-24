package server;

import server.storage.XMLReader;
import server.storage.XMLWriter;
import server.utility.CollectionHandler;
import server.utility.logging.ColorfulFormatter;
import server.utility.NetworkHandler;
import server.utility.RequestDeserializer;
import server.utility.logging.FlushingStreamHandler;
import shared.CommandHandler;
import shared.Reader;
import shared.command.Add;
import shared.command.AddIfMax;
import shared.entity.*;
import shared.exceptions.NotEnoughRightsToWriteException;


import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.*;

public class Main {
    public static final Logger LOGGER = Logger.getLogger("server");

    public static void main(String[] args) {

            LOGGER.setUseParentHandlers(false);
            StreamHandler sh = new FlushingStreamHandler(System.out, new ColorfulFormatter());
            sh.setLevel(Level.ALL);
            LOGGER.addHandler(sh);
            LOGGER.setLevel(Level.FINE);

            File save = new File(System.getenv("FIFTH_LAB_SAVING_FILE_PATH"));
            Reader xmlReader = new XMLReader(save);
            xmlReader.read();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    CollectionHandler.getHandler().save();
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, "Файл сохранения не найден. Коллекция не была сохранена.", ex);
                } catch (NotEnoughRightsToWriteException e) {
                    LOGGER.log(Level.SEVERE, e.getMessage());
                }
            }));

            CollectionHandler.getHandler();
            CommandHandler ch = new CommandHandler();
            RequestDeserializer rd = new RequestDeserializer(ch);
            try(Scanner sc = new Scanner(System.in)) {
                System.out.print("Введите имя хоста(оставьте пустым для значения по умолчанию): ");
                String host = sc.nextLine();
                int port;
                do {
                    System.out.print("Введите порт(число от 0 до 65535): ");
                    String line = sc.nextLine();
                    if (!line.isBlank()) port = Integer.parseInt(line);
                    else port = -1;
                } while (port > 65535 || port < 0);
                NetworkHandler nh = new NetworkHandler(host.isBlank() ? "localhost" : host,port, ch, rd);
                nh.listen();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Ошибка сетевого модуля" + e);
            }
    }
}
