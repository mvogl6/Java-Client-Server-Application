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
