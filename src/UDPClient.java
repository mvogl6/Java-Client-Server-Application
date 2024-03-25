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
import java.util.ArrayList;

public class UDPClient {
    private static final String SERVER_HOST = "localhost"; 
    private static final int SERVER_PORT = 2235; // Port based on student ID

    public static void main(String[] args) {
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName(SERVER_HOST);

            // Send request to server for member list object
            String request = "Request for memberlistObject";
            byte[] sendData = request.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
            clientSocket.send(sendPacket);
            System.out.println("Request sent to server for memberlistObject.");

            // Receive member list from server
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            // Deserialize member list received from server
            ByteArrayInputStream byteStream = new ByteArrayInputStream(receivePacket.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
            ArrayList<Member> members = (ArrayList<Member>) objectInputStream.readObject();
            objectInputStream.close();

            // Display member list received from server
            System.out.println("Received memberlistObject from server:");
            displayMemberList(members);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }