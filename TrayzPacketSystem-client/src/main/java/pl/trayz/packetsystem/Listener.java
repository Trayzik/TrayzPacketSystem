package pl.trayz.packetsystem;

/**
 * @Author: Fabian 'Trayz'
 * @Created 28.01.2022
 * @Class: Listener
 **/


public abstract class Listener <T extends Packet> {

    private final String channel;
    private final Class<T> packet;

    public Listener(String channel, Class<T> packet) {
        this.channel = channel;
        this.packet = packet;
    }

    public abstract void onReceive(T packet, String replyTo);

    public Class<T> getPacket() {
        return packet;
    }

    public String getChannel() {
        return channel;
    }
}