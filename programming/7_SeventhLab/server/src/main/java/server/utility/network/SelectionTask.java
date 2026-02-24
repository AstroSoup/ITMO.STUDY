package server.utility.network;

import server.utility.processing.CommandProcessingTask;
import server.utility.response.CommandResponseTask;
import server.utility.response.Response;
import shared.command.UsableCommand;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectionTask implements Runnable {

    private Selector selector;

    private ForkJoinPool fjp = new ForkJoinPool();
    private ExecutorService ctp = Executors.newCachedThreadPool();
    private ExecutorService ftp = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static final Logger selectorLogger = Logger.getLogger(SelectionTask.class.getName());

    private final Queue<SocketChannel> registerQueue = new ConcurrentLinkedQueue<>();
    private final Queue<Runnable> writeQueue = new ConcurrentLinkedQueue<>();

    public SelectionTask(Selector selector) {

        this.selector = selector;
    }
    public void run() {
        try {
            selectorLogger.info("running selection task");
            while (true) {
                selector.select();

                SocketChannel channel;
                while ((channel = registerQueue.poll()) != null) {
                    channel.register(selector, SelectionKey.OP_READ);
                }

                Runnable w;
                while ((w = writeQueue.poll()) != null) {
                    ftp.submit(w);
                }

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();

                while (it.hasNext()) {
                    selectorLogger.info("smth selected here");
                    SelectionKey key = it.next();
                    it.remove();

                    if (key.isReadable()) {
                        selectorLogger.info("smth was read here");
                        StringBuilder receivedData = new StringBuilder();
                        if (Objects.nonNull(key.attachment())) receivedData.append(key.attachment());
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);

                        int bytesRead = client.read(buffer);
                        if (bytesRead == -1) {
                            client.close();
                        }

                        buffer.flip();
                        receivedData.append(StandardCharsets.UTF_8.decode(buffer));

                        if (isCompleteXML(receivedData.toString())) {
                            try {
                                selectorLogger.info(receivedData.toString());
                                CompletableFuture.supplyAsync(() -> {
                                        return new RequestHandleTask(receivedData.toString()).compute();
                                    }, fjp)
                                        .thenApplyAsync(cmd -> {
                                            Response rsp = new CommandProcessingTask(cmd).call();
                                            return rsp;
                                }, ctp).thenAcceptAsync(rsp -> {
                                    SocketChannelWrapper<Response> procRsp = new SocketChannelWrapper<>(client);
                                    procRsp.setAttachment(rsp);
                                    new CommandResponseTask(procRsp).run();
                                        },ftp);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        } else {
                            key.attach(receivedData.toString());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    public void register(SocketChannel channel) {
        selectorLogger.info("registering smth" );
        registerQueue.offer(channel);
        selector.wakeup();
    }

}
