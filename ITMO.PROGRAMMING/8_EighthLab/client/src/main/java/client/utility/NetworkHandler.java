package client.utility;

import client.Main;
import shared.command.RemoteCommand;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class NetworkHandler {
    private final String host;
    private final int port;
    private Socket socket;
    private RequestSerializer serializer;



    public NetworkHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }



    public void openConnection() throws IOException {
        if (socket != null && !socket.isClosed()) {
            return;
        }
        socket = new Socket(host, port);
        serializer = new RequestSerializer(socket.getOutputStream());
        InputStream in = socket.getInputStream();



    }


    public void sendCommand(RemoteCommand cmd) throws IOException {
        ensureConnected();
        serializer.serialize(cmd);
    }


    public String receiveResponse() throws IOException {
        ensureConnected();
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        int len = dis.readInt();


        byte[] buf = new byte[len];
        dis.readFully(buf);

        return new String(buf, StandardCharsets.UTF_8);
    }



    public void closeConnection() throws IOException {

        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }


    private void ensureConnected() throws IOException {
        if (socket == null || socket.isClosed()) {
            openConnection();
        }
    }
}
