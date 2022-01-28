package pl.trayz.packetsystem;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * @Author: Fabian 'Trayz'
 * @Created 28.01.2022
 * @Class: Packet
 **/

@Getter
@Setter
public class Packet implements Serializable {

    private UUID uuid;
}
