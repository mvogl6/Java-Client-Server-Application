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
    // Port number for the server
    private static final int SERVER_PORT = 2235; // Port based on my student ID
    // File name for storing member list objects
    private static final String MEMBER_LIST_OBJECT_FILE = "memberlistObject";

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(SERVER_PORT)) {
            System.out.println("Server started. Listening on port " + SERVER_PORT);

            while (true) {
                // Receive request packet from client
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                // Extract client address and port
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
    }

    // Method to read member list from file
    private static ArrayList<Member> readMemberList() {
        ArrayList<Member> members = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(MEMBER_LIST_OBJECT_FILE))) {
            // Read objects from file until EOF
            while (true) {
                Member member = (Member) objectInputStream.readObject();
                members.add(member);
            }
        } catch (EOFException e) {
            // Reached end of file
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return members;
    }
}
