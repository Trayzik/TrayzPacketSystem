<p align="center">
  <img src="https://img.shields.io/badge/Made%20with-Java-1f425f.svg?style=flat-square"/>
  <img src="https://img.shields.io/badge/License-GNU-blue.svg?style=flat-square"/>
</p>

# TrayzPacketsSystem
This packets system allows you to easily and quickly send packets between clients. You can easily send and intercept packets. This system also supports request packets. 

## Setup 🚀

### Using Maven
TrayzPakcetsSystem-client is available on maven.

#### Repository
```xml
<repository>
  <id>jitpack.io</id>
  <url>https://jitpack.io</url>
</repository>
```
#### Dependency
```xml
<dependency>
  <groupId>com.github.Trayzik.TrayzPacketSystem</groupId>
  <artifactId>TrayzPacketSystem-client</artifactId>
  <version>1.2-SNAPSHOT</version>
</dependency>
```

## Usage ⚒

### Initialize server
- Run jar with specified arguments (ip port) or without arguments (with default port 33333)

### Initialize client
```java
PacketSystem.setup("host",port);
```

## Examples 📜

### Example Packet
You can easily add your own variables and getter/setter methods to your packet
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
You can easily send the packet from any method.
```java
PacketSystem.sendPacket("exampleChannel", new ExamplePacket("exampleString", false));
```

### Example Listener
Listener for data capture.
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
You can easily register your listener at any time.
```java
PacketSystem.registerListener(new ExampleListener("exampleChannel", ExamplePacket.class));
```

### Example callback packet
Request packet with which you can send request and capture data in one method.
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
Listener with which you can reply to the request packet.
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
## Addons➕

- You can disable logs using method setLogger in class PacketSystem <br>
- You can enable autoreconnect using method setAutoReconnect in class PacketSystem

## License❤️

Copyright © 2022 [Trayz](https://github.com/Trayzik).<br /> 
This project is [GNU](https://github.com/Trayzik/TrayzPacketSystem/blob/master/LICENSE) licensed.
