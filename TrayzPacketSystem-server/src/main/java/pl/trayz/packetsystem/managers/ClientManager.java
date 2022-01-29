package pl.trayz.packetsystem.managers;

import lombok.Getter;
import pl.trayz.packetsystem.clients.Client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Trayz
 * @Created 28.01.2022
 **/


public class ClientManager {

    @Getter
    private static final ConcurrentHashMap<Socket, Client> clients = new ConcurrentHashMap<>();

    public static Client getClient(Socket socket) {
        return clients.getOrDefault(socket,null);
    }

    public static void createClient(Socket socket) {
        Client client;
        try {
            client = new Client(socket,new DataOutputStream(socket.getOutputStream()),new DataInputStream(socket.getInputStream()),null,new ArrayList<>());
            client.getRegisteredChannels().add("requests");
        } catch (IOException e) {
            return;
        }
        clients.put(socket,client);
        new Thread(client).start();
    }

    public static void removeClient(Socket socket) {
        clients.remove(socket);
    }
}
