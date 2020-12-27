# x11-client

x11-client enables java and other jvm languages to talk directly to a x11 
server without binding to a C library. The client is similar to X11lib for C
but uses objects to represent the protocol resulting in a simplified client. It 
supports the core protocol as well as several extensions. The client is
similar to X11lib and follows the same pattern of queuing one-way requests 
before sending them to the server.

![Java CI with Gradle](https://github.com/moaxcp/x11-client/workflows/Java%20CI%20with%20Gradle/badge.svg?branch=master)
[![maven central](https://img.shields.io/maven-central/v/com.github.moaxcp.x11/x11-client)](https://search.maven.org/artifact/com.github.moaxcp.x11/x11-client)
[![javadoc](https://javadoc.io/badge2/com.github.moaxcp.x11/x11-client/javadoc.svg)](https://javadoc.io/doc/com.github.moaxcp.x11/x11-client)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.github.moaxcp.x11%3Ax11-client&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.github.moaxcp.x11%3Ax11-client)

# Users

This library can be added to your project using maven or gradle.

## Maven

```
<dependency>
 <groupId>com.github.moaxcp.x11</groupId>
 <artifactId>x11-client</artifactId>
 <version>0.3.0</version>
 <type>module</type>
</dependency>
```

## Gradle

```
implementation 'com.github.moaxcp.x11:x11-client:0.3.0'
```

The library has one dependency for using unix sockets.

```
implementation 'com.kohlschutter.junixsocket:junixsocket-core:2.3.2'
```

# Usage

Users build requests using the builder pattern.

```
CreateWindow window = CreateWindow.builder()
        .depth(x11Client.getDepth(0))
        .wid(x11Client.nextResourceId())
        .parent(x11Client.getRoot(0))
        .x((short) 10)
        .y((short) 10)
        .width((short) 600)
        .height((short) 480)
        .borderWidth((short) 5)
        .clazz(COPY_FROM_PARENT)
        .visual(x11Client.getVisualId(0))
        .backgroundPixel(x11Client.getWhitePixel(0))
        .borderPixel(x11Client.getBlackPixel(0))
        .eventMaskEnable(EXPOSURE, KEY_PRESS)
        .build();
```

All protocol objects use the builder pattern. All fields using a value_mask 
will automatically set the bit in the value_mask when the builder method is 
called. An example of this is shown above when eventMaskEnable is called.

```
...
        .eventMaskEnable(EXPOSURE, KEY_PRESS)
...
```

This enables the value_mask bit for `eventMask` and turns on the `eventMask` 
bits for EXPOSURE and KEY_PRESS.

A client is a resource and should be managed in a try-with-resources block. The 
try-with-resources block will close the socket connection correctly.

```
try(X11Client client = X11Client.connect(new DisplayName(":1"))) {

}
```

In this block requests can be sent through the client.

```
client.send(window);
```

Creating a window is a one-way request. These requests do not receive a 
response from the server. Similar to X11lib, the client queues one-way requests 
until a two-way request is sent. This queue can be inspected and flushed to the 
server.

```
client.flush();
```

This project converts the x11 protocol to classes which implement certain
interfaces. The client can read and write these objects using the read and 
write methods defined in each object’s class. Here is a diagram of the class 
hierarchy for the protocol:

X11ClientExceptions may be thrown when calling methods on a client. These 
exceptions represent IOExceptions with the socket. X11ErrorExceptions represent 
Errors from the X11 Server. Errors can be handled using the standard try/catch 
method rather than using an error handler callback as is done with X11lib.

```
try {
  client.send(request);
} catch(X11ErrorException e) {
  LOG.error(e);
}
```

All supported x11 extensions are automatically loaded by the client on startup. 
Users can check if an extension is loaded or activated by calling 

```
client.loadedPlugin("BIG-REQUESTS");
client.activePlugin("BIG-REQUESTS");
```

If an extension is supported, then XRequests for that extension can be sent to 
the client.

```
client.send(Enable.builder().build());
```

Otherwise, sending an unsupported request will result in an exception.

```
could not find plugin for request
```

# Examples

These examples are conversions of a X11lib example written in C.

## Hello World

```
try(X11Client x11Client = X11Client.connect()) {
  CreateWindow window = CreateWindow.builder()
    .depth(x11Client.getDepth(0))
    .wid(x11Client.nextResourceId())
    .parent(x11Client.getRoot(0))
    .x((short) 10)
    .y((short) 10)
    .width((short) 600)
    .height((short) 480)
    .borderWidth((short) 5)
    .clazz(WindowClass.COPY_FROM_PARENT)
    .visual(x11Client.getVisualId(0))
    .backgroundPixel(x11Client.getWhitePixel(0))
    .borderPixel(x11Client.getBlackPixel(0))
    .eventMaskEnable(EventMask.EXPOSURE, EventMask.KEY_PRESS)
    .build();
  x11Client.send(window);
  x11Client.send(MapWindow.builder()
    .window(window.getWid())
    .build());
  CreateGC gc = CreateGC.builder()
    .cid(x11Client.nextResourceId())
    .drawable(window.getWid())
    .background(x11Client.getWhitePixel(0))
    .foreground(x11Client.getBlackPixel(0))
    .build();
  x11Client.send(gc);
  while(true) {
    XEvent event = x11Client.getNextEvent();
    if(event instanceof ExposeEvent) {
      List<Rectangle> rectangles = new ArrayList<>();
      rectangles.add(Rectangle.builder()
        .x((short) 20)
        .y((short) 20)
        .width((short) 10)
        .height((short) 10)
        .build());
      x11Client.send(PolyFillRectangle.builder()
        .drawable(window.getWid())
        .gc(gc.getCid())
        .rectangles(rectangles)
        .build());
      x11Client.send(ImageText8.builder()
        .drawable(window.getWid())
        .gc(gc.getCid())
        .stringLen((byte) "Hello World!".length())
        .string(stringToByteList("Hello World!"))
        .x((short) 10)
        .y((short) 50)
        .build());
    } else if(event instanceof KeyPressEvent) {
      break;
    }
  }
}
```

# Design

## Request Prossesing

OneWayRequests are requests which the client expects no response from the 
server. `OneWayRequests are queued and are only sent when the client is flushed. 
There are 3 ways in which the client will be flushed.

1. Manually by calling the `flush()` method
2. sending a `TwoWayRequest`
3. When the event queue is empty and `getNextEvent()` is called

TwoWayRequests are requests where the client expects a response from the 
server. These requests cause the client to flush the `OneWayRequest` queue and 
send the `TwoWayRequest`. Next the client reads input from the server and 
attempts to find the corresponding `XReply` and return it. The protocol object 
read from the server can be a `XEvent` or `XError` rather than an `XReply`. The 
client needs to handle these before it can find and return the `XReply`.When a 
`XEvent` is read it is stored in the event queue.

When an `XError` is read it may be for the current request or any of the 
previous OneWayRequests. Anytime an `XError` is found an exception will be 
thrown.

## Error Handling

NOTE: error handling per request has not been implemented. This could be in the
form of providing an error handler when sending requests.

## Event Prossesing

Events can be sent by the server at any time, so they are stored in an event 
queue when processing TwoWayRequests. This allows the user to receive events by
calling `client.getNextEvent()`.

The event queue is an internal queue containing events which are deferred while 
processing a TwoWayRequest. `client.getNextEvent()` will empty this queue 
before reading events from the server. When the queue is empty getNextEvent() 
will flush the `OneWayRequest` queue and attempt to find and return a `XEvent` 
from the server.

When reading events from the server an error can be read. These errors can only 
be a result of OneWayRequests that are sent after `flush()` is called. These
errors will be thrown as an exception.

Note: Receiving an XReply while processing events should not be expected since 
sending TwoWayRequests clears the stream of replies.

## Concurrency

The client is not thread safe and invocations from one thread must be isolated 
from invocations of another thread. The connection socket, one-way request 
queue, and event queue are shared mutable data. All invocations must be 
synchronized in some way. Protocol objects are immutable, which allows them to 
be shared without synchronization.

# Contributors

I am not an x11 programmer but I find the protocol to be an interesting 
challenge and learning experience. The only other x11 client implementation for 
java that I have found is escher. Escher is very hand written and has many 
issues. The goal of this client is to automate the generation of the protocol 
and make a clear distinction between the client and any framework that may 
provide things like resource management and event dispatch.

This project uses the xcb xmls to generate protocol classes for the core 
protocol and extensions. It uses a custom gradle plugin to generate the 
classes. Be sure to check the javadoc to view supported protocol objects.

All protocol objects support read and write methods regardless of type. This 
means that these objects can also be used to build an x11 server and are not 
tied specifically to the client.

Xlib and XCB provides convenient methods rather than directly using the 
protocol. I have avoided adding convenience methods to the client but may do 
so in the future. Methods such as createSimpleWindow can be added. If you have 
any suggestions on methods that can be added feel free to submit an issue or 
PR.

The core protocol and every supported extension implements a plugin which 
enables the client to figure out which class to use when reading errors and 
events from the server. These plugins are generated durring the build process. 
Plugins are discovered and loaded using the ServiceLoader pattern.

Support is needed for a few things in the protocol before all extensions can 
be supported. Contributions are welcome!

fd – file descriptors. I believe these should work like an int field. If this 
is true this should be easy to implement.

sumOf expressions – creates a sum value which is used to determine list sizes. 
The list is the size of a sumOf function called on another list.

Polymorphism – some objects use a case switch which seems to describe a 
polymorphic object. There is usually a type field which describes the type and 
each switch case provides additional fields for that type. The generation code 
needs to support generating multiple objects when it runs into an object with 
these switch constructs. Reading and writing will be tricky since the type 
field can be deep within the protocol. These switches may also be nested.

# Frameworks

There is a need for higher levels of abstraction such as Window, Pixmap, and 
GraphicsContext. As well as managing the creation and destruction of these 
objects. There is also a need for dispatching events in a consistent way. These 
abstractions will be needed for any application and can be implemented in a 
framework. I would like to consider this the job of a Display class and 
possibly a Toolkit. Currently there is a Display class which manages Resources 
and event dispatch. A framework is not the primary goal of the client project 
and will likely move into a new project.

# versions

## 0.4.0

## 0.3.0

Adding TinyWM example.

Added keySymToKeyCode which can be used to grab keys.

Moving XDisplay methods to main client. Removing XDisplay.

Moved DisplayName, KeySym, KeySystem, ParametersCheck, and XAuthority to protocol package.

Adding ResourceIdService which will switch to XC_MISC extension once ids run out.

Adding AtomService which will cache InternAtoms.

## 0.2.1

Adding github actions build. The workflow will build the library and run a sonar scan.

Adding keysymdef.h support for some of the keys.

## 0.2.0

Fixed issue where the size of a padalign is returned as 0 rather than calculated.

Updating javadoc and adding tests. Refactoring classes for better package encapsulation.

* Each plugin is now in its extension package
* XProtocolService is in the client package and package private
* Utilities, X11InputStream, and X11OutputStream is in the protocol package
* Made ParametersCheck package private
* ConnectionFailureException, X11ClientException, and X11ErrorException constructors are package private
* X11ClientException is now used for IOExceptions thrown while connecting to the x11 server
* X11Connection is now private

## 0.1.0

Initial release of x11-client. The client generates classes for bigreq, composite, damage, dpms, dri2, ge, record, res, 
screensaver, shape, xc_misc, xevie, xf86dri, xf86vidmode, xfixes, xinerama, xproto, xselinux, and xtest.

# License

Copyright 2020 John Mercier

Permission is hereby granted, free of charge, to any 
person obtaining a copy of this software and 
associated documentation files (the "Software"), to 
deal in the Software without restriction, including 
without limitation the rights to use, copy, modify, 
merge, publish, distribute, sublicense, and/or sell 
copies of the Software, and to permit persons to 
whom the Software is furnished to do so, subject to 
the following conditions:

The above copyright notice and this permission notice 
shall be included in all copies or substantial 
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY 
OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND 
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR 
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES 
OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR 
IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
DEALINGS IN THE SOFTWARE.

