package utility;

import entity.*;
import exceptions.TooMuchArgumentsException;
import exceptions.UnknownCommandException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Класс для парсинга комманд из файла.
 *
 * @author AstroSoup
 */
public class FileParser extends Parser {
    private Scanner scanner;

    /**
     * Конструктор парсера файла.
     *
     * @param file    Объект класса File, из которого будут читаться комманды
     * @param textUI  Объект класса TextUI, выводящего информацию в консоль
     * @param invoker Объект класса CommandHandler, используемого для межмодульного взаимодействия
     * @throws FileNotFoundException
     */
    public FileParser(TextUI textUI, File file, CommandHandler invoker) throws FileNotFoundException {
        super(textUI, invoker);
        this.scanner = new Scanner(file);
    }

    /**
     * Метод чтения информации из файла.
     */
    @Override
    public void read() {
        while (scanner.hasNextLine()) {

            String[] args = scanner.nextLine().strip().split(" ");
            try {
                interpret(args);
            } catch (IllegalArgumentException e) {
                textUI.outputLn("В команде " + String.join(" ", args) + " неверный аргумент, напишите help чтобы узнать больше.");
                break;
            } catch (TooMuchArgumentsException | UnknownCommandException e) {
                break;
            }
        }
    }

    /**
     * Метод для парсинга имени пассажира из файла.
     *
     * @return имя пассажира в строковом представлении
     */
    public String parseName() {
        String name = scanner.nextLine();
        if (!Ticket.validateName(name)) throw new IllegalArgumentException();
        return name;
    }

    /**
     * Метод для парсинга цены билета из файла.
     *
     * @return цена билета в целочисленном положительном представлении
     */
    public long parsePrice() {
        long price = Long.parseLong(scanner.nextLine());
        if (!Ticket.validatePrice(price)) throw new IllegalArgumentException();
        return price;
    }

    /**
     * Метод для парсинга скидки на билет из файла.
     *
     * @return скидка на билет в представлении числа с плавающей точкой от 0 до 100
     */
    public Float parseDiscount() {
        String line = scanner.nextLine();
        float discount = Float.parseFloat(line);
        if (!Ticket.validateDiscount(discount)) throw new IllegalArgumentException();
        return discount;
    }

    /**
     * Метод для парсинга типа вагона указанного в билете из файла.
     *
     * @return Объект enum'а TicketType
     */
    public TicketType parseTicketType() {
        String ticketType = scanner.nextLine().toUpperCase();
        if (ticketType.isEmpty()) return null;
        return TicketType.valueOf(ticketType);
    }

    /**
     * Метод для парсинга координаты Х из файла.
     *
     * @return координата Х в представлении числа с плавающей точкой не большего 952
     */
    public float parseCoordinateX() {
        float x = Float.parseFloat(scanner.nextLine());
        if (!Coordinates.validateX(x)) throw new IllegalArgumentException();
        return x;
    }

    /**
     * Метод для парсинга координаты Y из файла.
     *
     * @return координата Y в представлении числа с плавающей точкой
     */
    public double parseCoordinateY() {
        Double y = Double.parseDouble(scanner.nextLine());
        if (!Coordinates.validateY(y)) throw new IllegalArgumentException();
        return y;
    }

    /**
     * Метод для парсинга паспортных данных из файла.
     *
     * @return паспортные данные в строковом представлении занимающие от 6 до 28 символов.
     */
    public String parsePassportID() {
        String passportID = scanner.nextLine();
        if (!Person.validatePassportID(passportID)) throw new IllegalArgumentException();
        return passportID;
    }

    /**
     * Метод для парсинга цвета глаз из файла.
     *
     * @return Объект enum'а Color
     */
    public Color parseEyeColor() {
        String line = scanner.nextLine().toUpperCase();
        Color eyeColor = Color.valueOf(line);
        if (!Person.validateEyeColor(eyeColor)) throw new IllegalArgumentException();
        return eyeColor;
    }

    /**
     * Метод для парсинга координаты локации X из файла.
     *
     * @return координата локации X в целочисленном представлении
     */
    public long parseLocationX() {
        long x = Long.parseLong(scanner.nextLine());
        return x;
    }

    /**
     * Метод для парсинга координаты локации Y из файла.
     *
     * @return координата локации Y в целочисленном представлении
     */
    public long parseLocationY() {
        long y = Long.parseLong(scanner.nextLine());
        if (!Location.validateY(y)) throw new IllegalArgumentException();
        return y;
    }

    /**
     * Метод для парсинга координаты локации Z из файла.
     *
     * @return координата локации Z в целочисленном представлении
     */
    public long parseLocationZ() {
        long z = Long.parseLong(scanner.nextLine());
        return z;
    }

