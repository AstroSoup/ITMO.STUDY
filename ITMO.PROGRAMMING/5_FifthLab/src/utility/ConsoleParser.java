package utility;

import entity.*;
import exceptions.TooMuchArgumentsException;
import exceptions.UnknownCommandException;


import java.util.Scanner;

/**
 * Класс для парсинга комманд из консоли.
 *
 * @author AstroSoup
 */
public class ConsoleParser extends Parser {

    private Scanner scanner = new Scanner(System.in);

    /**
     * Конструктор парсера консоли.
     *
     * @param textUI  Объект класса TextUI, выводящего информацию в консоль
     * @param invoker Объект класса CommandHandler, используемого для межмодульного взаимодействия.
     */
    public ConsoleParser(TextUI textUI, CommandHandler invoker) {
        super(textUI, invoker);
    }

    /**
     * Метод чтения информации из консоли.
     */
    @Override
    public void read() {
        while (scanner.hasNextLine()) {
            try {
                String[] args = scanner.nextLine().strip().split(" ");
                interpret(args);
            } catch (TooMuchArgumentsException | UnknownCommandException e) {
                textUI.outputLn(e.getMessage());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                textUI.outputLn("Неверный аргумент. Напишите help чтобы узнать больше.");
            }
        }

    }

    /**
     * Метод для парсинга имени пассажира из консоли. Рекуррентно запрашивает у пользователя данные до тех пор пока он не введет данные в правильном формате.
     *
     * @return имя пассажира в строковом представлении
     */
    public String parseName() {
        try {
            textUI.output("Введите имя: ");
            String name = scanner.nextLine();
            if (!Ticket.validateName(name)) throw new IllegalArgumentException();
            return name;
        } catch (IllegalArgumentException e) {
            textUI.outputLn("Неверный аргумент. Поле не может быть пустым.");
            return parseName();
        }
    }

    /**
     * Метод для парсинга цены билета из консоли. Рекуррентно запрашивает у пользователя данные до тех пор пока он не введет данные в правильном формате.
     *
     * @return цена билета в целочисленном положительном представлении
     */
    public long parsePrice() {
        try {
            textUI.output("Введите цену: ");
            long price = Long.parseLong(scanner.nextLine());
            if (!Ticket.validatePrice(price)) throw new IllegalArgumentException();
            return price;
        } catch (IllegalArgumentException e) {
            textUI.outputLn("Неверный аргумент. Цена может принимать целочисленные значения больше 0 и меньше " + Long.MAX_VALUE + ". Поле не может быть пустым.");
            return parsePrice();
        }
    }

    /**
     * Метод для парсинга скидки на билет из консоли. Рекуррентно запрашивает у пользователя данные до тех пор пока он не введет данные в правильном формате.
     *
     * @return скидка на билет в представлении числа с плавающей точкой от 0 до 100
     */
    public Float parseDiscount() {
        try {
            textUI.output("Введите скидку: ");
            String line = scanner.nextLine();
            float discount = Float.parseFloat(line);
            if (!Ticket.validateDiscount(discount)) throw new IllegalArgumentException();
            return discount;
        } catch (IllegalArgumentException e) {
            textUI.outputLn("Неверный аргумент. Скидка может принимать числовые значения от 0 до 100 включительно. Поле не может быть пустым.");
            return parseDiscount();
        }
    }

    /**
     * Метод для парсинга типа вагона указанного в билете из консоли. Рекуррентно запрашивает у пользователя данные до тех пор пока он не введет данные в правильном формате.
     *
     * @return Объект enum'а TicketType
     */
    public TicketType parseTicketType() {
        textUI.output("Введите тип вагона(VIP, USUAL, BUDGETARY, CHEAP): ");
        String ticketType = scanner.nextLine().toUpperCase();
        try {
            return TicketType.valueOf(ticketType);
        } catch (IllegalArgumentException e) {
            if (ticketType.isEmpty()) return null;
            textUI.outputLn("Неверный аргумент. Тип билета может принимать только значения VIP, USUAL, BUDGETARY или CHEAP. Поле может быть пустым.");
            return parseTicketType();
        }
    }

