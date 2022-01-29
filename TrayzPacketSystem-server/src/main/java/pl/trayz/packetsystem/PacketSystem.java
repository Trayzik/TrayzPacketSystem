package pl.trayz.packetsystem;

import pl.trayz.packetsystem.managers.ClientManager;
import pl.trayz.packetsystem.utils.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Trayz
 * @Created 28.01.2022
 **/


public class PacketSystem {

    protected static final ExecutorService SERVER_THREAD = Executors.newScheduledThreadPool(4);

    public static void main(String[] args) {
        int port;
        String hostname;
        try {
            port = args.length != 2 ? 33333 : Integer.parseInt(args[1]);
            hostname = args.length != 2 ? "127.0.0.1" : args[1];
        }catch (NumberFormatException e) {
            Logger.logError("Enter correct port!");
            System.exit(1);
            return;
        }
        if(args.length != 2)
            Logger.logWarning("If you want to use different port than the default, fill startup arguments (ip port)!");
        startSystem(hostname,port);
    }

    public static void startSystem(String host, int port) {
        SERVER_THREAD.submit(() -> {
            ServerSocket server = null;
            try {
                server = new ServerSocket(port, 50, InetAddress.getByName(host));
                Logger.logSuccess("Successfully started packets system on the port " + port + "!");

                while (true) {
                    Socket client = server.accept();
                    Logger.logSuccess("Detected connection from " + client.getInetAddress().getHostAddress());
                    ClientManager.createClient(client);
                }
            } catch (IOException e) {
                try {
                    if (server != null) {
                        server.close();
                    }
                } catch (IOException ignored) {
                }
                Logger.logError("Failed to start packets system!");
            } catch (IllegalArgumentException e) {
                Logger.logError("Incorrect port or hostname!");
            }
        });
    }
}
