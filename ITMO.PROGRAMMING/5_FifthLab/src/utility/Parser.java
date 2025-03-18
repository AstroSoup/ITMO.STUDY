package utility;

import command.*;
import entity.*;
import exceptions.TooMuchArgumentsException;
import exceptions.UnknownCommandException;

/**
 * Абстрактный класс парсера комманд. Любой класс интерпретирующий прочтенные команды наследуется от него.
 *
 * @author AstroSoup
 */

public abstract class Parser implements Reader {

    protected TextUI textUI;
    private CommandHandler invoker;

    /**
     * Конструктор парсера.
     *
     * @param textUI  объект TextUI используемый для вывода в консоль
     * @param invoker объект CommandHandler используемый для межмодульного взаимодействия
     */

    public Parser(TextUI textUI, CommandHandler invoker) {
        this.textUI = textUI;
        this.invoker = invoker;
    }

    /**
     * Абстрактный метод, реализующий чтение из некоторого источника.
     */
    public abstract void read();

    /**
     * Абстрактный метод, вызываемый чтобы считать имя пассажира.
     *
     * @return имя пассажира
     */
    public abstract String parseName();

    /**
     * Абстрактный метод, вызываемый чтобы считать цену билета.
     *
     * @return цена билета
     */
    public abstract long parsePrice();

    /**
     * Абстрактный метод, вызываемый чтобы считать скидку на билет.
     *
     * @return скидка на билет
     */
    public abstract Float parseDiscount();

    /**
     * Абстрактный метод, вызываемый чтобы считать тип вагона.
     *
     * @return enum, тип вагона указанный в билете
     */
    public abstract TicketType parseTicketType();

    /**
     * Абстрактный метод, вызываемый чтобы считать координаты точки прибытия.
     *
     * @return объект координат
     */
    public abstract Coordinates parseCoordinates();

    /**
     * Абстрактный метод, вызываемый чтобы считать паспортные данные пассажира.
     *
     * @return строка пасспортных данных
     */
    public abstract String parsePassportID();

    /**
     * Абстрактный метод, вызываемый чтобы считать цвет глаз пассажира.
     *
     * @return enum, цвет глаз пассажира
     */
    public abstract Color parseEyeColor();

    /**
     * Абстрактный метод, вызываемый чтобы считать координаты точки отправления.
     *
     * @return объект Location, содержащий координаты точки отправления.
     */
    public abstract Location parseLocation();

    /**
     * Метод, собирающий все собранные данные в объект билета.
     *
     * @return объект Ticket, сохраняемый в коллекции.
     */
    protected Ticket parseTicket() throws IllegalArgumentException {
        return new Ticket(parseName(), parsePrice(), parseDiscount(), parseTicketType(), parseCoordinates(), new Person(parsePassportID(), parseEyeColor(), parseLocation()));
    }

    public abstract StringifiedTicket parseTicket(boolean strict);

    /**
     * Метод, интерпретирующий строку команды, формирующий команду и отправляющий команду на исполнение в CommandHandler.
     *
     * @param args массив строк, разделенных по " "
     */
    public void interpret(String[] args) throws IllegalArgumentException, TooMuchArgumentsException, UnknownCommandException {
        try {
            switch (args[0].toUpperCase()) {
                case "ADD" -> {
                    if (args.length > 1) throw new TooMuchArgumentsException(0, args.length - 1);
                    invoker.invoke(new Add(parseTicket(), invoker));
                }
                case "ADD_IF_MAX" -> {
                    if (args.length > 1) throw new TooMuchArgumentsException(0, args.length - 1);
                    invoker.invoke(new AddIfMax(parseTicket(), invoker));
                }
                case "AVERAGE_OF_PRICE" -> {
                    if (args.length > 1) throw new TooMuchArgumentsException(0, args.length - 1);
                    invoker.invoke(new AverageOfPrice(invoker));
                }
                case "CLEAR" -> {
                    if (args.length > 1) throw new TooMuchArgumentsException(0, args.length - 1);
                    invoker.invoke(new Clear(invoker));
                }
                case "EXECUTE_SCRIPT" -> {
                    if (args.length > 2) throw new TooMuchArgumentsException(1, args.length - 1);
                    invoker.invoke(new ExecuteScript(args[1], textUI, invoker));
                }
                case "EXIT" -> {
                    if (args.length > 1) throw new TooMuchArgumentsException(0, args.length - 1);
                    invoker.invoke(new Exit());
                }
                case "HELP" -> {
                    if (args.length > 1) throw new TooMuchArgumentsException(0, args.length - 1);
                    invoker.invoke(new Help(invoker));
                }
                case "INFO" -> {
                    if (args.length > 1) throw new TooMuchArgumentsException(0, args.length - 1);
                    invoker.invoke(new Info(invoker));
                }
                case "INSERT_AT" -> {
                    if (args.length > 2) throw new TooMuchArgumentsException(1, args.length - 1);
                    int index = Integer.parseInt(args[1]);
                    invoker.invoke(new CheckSize(index, invoker));
                    if (invoker.getFeedback().isEmpty()) invoker.setFeedback("В коллекции нет такого индекса.");
                    else invoker.invoke(new Insert(index, parseTicket(), invoker));
                }
                case "PRINT_FIELD_ASCENDING_TYPE" -> {
                    if (args.length > 1) throw new TooMuchArgumentsException(0, args.length - 1);
                    invoker.invoke(new PrintFieldAscendingType(invoker));
                }
                case "PRINT_FIELD_DESCENDING_TYPE" -> {
                    if (args.length > 1) throw new TooMuchArgumentsException(0, args.length - 1);
                    invoker.invoke(new PrintFieldDescendingType(invoker));
                }
                case "REMOVE_BY_ID" -> {
                    if (args.length > 2) throw new TooMuchArgumentsException(1, args.length - 1);
                    invoker.invoke(new RemoveById(Integer.parseInt(args[1]), invoker));
                }
                case "REORDER" -> {
                    if (args.length > 1) throw new TooMuchArgumentsException(0, args.length - 1);
                    invoker.invoke(new Reorder(invoker));
                }
                case "SHOW" -> {
                    if (args.length > 1) throw new TooMuchArgumentsException(0, args.length - 1);
                    invoker.invoke(new Show(invoker));
                }
                case "SAVE" -> {
                    if (args.length > 1) throw new TooMuchArgumentsException(0, args.length - 1);
                    invoker.invoke(new Save(invoker));
                }
                case "UPDATE" -> {
                    if (args.length > 2) throw new TooMuchArgumentsException(1, args.length - 1);
                    int id = Integer.parseInt(args[1]);
                    invoker.invoke(new CheckID(id, invoker));
                    if (invoker.getFeedback().isEmpty()) invoker.setFeedback("Билета с таким ID нет в коллекции.");
                    else invoker.invoke(new Update(id, parseTicket(false), invoker));
                }
                default -> throw new UnknownCommandException(String.join(" ", args));
            }
            textUI.outputLn(invoker.getFeedback());
        } catch (ArrayIndexOutOfBoundsException e) {
            textUI.outputLn("В команде '" + String.join(" ", args) + "' недостаточно аргументов, напишите help чтобы узнать больше.");
        }
    }
}
