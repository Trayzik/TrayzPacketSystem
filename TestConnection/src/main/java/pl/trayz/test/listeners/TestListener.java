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
    public void onReceive(TestPacket packet, String replyTo) {
        System.out.println("odebrano pakiet! "+packet.getJd()+" id: "+packet.getAxa());
    }
}
