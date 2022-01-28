package pl.trayz.test;

import pl.trayz.packetsystem.PacketSystem;

/**
 * @Author: Fabian 'Trayz'
 * @Created 28.01.2022
 * @Class: Main
 **/


public class Main {

    public static void main(String[] args) {
        PacketSystem.setup("127.0.0.1",33333);
    }
}
