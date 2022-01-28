package pl.trayz.packetsystem;

public abstract class Request<T extends Packet> {

    public abstract void onAnswer(T packet);

    public abstract void onComplete();
}
