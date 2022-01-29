package pl.trayz.packetsystem;

import lombok.Getter;
import lombok.Setter;
import org.nustaq.serialization.FSTConfiguration;
import pl.trayz.packetsystem.enums.LoggerType;
import pl.trayz.packetsystem.utils.Logger;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Author: Fabian 'Trayz'
 * @Created 28.01.2022
 * @Class: PacketSystem
 **/

public class PacketSystem {

    protected static final ExecutorService service = Executors.newScheduledThreadPool(4);
    private static final ScheduledExecutorService PACKETS_THREAD = Executors.newScheduledThreadPool(4);
    protected static final FSTConfiguration FST_CONFIG = FSTConfiguration.createDefaultConfiguration();
    private static DataOutputStream out;
    private static DataInputStream in;
    private static final ConcurrentHashMap<String, Listener> listeners = new ConcurrentHashMap<>();
    @Getter @Setter
    protected static Socket socket;
    @Getter @Setter
    private static boolean autoReconnect;

    public static void setup(String hostname, int port) {
        service.submit(() -> {
        try (Socket socket = new Socket(hostname, port)) {
            Logger.logSuccess("Successfully connected with the packets system!");
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            if(listeners.size() != 0) {
                for(Listener listener : listeners.values()) {
                    registerListener(listener);
                }
            }
            setSocket(socket);

            while (socket.isConnected()) {
                int length = in.readInt();
                byte[] message = new byte[length];
                String channel = in.readUTF();
                in.readFully(message, 0, message.length);
                Packet receivedPacket = (Packet) FST_CONFIG.asObject(message);
                if (channel.equals("requests")) {
                    if (receivedPacket.getUuid()!=null && awaitingRequests.containsKey(receivedPacket.getUuid())) {
                        awaitingRequests.get(receivedPacket.getUuid()).onAnswer(receivedPacket);
                        awaitingRequests.remove(receivedPacket.getUuid());
                    }
                }
                if(listeners.containsKey(channel)) {
                    listeners.get(channel).onReceive(receivedPacket, receivedPacket.getUuid());
                }
            }
        }
        catch (IOException ignored) {}
        finally {
            setSocket(null);
            Logger.logError("Connection with packets system has been lost!");
            if(isAutoReconnect()) {
                Logger.logSuccess("Reconnecting...");
                setup(hostname,port);
            }
        }
        });
    }

    public static void sendPacket(String channel, Packet packet) {
            if (socket == null || !socket.isConnected() || out == null) {
                PACKETS_THREAD.schedule(() -> {
                    sendPacket(channel,packet);
                },100L, TimeUnit.MILLISECONDS);
                return;
            }
        PACKETS_THREAD.submit(()-> {
                try {
                    byte[] message = FST_CONFIG.asByteArray(packet);
                    try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream)) {
                        dataOutputStream.writeInt(message.length);
                        dataOutputStream.writeUTF(channel);
                        dataOutputStream.write(message);
                        dataOutputStream.flush();

                        out.write(byteArrayOutputStream.toByteArray());
                        out.flush();
                    }
                } catch (IOException e) {
                    Logger.logError("Failed to send packet!");
                }
            });
    }

    private static final ConcurrentHashMap<UUID, Request> awaitingRequests = new ConcurrentHashMap<>();

    public static void sendAnswerPacket(UUID replyTo, Packet packet) {
        packet.setUuid(replyTo);
        sendPacket("requests", packet);
    }

    public static <T extends Packet> void sendRequestPacket(String channel, Packet packet, int duration, Request<T> request) {
            UUID requestUUID = UUID.randomUUID();
            packet.setUuid(requestUUID);
            sendPacket(channel, packet);
            awaitingRequests.put(requestUUID, request);
        PACKETS_THREAD.schedule(() -> {
            if (awaitingRequests.containsKey(requestUUID)) {
                awaitingRequests.remove(requestUUID);
                request.onComplete();
            }
        },duration, TimeUnit.MILLISECONDS);
    };

    public static <T extends Packet> void registerListener(Listener<T> listener) {
        if (socket == null || !socket.isConnected() || out == null) {
            PACKETS_THREAD.schedule(() -> {
                registerListener(listener);
            },100L, TimeUnit.MILLISECONDS);
            return;
        }
        PACKETS_THREAD.submit(()-> {
            listeners.put(listener.getChannel(), listener);
            try {
                out.writeInt(1);
                out.writeUTF("registerListener@" + listener.getChannel());
                out.flush();
                Logger.logSuccess("Successfully registered listener " + listener.getChannel() + "!");
            } catch (IOException e) {
                Logger.logError("Failed to register listener!");
            }
        });
    }


    public static void setLogger(LoggerType loggerType, boolean status) {
        switch (loggerType){
            case ERROR:
                Logger.setLogError(status);
                break;
            case SUCCESS:
                Logger.setLogSuccess(status);
                break;
        }
    }

}
