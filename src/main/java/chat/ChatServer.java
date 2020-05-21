package chat;

import lombok.extern.log4j.Log4j;
import utils.ChatWriterUtils;
import utils.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: Claudiu-Damian Panzaru
 * @date: 5/20/2020
 * @description:
 */

@Log4j
public class ChatServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = getServerSocket();
        log.info("Waiting for client to connect\n");
        Socket socket = getSocket(serverSocket);
        log.info("Connection with client established\n");

        BufferedReader bufferedReader = ChatWriterUtils.startChat(socket);
        readFromClient(serverSocket, socket, bufferedReader);
    }

    private static void readFromClient(ServerSocket serverSocket, Socket socket, BufferedReader bufferedReader) {
        try {
            String chatMessage;
            while ((chatMessage = bufferedReader.readLine()) != null) {
                log.info("Client message: " + chatMessage + "\n");
                if (chatMessage.equals("EXIT")) {
                    log.info("Client has disconnected\n");
                    socket.close();
                    serverSocket.close();
                    break;
                }
            }
        } catch (IOException e) {
            log.error("Failed to read: " + e.getMessage());
        }
    }

    private static Socket getSocket(ServerSocket serverSocket) {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            log.error("Cannot get the Socket: " + e.getMessage());
        }
        return socket;
    }

    private static ServerSocket getServerSocket() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Constants.PORT_NUMBER);
        } catch (IOException e) {
            log.error("Cannot get the Server Socket: " + e.getMessage());
        }
        return serverSocket;
    }
}
