package server;
import server.utility.logging.ColorfulFormatter;

import server.utility.logging.FlushingStreamHandler;
import server.utility.network.RequestHandleTask;
import server.utility.network.SelectionTask;
import server.utility.network.SocketChannelWrapper;
import server.utility.processing.CommandHandler;
import server.utility.processing.CommandProcessingTask;
import server.utility.processing.strategies.*;
import server.utility.response.CommandResponseTask;
import server.utility.response.Response;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.logging.*;

public class Main {
    public static final Logger LOGGER = Logger.getLogger("server");

    public static void main(String[] args) {

        LOGGER.setUseParentHandlers(false);
        StreamHandler sh = new FlushingStreamHandler(System.out, new ColorfulFormatter());
        sh.setLevel(Level.ALL);
        LOGGER.addHandler(sh);
        LOGGER.setLevel(Level.FINE);

        HashMap<String, Function<Void, CommandStrategy>> strats = new HashMap<>();
        strats.put("ADD", v -> new AddStrategy());
        strats.put("ADD_IF_MAX", v -> new AddIfMaxStrategy());
        strats.put("AVERAGE_OF_PRICE", v -> new AverageOfPriceStrategy());
        strats.put("CHECK_CONNECTION", v -> new CheckConnectionStrategy());
        strats.put("CHECK_ID", v -> new CheckIDStrategy());
        strats.put("CHECK_SIZE", v -> new CheckSizeStrategy());
        strats.put("CLEAR", v -> new ClearStrategy());
        strats.put("INFO", v -> new InfoStrategy());
        strats.put("INSERT_AT", v -> new InsertStrategy());
        strats.put("LOGIN", v -> new LoginStrategy());
        strats.put("PRINT_FIELD_ASCENDING_TYPE", v -> new PrintFieldAscendingTypeStrategy());
        strats.put("PRINT_FIELD_DESCENDING_TYPE", v -> new PrintFieldDescendingTypeStrategy());
        strats.put("REGISTER", v -> new RegisterStrategy());
        strats.put("REMOVE_BY_ID", v -> new RemoveByIdStrategy());
        strats.put("REORDER", v -> new ReorderStrategy());
        strats.put("SHOW", v -> new ShowStrategy());
        strats.put("UPDATE", v -> new UpdateStrategy());

        CommandHandler.setStrategies(strats);


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

            ServerSocketChannel server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(host.isBlank() ? "localhost" : host,port));
            server.configureBlocking(false);

            int processorNum = Runtime.getRuntime().availableProcessors();



            ExecutorService selectorPool = Executors.newFixedThreadPool(processorNum);

            Selector[] selectors = new Selector[processorNum];
            SelectionTask[] tasks = new SelectionTask[processorNum];

            for (int i = 0; i < processorNum; i++) {
                selectors[i] = Selector.open();
                tasks[i] = new SelectionTask(selectors[i]);
                selectorPool.submit(tasks[i]);
            }

            int nextSelector = 0;

            while(true) {
                SocketChannel client = server.accept();
                if (!Objects.isNull(client)) {

                    client.configureBlocking(false);
                    Selector selector = selectors[nextSelector];

                    selector.wakeup();
                    LOGGER.info("I think it should register...");
                    tasks[nextSelector].register(client);

                    nextSelector = (nextSelector + 1) % processorNum;
                }
            }


        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Ошибка сетевого модуля" + e);
        }
    }
}
