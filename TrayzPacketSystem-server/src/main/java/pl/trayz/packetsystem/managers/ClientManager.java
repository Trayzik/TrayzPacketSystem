package pl.trayz.packetsystem.managers;

import lombok.Getter;
import pl.trayz.packetsystem.clients.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Fabian 'Trayz'
 * @Created 28.01.2022
 * @Class: ClientManager
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
            client = new Client(socket,new PrintWriter(socket.getOutputStream(), true),new BufferedReader(new InputStreamReader(socket.getInputStream())),null,new ArrayList<>());
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
