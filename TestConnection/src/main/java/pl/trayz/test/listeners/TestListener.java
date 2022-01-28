package pl.trayz.test.listeners;

import pl.trayz.packetsystem.Listener;
import pl.trayz.packetsystem.Packet;
import pl.trayz.test.packets.TestPacket;

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
    public void onReceive(Packet tt, String replyTo) {
        TestPacket packet = (TestPacket) tt;
        System.out.println("odebrano pakiet! "+packet.getJd());
    }
}
