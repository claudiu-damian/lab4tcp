package chat;

import lombok.extern.log4j.Log4j;
import utils.ChatWriterUtils;
import utils.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

/**
 * @author: Claudiu-Damian Panzaru
 * @date: 5/20/2020
 * @description:
 */

@Log4j
public class ChatClient {
    public static void main(String[] args) {
        log.info("Enter server address: ");
        String address = getAddress();
        Socket socket = getSocket(address);
        BufferedReader reader = ChatWriterUtils.startChat(socket);

        log.info("Connection established\n");
        readClient(socket, reader);
    }

    private static void readClient(Socket socket, BufferedReader reader) {
        try {
            String chatText;
            while ((chatText = reader.readLine()) != null) {
                if (chatText.equals("EXIT")) {
                    socket.close();
                    break;
                }
            }
        } catch (IOException e) {
            log.error("Problems with client's stream: " + e.getMessage());
        }
    }

    private static String getAddress() {
        String address = "";
        try {
            address = ChatWriterUtils.reader.readLine();
        } catch (IOException e) {
            log.error("Cannot get the client's address: " + e.getMessage());
        }
        return address;
    }

    private static Socket getSocket(String address) {
        Socket socket = null;
        try {
            socket = new Socket(address, Constants.PORT_NUMBER);
        } catch (IOException e) {
            log.error("Cannot get the Client Socket: " + e.getMessage());
        }
        return socket;
    }
}
