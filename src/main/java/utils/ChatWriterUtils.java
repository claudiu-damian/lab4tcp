package utils;

import chat.ChatWriter;
import lombok.extern.log4j.Log4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author: Claudiu-Damian Panzaru
 * @date: 5/21/2020
 * @description:
 */

@Log4j
public class ChatWriterUtils {
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static BufferedReader startChat(Socket socket) {
        BufferedReader bufferedReader = getBufferedReader(socket);
        PrintWriter outputStreamWriter = getPrintWriter(socket);

        Thread chatWriter = new ChatWriter(outputStreamWriter, reader);
        chatWriter.start();
        return bufferedReader;
    }

    private static PrintWriter getPrintWriter(Socket socket) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            log.error("Cannot get the PrinterWriter: " + e.getMessage());
        }
        return printWriter;
    }

    private static BufferedReader getBufferedReader(Socket socket) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            log.error("Cannot get the BufferedReader: " + e.getMessage());
        }
        return bufferedReader;
    }
}
