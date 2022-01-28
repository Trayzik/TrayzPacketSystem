package pl.trayz.packetsystem;

import pl.trayz.packetsystem.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Fabian 'Trayz'
 * @Created 28.01.2022
 * @Class: PacketSystem
 **/


public class PacketSystem {

    protected static final ExecutorService service = Executors.newSingleThreadExecutor();
    protected static String lastMessage;

    public static void setup(String hostname, int port) {
        service.submit(() -> {
        try (Socket socket = new Socket(hostname, port)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Logger.logSuccess("Polaczono z systemem pakietow!");
            while ((lastMessage = in.readLine()) != null) {
                System.out.println(lastMessage);
            }
        }
        catch (IOException e) {
            Logger.logError("Nie udalo sie polaczyc z systemem pakietow!");
        }
        finally {
            Logger.logError("Utracono połączenie z serwere ");
        }
        });
    }
}
