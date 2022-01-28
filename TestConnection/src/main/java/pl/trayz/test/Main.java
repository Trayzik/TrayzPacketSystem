package pl.trayz.test;

import pl.trayz.packetsystem.Packet;
import pl.trayz.packetsystem.PacketSystem;
import pl.trayz.packetsystem.Request;
import pl.trayz.test.listeners.TestListener;
import pl.trayz.test.packets.TestPacket;

/**
 * @Author: Fabian 'Trayz'
 * @Created 28.01.2022
 * @Class: Main
 **/


public class Main {

    public static void main(String[] args) {
        PacketSystem.setup("127.0.0.1",33333);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PacketSystem.registerListener(new TestListener("test",TestPacket.class));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PacketSystem.sendPacket("test",new TestPacket("Trayz to kox",1337));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PacketSystem.sendRequestPacket("test", new TestPacket("jebacdisa", 123), 2000, new Request<TestPacket>() {
            @Override
            public void onAnswer(TestPacket packet) {
                System.out.println("odpowiedz "+packet.getJd());
            }

            @Override
            public void onComplete() {
                System.out.println("nie otrzymano odpowiedzi");
            }
        });
    }
}
