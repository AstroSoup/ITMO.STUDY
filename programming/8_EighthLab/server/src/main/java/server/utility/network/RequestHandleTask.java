package server.utility.network;

import shared.command.UsableCommand;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.RecursiveTask;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHandleTask extends RecursiveTask<UsableCommand> {


    private final String data;
    final private Logger RHTLOGGER = Logger.getLogger("server.utility.network.RequestHandleTask");


    public RequestHandleTask(String data) {
        this.data = data;

    }



    protected UsableCommand compute() {
        try {
            RHTLOGGER.info("running RHT");
            UsableCommand c = new RequestDeserializer().deserialize(data);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}
