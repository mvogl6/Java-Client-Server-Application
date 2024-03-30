/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *Author: Matthew Vogler
 *This server receives data from TCPClient, writes member details into a text file named "memberlist.txt",
 *and periodically converts the data into Java Object Serialization and saves it into "memberlistObject" file.
 */
import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

public class TCPServer {
    // Server port and file names
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

    // Runnable class to handle client requests
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            synchronized (TCPServer.class) { // Synchronize access to the file
                try (DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                     DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream())) {

                    // Receive member details from client
                    String name = inputStream.readUTF();
                    String address = inputStream.readUTF();
                    String phoneNumber = inputStream.readUTF();

                    // Log received member details
                    System.out.println("Received member details: " + name + ", " + address + ", " + phoneNumber);

                    // Save member details to memberlist.txt (append to existing file)
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBER_LIST_TXT_FILE, true))) {
                        writer.write(name + ", " + address + ", " + phoneNumber + "\n");
                    }

                    // Send feedback to client
                    outputStream.writeUTF("Member details saved successfully.");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Timer task to convert memberlist.txt to memberlistObject
    private static class ConvertFileTask extends TimerTask {
        @Override
        public void run() {
            synchronized (TCPServer.class) { // Synchronize access to the file
                // Convert memberlist.txt to memberlistObject
                try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(MEMBER_LIST_OBJECT_FILE));
                     BufferedReader reader = new BufferedReader(new FileReader(MEMBER_LIST_TXT_FILE))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(", ");
                        Member member = new Member(parts[0], parts[1], parts[2]);
                        objectOutputStream.writeObject(member);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
