<p align="center">
    <strong>Packets system.</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/license-GNU-yellow.svg?style=for-the-badge" />
</p>

## How to use

### Initialize server
Ps. default port is 33333
<br>Method 1:
<br>Add server jar into your dependencies and paste this into your main class:
```java
PacketSystem.startSystem("host",port);
```
Method 2:
```
Run jar with specified arguments (ip port) or without arguments (with default settings)
```
```
java -cp TrayzPacketSystem-server.jar pl.trayz.packetsystem.PacketSystem
```
### Initialize client
```java
PacketSystem.setup("host",port);
```

## Examples:

### Example Packet
you can easily add your own variables and getter/setter methods to your packet
```java
public class ExamplePacket extends Packet {
    
    private String myString;
    private boolean myBoolean;
    
    public ExamplePacket(String myString, boolean myBoolean) {
        this.myString=myString;
        this.myBoolean=myBoolean;
    }

    public boolean isMyBoolean() {
        return myBoolean;
    }

    public String getMyString() {
        return myString;
    }
}
```

### Send Packet
```java
PacketSystem.sendPacket("exampleChannel", new ExamplePacket("exampleString", false));
```

### Example Listener
```java
public class ExampleListener extends Listener<ExamplePacket> {
    
    public ExampleListener(String channel, Class<ExamplePacket> packet) {
        super(channel, packet);
    }

    @Override
    public void onReceive(ExamplePacket packet, UUID replyTo) {
        System.out.println("Received example packet with parameters "+packet.getMyString()+" "+packet.isMyBoolean());
    }
}
```

### Register listener
```java
PacketSystem.registerListener(new ExampleListener("exampleChannel", ExamplePacket.class));
```

### Example callback packet
```java
PacketSystem.sendRequestPacket("exampleChannel", new ExamplePacket("exampleString", false), 2, new Request<ExamplePacket>() {
    @Override
    public void onAnswer(ExamplePacket packet) {
        System.out.println(packet.getMyString());
        System.out.println(packet.isMyBoolean());
    }
        
    @Override
    public void onComplete() {
        System.out.println("No reply was received");
    }
});
```

### Reply to request
```java
public class ExampleListener extends Listener<ExamplePacket> {
    
    public ExampleListener(String channel, Class<ExamplePacket> packet) {
        super(channel, packet);
    }

    @Override
    public void onReceive(ExamplePacket packet, UUID replyTo) {
        if (replyTo==null) return;
        PacketSystem.sendAnswerPacket(replyTo, new ExamplePacket("exampleString", false));
    }
}
```
### Addons
```You can disable logs using method setLogger in class PacketSystem```<br>
```You can enable autoreconnect using method setAutoReconnect in class PacketSystem```
## License

Copyright © 2021 [Trayz](https://github.com/Trayzik).<br />
This project is [GNU](https://github.com/Trayzik/TrayzPacketSystem/blob/master/LICENSE) licensed.
