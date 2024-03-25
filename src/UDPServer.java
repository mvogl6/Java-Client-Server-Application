/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Matthew
 */
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class UDPServer {
    private static final int SERVER_PORT = 2235; // Port based on my student ID
    private static final String MEMBER_LIST_OBJECT_FILE = "memberlistObject";

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(SERVER_PORT)) {
            System.out.println("Server started. Listening on port " + SERVER_PORT);

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // Read member list from memberlistObject file
                ArrayList<Member> members = readMemberList();

                // Serialize member list
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream)) {
                    objectOutputStream.writeObject(members);
                    objectOutputStream.flush();
                    byte[] sendData = byteStream.toByteArray();

                    // Send serialized member list to client
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                    socket.send(sendPacket);

                    System.out.println("Sending memberlistObject.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }static final String MEMBER_LIST_OBJECT_FILE = "memberlistObject";