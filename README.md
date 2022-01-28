# yoczusNatsPacketsSystem
Api for sending packets based on jnats

## How to use

### Initialize manager
```java
NatsManager.setup("localhost", 4222, "username", "password");
```


### Example Packet
you can easily add your own variables and getter/setter methods to your packet
```java
public class ExamplePacket extends NatsPacket {
    
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
