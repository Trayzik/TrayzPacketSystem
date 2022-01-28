package pl.trayz.packetsystem;

import lombok.extern.java.Log;
import pl.trayz.packetsystem.clients.Client;
import pl.trayz.packetsystem.managers.ClientManager;
import pl.trayz.packetsystem.utils.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Fabian 'Trayz'
 * @Created 28.01.2022
 * @Class: PacketSystem
 **/


public class PacketSystem {

    public static void main(String[] args) {
        int port;
        String hostname;
        try {
            port = args.length != 2 ? 33333 : Integer.parseInt(args[1]);
            hostname = args.length != 2 ? "127.0.0.1" : args[1];
        }catch (NumberFormatException e) {
            Logger.logError("Podaj poprawny port!");
            System.exit(1);
            return;
        }
        if(args.length != 2)
            Logger.logWarning("Jeśli chcesz mieć inny port niż defaultowy uzupełnij argumenty startowe (ip port)!");
        startSystem(hostname,port);
    }

    public static void startSystem(String host, int port) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port,50, InetAddress.getByName(host));
            server.setReuseAddress(true);
            Logger.logSuccess("Pomyslnie uruchomiono system pakietów na porcie "+port+"!");

            while (true) {
                Socket client = server.accept();
                Logger.logSuccess("Wykryto połączenie od "+client.getInetAddress().getHostAddress());
                ClientManager.createClient(client);
            }
        }
        catch (IOException e) {
            try {
                assert server != null;
                server.close();
            } catch (IOException ignored) {
            }
            Logger.logError("Nie udalo sie uruchomic systemu pakietów!");
        }
        catch (IllegalArgumentException e) {
            Logger.logError("Niepoprawny port/hostname!");
        }
    }
}
