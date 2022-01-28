package pl.trayz.test.listeners;

import pl.trayz.packetsystem.Listener;
import pl.trayz.packetsystem.Packet;
import pl.trayz.packetsystem.PacketSystem;
import pl.trayz.test.packets.TestPacket;

import java.util.UUID;

/**
 * @Author: Fabian 'Trayz'
 * @Created 28.01.2022
 * @Class: TestListener
 **/


public class TestListener extends Listener<TestPacket> {
    public TestListener(String channel, Class<TestPacket> packet) {
        super(channel, packet);
    }

    @Override
    public void onReceive(TestPacket packet, UUID replyTo) {
        System.out.println("odebrano pakiet! "+packet.getJd());
        if (replyTo==null) {
            return;
        }
        TestPacket testPacket = new TestPacket("odpowiedz", 123);
        PacketSystem.sendAnswerPacket(replyTo, testPacket);
        System.out.println("odpowiedziano "+replyTo.toString());
    }
}