    /**
     * Метод для парсинга локации из файла.
     *
     * @return Объект класса Location
     */
    public Location parseLocation() {
        return new Location(parseLocationX(), parseLocationY(), parseLocationZ());
    }

    /**
     * Метод для парсинга координат из файла.
     *
     * @return Объект класса Coordinates
     */
    public Coordinates parseCoordinates() {
        return new Coordinates(parseCoordinateX(), parseCoordinateY());
    }

    public String parseName(boolean strict) {
        return parseName();
    }

    public String parsePrice(boolean strict) {
        if (!strict) {
            String line = scanner.nextLine();
            if (line.equals("-")) return line;
            if (!Ticket.validatePrice(Long.parseLong(line))) throw new IllegalArgumentException();
            return line;
        } else {
            return Long.toString(parsePrice());
        }
    }

    public String parseDiscount(boolean strict) {
        if (!strict) {
            String line = scanner.nextLine();
            if (line.equals("-")) return line;
            float discount = Float.parseFloat(line);
            if (!Ticket.validateDiscount(discount)) throw new IllegalArgumentException();
            return Float.toString(discount);
        } else {
            return Float.toString(parseDiscount());
        }
    }

    public String parseTicketType(boolean strict) {
        if (!strict) {
            String line = scanner.nextLine();
            if (line.equals("-")) return line;
            if (line.isEmpty()) return null;
            String ticketType = line.toUpperCase();
            return TicketType.valueOf(ticketType).toString();
        } else {
            return parseTicketType().toString();
        }
    }

    public String parseCoordinateX(boolean strict) {
        if (!strict) {
            String line = scanner.nextLine();
            if (line.equals("-")) return line;
            float x = Float.parseFloat(line);
            if (!Coordinates.validateX(x)) throw new IllegalArgumentException();
            return Float.toString(x);
        } else {
            return Float.toString(parseCoordinateX());
        }
    }

    public String parseCoordinateY(boolean strict) {
        if (!strict) {
            String line = scanner.nextLine();
            if (line.equals("-")) return line;
            double y = Double.parseDouble(line);
            if (!Coordinates.validateY(y)) throw new IllegalArgumentException();
            return Double.toString(y);
        } else {
            return Double.toString(parseCoordinateY());
        }
    }

    public String parsePassportID(boolean strict) {
        if (!strict) {
            String passportID = scanner.nextLine();
            if (passportID.equals("-")) return passportID;
            if (!Person.validatePassportID(passportID)) throw new IllegalArgumentException();
            return passportID;
        } else {
            return parsePassportID();
        }
    }

    public String parseEyeColor(boolean strict) {
        if (!strict) {
            String line = scanner.nextLine().toUpperCase();
            if (line.equals("-")) return line;
            Color eyeColor = Color.valueOf(line);
            if (!Person.validateEyeColor(eyeColor)) throw new IllegalArgumentException();
            return eyeColor.toString();
        } else {
            return parseEyeColor().toString();
        }
    }

    public String parseLocationX(boolean strict) {
        if (!strict) {
            String line = scanner.nextLine();
            if (line.equals("-")) return line;
            long x = Long.parseLong(line);
            return Long.toString(x);
        } else {
            return Long.toString(parseLocationX());
        }
    }

    public String parseLocationY(boolean strict) {
        if (!strict) {
            String line = scanner.nextLine();
            if (line.equals("-")) return line;
            long y = Long.parseLong(line);
            if (!Location.validateY(y)) throw new IllegalArgumentException();
            return Long.toString(y);
        } else {
            return Long.toString(parseLocationY());
        }
    }

    public String parseLocationZ(boolean strict) {
        if (!strict) {
            String line = scanner.nextLine();
            if (line.equals("-")) return line;
            long z = Long.parseLong(line);
            return Long.toString(z);
        } else {
            return Long.toString(parseLocationZ());
        }
    }

    public StringifiedCoordinates parseCoordinates(boolean strict) {
        return new StringifiedCoordinates(parseCoordinateX(strict), parseCoordinateY(strict));
    }

    public StringifiedLocation parseLocation(boolean strict) {
        return new StringifiedLocation(parseLocationX(strict), parseLocationY(strict), parseLocationZ(strict));
    }

    public StringifiedPerson parsePerson(boolean strict) {
        return new StringifiedPerson(parsePassportID(strict), parseEyeColor(strict), parseLocation(strict));
    }

    public StringifiedTicket parseTicket(boolean strict) {
        return new StringifiedTicket(parseName(strict), parsePrice(strict), parseDiscount(strict), parseTicketType(strict), parseCoordinates(strict), parsePerson(strict));
    }
}