    /**
     * Метод для парсинга координаты Х из консоли. Рекуррентно запрашивает у пользователя данные до тех пор пока он не введет данные в правильном формате.
     *
     * @return координата Х в представлении числа с плавающей точкой не большего 952
     */
    public float parseCoordinateX() {
        try {
            textUI.output("X: ");
            float x = Float.parseFloat(scanner.nextLine());
            if (!Coordinates.validateX(x)) throw new IllegalArgumentException();
            return x;
        } catch (IllegalArgumentException e) {
            textUI.outputLn("Неверный аргумент. X может принимать числовые значения между " + (-Float.MAX_VALUE) + " и 952, поле не может быть пустым.");
            return parseCoordinateX();
        }
    }

    /**
     * Метод для парсинга координаты Y из консоли. Рекуррентно запрашивает у пользователя данные до тех пор пока он не введет данные в правильном формате.
     *
     * @return координата Y в представлении числа с плавающей точкой
     */
    public double parseCoordinateY() {
        try {
            textUI.output("Y: ");
            Double y = Double.parseDouble(scanner.nextLine());
            if (!Coordinates.validateY(y)) throw new IllegalArgumentException();
            return y;
        } catch (IllegalArgumentException e) {
            textUI.outputLn("Неверный аргумент. Y может принимать только числовые значения между " + (-Double.MAX_VALUE) + " и " + Double.MAX_VALUE + ", поле не может быть пустым.");
            return parseCoordinateY();
        }
    }

    /**
     * Метод для парсинга паспортных данных из консоли. Рекуррентно запрашивает у пользователя данные до тех пор пока он не введет данные в правильном формате.
     *
     * @return паспортные данные в строковом представлении занимающие от 6 до 28 символов.
     */
    public String parsePassportID() {
        try {
            textUI.output("Введите паспортные данные пассажира: ");
            String passportID = scanner.nextLine();
            if (!Person.validatePassportID(passportID)) throw new IllegalArgumentException();
            return passportID;
        } catch (IllegalArgumentException e) {
            textUI.outputLn("Неверный аргумент. Паспортные данные могут принимать строковые значения от 6 до 28 символов длинной, поле не может быть пустым.");
            return parsePassportID();
        }
    }

    /**
     * Метод для парсинга цвета глаз из консоли. Рекуррентно запрашивает у пользователя данные до тех пор пока он не введет данные в правильном формате.
     *
     * @return Объект enum'а Color
     */
    public Color parseEyeColor() {
        try {
            textUI.output("Введите цвет глаз пассажира(GREEN, RED, ORANGE): ");
            String line = scanner.nextLine().toUpperCase();
            Color eyeColor = Color.valueOf(line);
            if (!Person.validateEyeColor(eyeColor)) throw new IllegalArgumentException();
            return eyeColor;
        } catch (IllegalArgumentException e) {
            textUI.outputLn("Неверный аргумент. Цвет глаз может принимать только значения GREEN, RED или ORANGE. Поле не может быть пустым.");
            return parseEyeColor();
        }
    }

    /**
     * Метод для парсинга координаты локации X из консоли. Рекуррентно запрашивает у пользователя данные до тех пор пока он не введет данные в правильном формате.
     *
     * @return координата локации X в целочисленном представлении
     */
    public long parseLocationX() {
        try {
            textUI.output("X: ");
            long x = Long.parseLong(scanner.nextLine());
            return x;
        } catch (IllegalArgumentException e) {
            textUI.outputLn("Неверный аргумент. X может принимать только целочисленные значения между " + (Long.MIN_VALUE) + " и " + Long.MAX_VALUE + ", поле не может быть пустым.");
            return parseLocationX();
        }
    }

    /**
     * Метод для парсинга координаты локации Y из консоли. Рекуррентно запрашивает у пользователя данные до тех пор пока он не введет данные в правильном формате.
     *
     * @return координата локации Y в целочисленном представлении
     */
    public long parseLocationY() {
        try {
            textUI.output("Y: ");
            long y = Long.parseLong(scanner.nextLine());
            if (!Location.validateY(y)) throw new IllegalArgumentException();
            return y;
        } catch (IllegalArgumentException e) {
            textUI.outputLn("Неверный аргумент. Y может принимать только целочисленные значения между " + (Long.MIN_VALUE) + " и " + Long.MAX_VALUE + ", поле не может быть пустым.");
            return parseLocationY();
        }
    }

