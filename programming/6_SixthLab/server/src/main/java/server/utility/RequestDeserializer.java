package server.utility;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import server.utility.CollectionHandler;
import shared.CommandHandler;
import shared.command.Command;
import shared.entity.TicketVectorWrapper;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Scanner;
import java.util.logging.*;


/**
 * Класс использующийся для десереализации команд
 */
public class RequestDeserializer {

    private CommandHandler commandHandler;
    private final Logger DESERIALIZERLOGGER = Logger.getLogger("server.utility.RequestDeserializer");

    public RequestDeserializer(CommandHandler ch) {
        this.commandHandler = ch;
        DESERIALIZERLOGGER.setUseParentHandlers(true);
    }

    public Command deserialize(String xml) throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new StringReader(xml));
            reader.readLine();
            String name = reader.readLine();
            String className = "shared.command." + name.substring(1, 2).toUpperCase() + name.substring(2, name.length() - 1);
            DESERIALIZERLOGGER.log(Level.FINE, "Принята команда: " + className);
            DESERIALIZERLOGGER.finer(xml.trim());
            Class<?> clazz = Class.forName(className);

            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            Object cmd = unmarshaller.unmarshal(new StringReader(xml.trim()));

        /*
        переназначение полей invoker и collectionHandler так как они находятся в серверной части приложения.
        */

            Field f = cmd.getClass().getDeclaredField("invoker");
            f.setAccessible(true);
            f.set(cmd, commandHandler);
            f = cmd.getClass().getDeclaredField("collectionHandler");
            f.setAccessible(true);
            f.set(cmd, CollectionHandler.getHandler());

            return (Command) cmd;
        } catch (JAXBException | ClassNotFoundException e) {

            DESERIALIZERLOGGER.log(Level.SEVERE, "Команда была интерпретирована некорректно" + e.getMessage());
            throw new Exception();
        } catch (NoSuchFieldException e) {
            DESERIALIZERLOGGER.log(Level.SEVERE, "У команды отсутствует один или несколько обязательных полей", e);
            throw new Exception();
        } catch (IllegalAccessException e) {
            DESERIALIZERLOGGER.log(Level.SEVERE, "Ошибка доступа к полю класса", e);
            throw new Exception();
        }
    }
}
