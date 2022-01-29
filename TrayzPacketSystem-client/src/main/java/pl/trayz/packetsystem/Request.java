package pl.trayz.packetsystem;

/**
 * @Author: Trayz
 * @Created 28.01.2022
 **/

public abstract class Request<T extends Packet> {

    public abstract void onAnswer(T packet);

    public abstract void onComplete();
}