    /**
     * Метод для парсинга координаты локации Z из консоли. Рекуррентно запрашивает у пользователя данные до тех пор пока он не введет данные в правильном формате.
     *
     * @return координата локации Z в целочисленном представлении
     */
    public long parseLocationZ() {
        try {
            textUI.output("Z: ");
            long z = Long.parseLong(scanner.nextLine());
            return z;
        } catch (IllegalArgumentException e) {
            textUI.outputLn("Неверный аргумент. Z может принимать только целочисленные значения между " + (Long.MIN_VALUE) + " и " + Long.MAX_VALUE + ", поле не может быть пустым.");
            return parseLocationX();
        }
    }

    /**
     * Метод для парсинга локации из консоли.
     *
     * @return Объект класса Location
     */
    public Location parseLocation() {
        textUI.outputLn("Введите координаты точки отбытия(X, Y, Z по одной в строку): ");

        return new Location(parseLocationX(), parseLocationY(), parseLocationZ());
    }

    /**
     * Метод для парсинга координат из консоли.
     *
     * @return Объект класса Coordinates
     */
    public Coordinates parseCoordinates() {
        textUI.outputLn("Введите координаты точки прибытия(X, Y по одной в строку): ");
        return new Coordinates(parseCoordinateX(), parseCoordinateY());
    }

    public String parseName(boolean strict) {
        return parseName();
    }

    public String parsePrice(boolean strict) {
        if (!strict) {
            try {
                textUI.output("Введите цену: ");
                String line = scanner.nextLine();
                if (line.equals("-")) return line;
                if (!Ticket.validatePrice(Long.parseLong(line))) throw new IllegalArgumentException();
                return line;
            } catch (IllegalArgumentException e) {
                textUI.outputLn("Неверный аргумент. Цена может принимать целочисленные значения больше 0 и меньше " + Long.MAX_VALUE + ". Поле не может быть пустым.");
                return parsePrice(strict);
            }
        } else {
            return Long.toString(parsePrice());
        }
    }

    public String parseDiscount(boolean strict) {
        if (!strict) {
            try {
                textUI.output("Введите скидку: ");
                String line = scanner.nextLine();
                if (line.equals("-")) return line;
                float discount = Float.parseFloat(line);
                if (!Ticket.validateDiscount(discount)) throw new IllegalArgumentException();
                return Float.toString(discount);
            } catch (IllegalArgumentException e) {
                textUI.outputLn("Неверный аргумент. Скидка может принимать числовые значения от 0 до 100 включительно. Поле не может быть пустым.");
                return parseDiscount(strict);
            }
        } else {
            return Float.toString(parseDiscount());
        }
    }

    public String parseTicketType(boolean strict) {
        if (!strict) {
            textUI.output("Введите тип вагона(VIP, USUAL, BUDGETARY, CHEAP): ");
            String line = scanner.nextLine();
            if (line.equals("-")) return line;
            String ticketType = line.toUpperCase();
            try {
                return TicketType.valueOf(ticketType).toString();
            } catch (IllegalArgumentException e) {
                if (ticketType.isEmpty()) return null;
                textUI.outputLn("Неверный аргумент. Тип билета может принимать только значения VIP, USUAL, BUDGETARY или CHEAP. Поле может быть пустым.");
                return parseTicketType(strict);
            }
        } else {
            return parseTicketType().toString();
        }
    }

    public String parseCoordinateX(boolean strict) {
        if (!strict) {
            try {
                textUI.output("X: ");
                String line = scanner.nextLine();
                if (line.equals("-")) return line;
                float x = Float.parseFloat(line);
                if (!Coordinates.validateX(x)) throw new IllegalArgumentException();
                return Float.toString(x);
            } catch (IllegalArgumentException e) {
                textUI.outputLn("Неверный аргумент. X может принимать числовые значения между " + (-Float.MAX_VALUE) + " и 952, поле не может быть пустым.");
                return parseCoordinateX(strict);
            }
        } else {
            return Float.toString(parseCoordinateX());
        }
    }

