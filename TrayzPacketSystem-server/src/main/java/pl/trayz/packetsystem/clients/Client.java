package pl.trayz.packetsystem.clients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.trayz.packetsystem.managers.ClientManager;
import pl.trayz.packetsystem.utils.Logger;

import java.io.*;
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
    private DataOutputStream out;
    private DataInputStream in;
    private String lastMessage;
    private List<String> registeredChannels;

    @Override
    public void run() {
        try {
            int length;
            String msg = "";
            while ((length = in.readInt()) > 0) {
                msg = in.readUTF();
                if(msg.startsWith("registerListener@")) {
                    registeredChannels.add(msg.replace("registerListener@",""));
                    continue;
                }
                byte[] message = new byte[length];
                in.readFully(message, 0, message.length);
                for(Client c : ClientManager.getClients().values()) {
                   if(c.equals(this)) {
                       continue;
                   }
                   c.getOut().writeInt(length);
                   c.getOut().writeUTF(msg);
                   c.getOut().write(message);
                   c.getOut().flush();
                }
            }
        } catch (IOException ignored) {}
        finally {
            try {
                out.close();
                in.close();
                socket.close();
                Logger.logWarning("Connection lost with user "+socket.getInetAddress().getHostAddress());
                ClientManager.removeClient(socket);
            }
            catch (IOException ignored) {}
        }
    }
}
