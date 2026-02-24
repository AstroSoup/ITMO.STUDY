package client.utility;

import shared.command.RemoteCommand;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class NetworkHandler {

    private String host;
    private int port;

    public NetworkHandler(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void communicate(ClientCommandHandler cch, RemoteCommand cmd) throws IOException {

        try(Socket socket = new Socket(host, port);) {
            socket.setSoTimeout(5000);

            RequestSerializer rq = new RequestSerializer(socket.getOutputStream());
            rq.serialize(cmd);


            InputStream input = socket.getInputStream();
            Scanner sc = new Scanner(new InputStreamReader(input));
            StringBuilder response = new StringBuilder();
            while (sc.hasNextLine()) {
                response.append(sc.nextLine()).append("\n");
            }
            cch.setFeedback(response.toString());
        }

    }
}
