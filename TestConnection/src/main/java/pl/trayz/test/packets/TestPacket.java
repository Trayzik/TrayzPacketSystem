package pl.trayz.test.packets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.trayz.packetsystem.Packet;

/**
 * @Author: Fabian 'Trayz'
 * @Created 28.01.2022
 * @Class: TestPacket
 **/

@AllArgsConstructor @Getter
public class TestPacket extends Packet {

    private String jd;
    private int axa;
}
