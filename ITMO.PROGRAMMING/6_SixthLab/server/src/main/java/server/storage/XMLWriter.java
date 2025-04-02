package server.storage;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс записывающий данные из коллекции в формате
 */
public class XMLWriter {

    private File file;
    private static final Logger WRITERLOGGER = Logger.getLogger("server.storage.XMLWriter");
    /**
     * Конструктор врайтера.
     * @param file Файл в который будет производиться запись
     */
    public XMLWriter(File file) {
        this.file = file;
    }
    /**
     * Метод сериализующий объект в XML формат и записывающий его в файл file.
     * @param o Объект для сериализации.
     * @throws IOException
     */
    public void write(Serializable o) throws IOException{
        try {
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(o, new FileOutputStream(file));
        } catch (JAXBException e) {
            WRITERLOGGER.log(Level.SEVERE, "Выбранный объект не сериализуем.", e);
        }
    }
}
