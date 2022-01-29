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
            while (socket.isConnected()) {
                int length = in.readInt();
                String msg = in.readUTF();
                if(msg.startsWith("registerListener@")) {
                    registeredChannels.add(msg.replace("registerListener@",""));
                    continue;
                }
                final byte[] bytes = new byte[length];
                in.readFully(bytes);
                final int bufferSize = bytes.length + 4;
                final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bufferSize); final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                dataOutputStream.writeInt(bytes.length);
                dataOutputStream.writeUTF(msg);
                dataOutputStream.write(bytes, 0, bytes.length);
                dataOutputStream.flush();
                for(Client c : ClientManager.getClients().values()) {
                   if(c.equals(this)) {
                       continue;
                   }
                   if(!c.getRegisteredChannels().contains(msg)) {
                       continue;
                   }
                   if(c.socket.isConnected() && !c.socket.isClosed()) {
                       c.out.write(byteArrayOutputStream.toByteArray());
                       c.out.flush();
                   }
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
