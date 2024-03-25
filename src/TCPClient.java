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
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1135; // Port based on student ID

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            String name, address, phoneNumber;

            // Prompt user for member details
            System.out.println("Welcome to the Membership Registration System");
            System.out.println("Please enter the following details:");

            System.out.print("Name: ");
            name = reader.readLine();
            while (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please enter again:");
                name = reader.readLine();
            }

            System.out.print("Address: ");
            address = reader.readLine();
            while (address.isEmpty()) {
                System.out.println("Address cannot be empty. Please enter again:");
                address = reader.readLine();
            }
    System.out.print("Phone Number: ");
            address = reader.readLine();
            while (address.isEmpty()) {
                System.out.println("Address cannot be empty. Please enter again:");
                address = reader.readLine();
            }
            // Prompt user for phone number
            System.out.print("Phone Number: ");
            phoneNumber = reader.readLine();

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
