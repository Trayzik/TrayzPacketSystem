package pl.trayz.packetsystem.clients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.trayz.packetsystem.managers.ClientManager;
import pl.trayz.packetsystem.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * @Author: Fabian 'Trayz'
 * @Created 28.01.2022
 * @Class: Client
 **/


@AllArgsConstructor @Getter @Setter
public class Client implements Runnable{

    private final Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String lastMessage;
    private List<String> registeredListeners;

    @Override
    public void run() {
        try {
            while ((lastMessage = in.readLine()) != null) {
                System.out.println(lastMessage);
            }
        } catch (IOException ignored) {}
        finally {
            try {
                out.close();
                in.close();
                socket.close();
                Logger.logWarning("Utracono połączenie z użytkownikiem "+socket.getInetAddress().getHostAddress());
                ClientManager.removeClient(socket);
            }
            catch (IOException ignored) {}
        }
    }
}
