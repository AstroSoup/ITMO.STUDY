package server.storage;

import server.utility.CollectionHandler;
import shared.Reader;
import shared.entity.TicketVectorWrapper;
import shared.exceptions.NotEnoughRightsToReadException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InvalidObjectException;
import java.io.StringReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс, считывающий данные коллекции из .xml файла.
 *
 * @author AstroSoup
 */
public class XMLReader implements Reader {
    final private File file;
    private final Logger READERLOGGER = Logger.getLogger("server.storage.XMLReader");

    /**
     * Конструктор ридера.
     *
     * @param file Объект файла из которого считываются данные коллекции.
     */
    public XMLReader(File file) {
        this.file = file;
    }

    /**
     * Метод, читающий данные из файла заданного при создании объекта ридера.
     */

    public void read() {
        try {
            if (!file.exists()) throw new FileNotFoundException();
            if (!file.canRead()) throw new NotEnoughRightsToReadException();
            Scanner sc = new Scanner(file);
            StringBuilder xmlData = new StringBuilder();
            while (sc.hasNextLine()) {
                xmlData.append(sc.nextLine());
            }

            JAXBContext context = JAXBContext.newInstance(TicketVectorWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            TicketVectorWrapper ticketVectorWrapper = (TicketVectorWrapper) unmarshaller.unmarshal(new StringReader(xmlData.toString()));
            CollectionHandler.getHandler().setCollection(ticketVectorWrapper.getTickets());
            CollectionHandler.getHandler().setCDT(ticketVectorWrapper.getCDT());
            READERLOGGER.log(Level.INFO, "Коллекция загружена успешно.");
        } catch (FileNotFoundException e) {
            READERLOGGER.log(Level.WARNING, "Файл сохранения не найден, коллекция не была загружена.", e);
        } catch (JAXBException | InvalidObjectException e) {
            READERLOGGER.log(Level.SEVERE, "Файл сохранения поврежден, коллекция не была загружена.", e);
        } catch (NotEnoughRightsToReadException e) {
            READERLOGGER.log(Level.WARNING, e.getMessage());
        }
    }
}
