package utility;

import entity.TicketVectorWrapper;
import exceptions.NotEnoughRightsToReadException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InvalidObjectException;
import java.io.StringReader;
import java.util.Scanner;

/**
 * Класс, считывающий данные коллекции из .xml файла.
 *
 * @author AstroSoup
 */
public class XMLReader implements Reader {
    final private File file;
    /**
     * Конструктор ридера.
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
            System.out.println("Коллекция загружена успешно.");
        } catch (FileNotFoundException e) {
            System.out.println("Файл сохранения не найден, коллекция не была загружена.");
        } catch (JAXBException | InvalidObjectException e) {
            System.out.println("Файл сохранения поврежден, коллекция не была загружена.");

        } catch (NotEnoughRightsToReadException e) {
            System.out.println(e.getMessage());
        }
    }
}
