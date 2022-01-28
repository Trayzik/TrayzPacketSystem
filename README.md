# TrayzPacketsSystem
Api for sending packets

## How to use

### Initialize server
Method 1:
<br>Add server jar into your dependencies and paste this into your main class:
```java
PacketSystem.startSystem("host",port);
```
Method 2:
```
Run jar with specified arguments (ip port) or without arguments (with default settings)
```
### Initialize client
```java
PacketSystem.setup("host",port);
```


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