    public String parseCoordinateY(boolean strict) {
        if (!strict) {
            try {
                textUI.output("Y: ");
                String line = scanner.nextLine();
                if (line.equals("-")) return line;
                double y = Double.parseDouble(line);
                if (!Coordinates.validateY(y)) throw new IllegalArgumentException();
                return Double.toString(y);
            } catch (IllegalArgumentException e) {
                textUI.outputLn("Неверный аргумент. Y может принимать только числовые значения между " + (-Double.MAX_VALUE) + " и " + Double.MAX_VALUE + ", поле не может быть пустым.");
                return parseCoordinateY(strict);
            }
        } else {
            return Double.toString(parseCoordinateY());
        }
    }

    public String parsePassportID(boolean strict) {
        if (!strict) {
            try {
                textUI.output("Введите паспортные данные пассажира: ");
                String passportID = scanner.nextLine();
                if (passportID.equals("-")) return passportID;
                if (!Person.validatePassportID(passportID)) throw new IllegalArgumentException();
                return passportID;
            } catch (IllegalArgumentException e) {
                textUI.outputLn("Неверный аргумент. Паспортные данные могут принимать строковые значения от 6 до 28 символов длинной, поле не может быть пустым.");
                return parsePassportID(strict);
            }
        } else {
            return parsePassportID();
        }
    }

    public String parseEyeColor(boolean strict) {
        if (!strict) {
            try {
                textUI.output("Введите цвет глаз пассажира(GREEN, RED, ORANGE): ");
                String line = scanner.nextLine().toUpperCase();
                if (line.equals("-")) return line;
                Color eyeColor = Color.valueOf(line);
                if (!Person.validateEyeColor(eyeColor)) throw new IllegalArgumentException();
                return eyeColor.toString();
            } catch (IllegalArgumentException e) {
                textUI.outputLn("Неверный аргумент. Цвет глаз может принимать только значения GREEN, RED или ORANGE. Поле не может быть пустым.");
                return parseEyeColor(strict);
            }
        } else {
            return parseEyeColor().toString();
        }
    }

    public String parseLocationX(boolean strict) {
        if (!strict) {
            try {
                textUI.output("X: ");
                String line = scanner.nextLine();
                if (line.equals("-")) return line;
                long x = Long.parseLong(line);
                return Long.toString(x);
            } catch (IllegalArgumentException e) {
                textUI.outputLn("Неверный аргумент. X может принимать только целочисленные значения между " + (Long.MIN_VALUE) + " и " + Long.MAX_VALUE + ", поле не может быть пустым.");
                return parseLocationX(strict);
            }
        } else {
            return Long.toString(parseLocationX());
        }
    }

    public String parseLocationY(boolean strict) {
        if (!strict) {
            try {
                textUI.output("Y: ");
                String line = scanner.nextLine();
                if (line.equals("-")) return line;
                long y = Long.parseLong(line);
                if (!Location.validateY(y)) throw new IllegalArgumentException();
                return Long.toString(y);
            } catch (IllegalArgumentException e) {
                textUI.outputLn("Неверный аргумент. Y может принимать только целочисленные значения между " + (Long.MIN_VALUE) + " и " + Long.MAX_VALUE + ", поле не может быть пустым.");
                return parseLocationY(strict);
            }
        } else {
            return Long.toString(parseLocationY());
        }
    }

    public String parseLocationZ(boolean strict) {
        if (!strict) {
            try {
                textUI.output("Z: ");
                String line = scanner.nextLine();
                if (line.equals("-")) return line;
                long z = Long.parseLong(line);
                return Long.toString(z);
            } catch (IllegalArgumentException e) {
                textUI.outputLn("Неверный аргумент. Z может принимать только целочисленные значения между " + (Long.MIN_VALUE) + " и " + Long.MAX_VALUE + ", поле не может быть пустым.");
                return parseLocationZ(strict);
            }
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
