package pl.trayz.test.packets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.trayz.packetsystem.Packet;

import java.io.Serializable;

/**
 * @Author: Fabian 'Trayz'
 * @Created 28.01.2022
 * @Class: TestPacket
 **/

@AllArgsConstructor @Getter
public class TestPacket extends Packet implements Serializable {

    private String jd;
    private int axa;
}
