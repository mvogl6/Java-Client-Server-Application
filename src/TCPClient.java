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

public class TCPClient {
    // Server address and port
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1135; // Port based on my student ID

    public static void main(String[] args) {
        try (
            // Establishing a socket connection to the server
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            // Setting up input and output streams for communication
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String name, address, phoneNumber;

            // Prompt user for member details
            System.out.println("Welcome to the Membership Registration System");
            System.out.println("Please enter the following details:");
            
            // Prompt user for Name
            System.out.print("Name: ");
            name = reader.readLine();
            // Validate Name input
            while (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please enter again:");
                name = reader.readLine();
            }
            
            // Prompt user for address
            System.out.print("Address: ");
            address = reader.readLine();
            // Validate Address input
            while (address.isEmpty()) {
                System.out.println("Address cannot be empty. Please enter again:");
                address = reader.readLine();
            }

            // Prompt user for phone number
            do {
                System.out.print("Phone Number: ");
                phoneNumber = reader.readLine();
                if (phoneNumber.isEmpty()) {
                    System.out.println("Phone number cannot be empty. Please enter again:");
                }
            } while (phoneNumber.isEmpty());

            // Send member details to server
            outputStream.writeUTF(name);
            outputStream.writeUTF(address);
            outputStream.writeUTF(phoneNumber);

            // Receive feedback from server
            String feedback = inputStream.readUTF();
            System.out.println(feedback);

            System.out.println("Thank you for using the Membership Registration System");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
