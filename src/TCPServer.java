/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Matthew Vogler
 */
import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

public class TCPServer {
    private static final int SERVER_PORT = 1135; // Port based on my student ID
    private static final String MEMBER_LIST_TXT_FILE = "memberlist.txt";
    private static final String MEMBER_LIST_OBJECT_FILE = "memberlistObject";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server started. Listening on port " + SERVER_PORT);

            // Start the timer to convert memberlist.txt to memberlistObject every 2 seconds
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new ConvertFileTask(), 0, 2000);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                // Handle client request in a separate thread
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
