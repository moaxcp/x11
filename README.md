# x11-client

x11-client enables java and other jvm languages to 
talk directly to an x11 server without binding to a 
C library. The client is similar to Xlib for C but 
uses objects to represent the protocol resulting in 
a simplified client. It supports the core protocol 
as well as several extensions. The client is 
designed with Xlib in mind and follows the same 
pattern of queuing one-way requests before sending 
them to the server.

# Users

This library can be added to your project using 
maven or gradle.

## Maven

```
not yet published
```

## Gradle

```
not yet published
```

The library has one dependency for using unix 
sockets.

# Usage

Requests are built by the user and passed to the 
client.

```
CreateWindowRequest window = CreateWindowRequest.builder()
        .depth(x11Client.getDepth(0))
        .wid(x11Client.nextResourceId())
        .parent(x11Client.getRoot(0))
        .x((short) 10)
        .y((short) 10)
        .width((short) 600)
        .height((short) 480)
        .borderWidth((short) 5)
        .clazz(WindowClassEnum.COPY_FROM_PARENT)
        .visual(x11Client.getVisualId(0))
        .backgroundPixel(x11Client.getWhitePixel(0))
        .borderPixel(x11Client.getBlackPixel(0))
        .eventMaskEnable(EventMaskEnum.EXPOSURE)
        .eventMaskEnable(EventMaskEnum.KEY_PRESS)
        .build();
```

The builder pattern is used for all protocol 
objects. Value masks are not exposed through the 
builders. Value mask bits are set as needed when 
setting values in the builder. An example of this is 
shown above where the event masks are enabled.

```
...
.eventMaskEnable(EventMaskEnum.EXPOSURE)
.eventMaskEnable(EventMaskEnum.KEY_PRESS)
...
```

A client is a resource and should be managed in a 
try-with-resources block. This will ensure the 
socket connection is closed correctly.

```
try(X11Client client = X11Client.connect(new DisplayName(":1"))) {

}
```

In this block requests can be sent through the client.

```
client.send(window);
```

Creating a window is a one-way request. These 
requests do not recieve a resonse from the server.
Similar to Xlib, the client queues one-way requests 
until a two-way request is sent. This queue can be 
inspected and flushed to the server.

```
client.flush();
```

The x11 protocol is represented by classes which 
implement certain interfaces. The client can read 
and write these objects using the read and write 
methods defined in each object’s class. Here is a 
diagram of the class hierarchy for the protocol:

X11ClientExceptions may be thrown when calling 
methods on a client. These exceptions represent 
IOExceptions with the socket. X11ErrorExceptions 
represent Errors from the X11 Server. Errors can be 
handled using the standard try/catch method rather 
than using an error handler callback as is done with 
Xlib.

```
try {
  client.send(request);
} catch(X11ErrorException e) {
  LOG.error(e);
}
```

All supported x11 extensions are automatically 
loaded by the client on startup. Users can check if 
an extension is loaded by calling 

```

```

If an extension is supported, then XRequests for 
that extension can be sent to the client.

```

```

Otherwise, sending an unsupported request will 
result in an exception.

```

```

