package chat;

import lombok.extern.log4j.Log4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: Claudiu-Damian Panzaru
 * @date: 5/20/2020
 * @description:
 */

@Log4j
public class ChatWriter extends Thread {
    private BufferedReader reader;
    private PrintWriter writer;

    public ChatWriter(PrintWriter writer, BufferedReader reader) {
        this.writer = writer;
        this.reader = reader;
    }

    public void run() {
        String chatText;
        try {
            while (true) {
                chatText = reader.readLine();
                if (chatText != null) {
                    log.info("Message to send: ");
                    writer.println(chatText);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            log.error("Failed to read the stream: " + e.getMessage());
        }
    }
}
