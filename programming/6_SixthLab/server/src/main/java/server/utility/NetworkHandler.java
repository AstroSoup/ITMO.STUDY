package server.utility;

import server.utility.CollectionHandler;
import shared.CommandHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс реализующий сетевое взаимодействие на сервере
 */
public class NetworkHandler {

    final private Logger NETWORKLOGGER = Logger.getLogger("server.utility.NetworkHandler");

    final private ServerSocketChannel ssc;
    final private Selector selector;
    final private RequestDeserializer requestDeserializer;


    CommandHandler commandHandler;

    public NetworkHandler(String hostname, int port, CommandHandler commandHandler, RequestDeserializer rd) throws IOException {
        NETWORKLOGGER.setUseParentHandlers(true);
        this.requestDeserializer = rd;
        ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(hostname, port));
        selector = Selector.open();
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        this.commandHandler = commandHandler;


    }

    public void listen() throws IOException {
        NETWORKLOGGER.info("Прослушивание порта: " + ssc.getLocalAddress());
        StringBuilder receivedData = new StringBuilder();
        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                NETWORKLOGGER.log(Level.WARNING, e.getMessage(), e);
            }

            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();


            while (iterator.hasNext()) {

                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    if (sc != null) {
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                        NETWORKLOGGER.config("Клиент присоеденился с адреса: " + sc.getRemoteAddress());
                    }
                }
                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1048576);

                    int bytesRead = sc.read(buffer);
                    if (bytesRead == -1) {
                        sc.close();
                        return;
                    }

                    buffer.flip();
                    receivedData.append(StandardCharsets.UTF_8.decode(buffer).toString());
                    NETWORKLOGGER.finer(receivedData.toString() + isCompleteXML(receivedData.toString()));
                    if (isCompleteXML(receivedData.toString())) {
                        try {
                            NETWORKLOGGER.finer(receivedData.toString());
                            commandHandler.invoke(requestDeserializer.deserialize(receivedData.toString()));
                            receivedData.setLength(0);
                            buffer.clear();
                            Charset charset = StandardCharsets.UTF_8;
                            String fb = commandHandler.getFeedback();
                            NETWORKLOGGER.log(Level.FINE, "Возвращаемое значение: " + fb);

                            sc.write(charset.encode(fb));
                            NETWORKLOGGER.config("Клиент отключился: " + sc.getRemoteAddress());
                            sc.close();
                            key.cancel();
                        } catch (Exception e) {
                            sc.close();
                            key.cancel();
                        }
                    }


                }
            }
        }
    }

    private boolean isCompleteXML(String data) {
        Pattern rootPattern = Pattern.compile("<(\\w+)[^>]*>");
        Matcher matcher = rootPattern.matcher(data);

        if (!matcher.find()) return false;

        String rootTag = matcher.group(1);
        int openCount = countOccurrences(data, "<" + rootTag);
        int closeCount = countOccurrences(data, "</" + rootTag + ">");
        return openCount == closeCount;
    }

    private int countOccurrences(String data, String sub) {
        int count = 0, index = 0;
        while ((index = data.indexOf(sub, index)) != -1) {
            count++;
            index += sub.length();
        }
        return count;
    }
}
