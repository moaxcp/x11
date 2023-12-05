# x11-client

x11-client enables java and other jvm languages to talk directly to a x11 
server without binding to a C library. The client is similar to X11lib for C
but uses objects to represent the protocol resulting in a simplified client. It 
supports the core protocol and all extensions. The 
client is similar to X11lib and follows the same pattern of queuing one-way 
requests before sending them to the server.

[![Java CI with Gradle](https://github.com/moaxcp/x11-client/workflows/Java%20CI%20with%20Gradle/badge.svg?branch=master)](https://github.com/moaxcp/x11-client/actions?query=workflow%3A%22Java+CI+with+Gradle%22)
[![maven central](https://img.shields.io/maven-central/v/com.github.moaxcp.x11/x11-client)](https://search.maven.org/artifact/com.github.moaxcp.x11/x11-client)
[![javadoc](https://javadoc.io/badge2/com.github.moaxcp.x11/x11-client/javadoc.svg)](https://javadoc.io/doc/com.github.moaxcp.x11/x11-client)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=moaxcp_x11-client&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=moaxcp_x11-client)

# Users

This library can be added to your project using maven or gradle.

## Maven

```
<dependency>
 <groupId>com.github.moaxcp.x11</groupId>
 <artifactId>x11-client</artifactId>
 <version>0.12.0</version>
 <type>module</type>
</dependency>
```

## Gradle

```
implementation 'com.github.moaxcp.x11:x11-client:0.12.0'
```

The library has one dependency for using unix sockets.

```
implementation 'com.kohlschutter.junixsocket:junixsocket-core:x.x.x'
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

This is an example of a simple window. In this example none of the helper 
methods are used. Raw requests are built and sent directly to the server.

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

The next example is the same window but uses helper methods in the client.

```
try(X11Client client = X11Client.connect()) {
  int wid = client.createSimpleWindow((short) 10, (short) 10, (short) 600, (short) 480, EventMask.EXPOSURE, EventMask.KEY_PRESS);
  client.storeName(wid, "Hello World!");
  int deleteAtom = client.getAtom("WM_DELETE_WINDOW");
  client.setWMProtocols(wid, deleteAtom);
  client.mapWindow(wid);
  int gc = client.createGC(0, wid);
  while(true) {
    XEvent event = client.getNextEvent();
    if(event instanceof ExposeEvent) {
      client.fillRectangle(wid, gc, (short) 20, (short) 20, (short) 10, (short) 10);
      client.drawString(wid, gc, (short) 10, (short) 50, "Hello World!");
    } else if(event instanceof KeyPressEvent) {
      break;
    } else if(event instanceof ClientMessageEvent) {
      ClientMessageEvent clientMessage = (ClientMessageEvent) event;
      if(clientMessage.getFormat() == 32) {
        ClientMessageData32 data = (ClientMessageData32) clientMessage.getData();
        if(data.getData32().get(0) == deleteAtom) {
          break;
        }
      }
    }
  }
}
```

## TinyWM

[TinyWM](http://incise.org/tinywm.html) is a famous small window manager 
written in around 50 lines of code. This example is the implementation in java.

```
try(X11Client client = X11Client.connect(new DisplayName(":1"))) {
  int wid = client.createSimpleWindow(10, 10, 200, 200);
  client.mapWindow(wid);
  client.send(GrabKey.builder()
    .key((byte) client.keySymToKeyCode(KeySym.getByName("F1").get().getValue()))
    .modifiersEnable(ModMask.ONE)
    .grabWindow(client.getRoot(0))
    .ownerEvents(true)
    .keyboardMode(GrabMode.ASYNC)
    .pointerMode(GrabMode.ASYNC)
    .build());
  client.send(GrabButton.builder()
    .button(ButtonIndex.ONE)
    .modifiersEnable(ModMask.ONE)
    .grabWindow(client.getRoot(0))
    .ownerEvents(true)
    .eventMaskEnable(BUTTON_PRESS, BUTTON_RELEASE, POINTER_MOTION)
    .keyboardMode(GrabMode.ASYNC)
    .pointerMode(GrabMode.ASYNC)
    .build());
  client.send(GrabButton.builder()
    .button(ButtonIndex.THREE)
    .modifiersEnable(ModMask.ONE)
    .grabWindow(client.getRoot(0))
    .ownerEvents(true)
    .eventMaskEnable(BUTTON_PRESS, BUTTON_RELEASE, POINTER_MOTION)
    .keyboardMode(GrabMode.ASYNC)
    .pointerMode(GrabMode.ASYNC)
    .build());

  GetGeometryReply geometry = null;
  ButtonPressEvent start = null;

  while(true) {
    XEvent event = client.getNextEvent();
    if(event instanceof KeyPressEvent) {
      KeyPressEvent keyPress = (KeyPressEvent) event;
      int child = keyPress.getChild();
      if(child != Window.NONE.getValue()) {
        client.raiseWindow(child);
      }
    } else if(event instanceof ButtonPressEvent) {
      ButtonPressEvent buttonPress = (ButtonPressEvent) event;
      int child = buttonPress.getChild();
      if(child != Window.NONE.getValue()) {
        geometry = client.send(GetGeometry.builder()
          .drawable(child)
          .build());
        start = buttonPress;
      }
    } else if(event instanceof MotionNotifyEvent) {
      MotionNotifyEvent motionNotify = (MotionNotifyEvent) event;
      int child = motionNotify.getChild();
      if(child != Window.NONE.getValue()) {
        int xdiff = motionNotify.getRootX() - start.getRootX();
        int ydiff = motionNotify.getRootY() - start.getRootY();
        client.send(ConfigureWindow.builder()
          .window(child)
          .x(geometry.getX() + (start.getDetail() == ButtonIndex.ONE.getValue() ? xdiff : 0))
          .y(geometry.getY() + (start.getDetail() == ButtonIndex.ONE.getValue() ? ydiff : 0))
          .width(Math.max(1, geometry.getWidth() + (start.getDetail() == ButtonIndex.THREE.getValue() ? xdiff : 0)))
          .height(Math.max(1, geometry.getHeight() + (start.getDetail() == ButtonIndex.THREE.getValue() ? ydiff : 0)))
          .build());
      }
    } else if(event instanceof ButtonReleaseEvent) {
      start = null;
    }
  }
}
```

The java version is a bit longer due to the builder pattern being a little more
verbose.

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

# Other X11 Clients

https://github.com/psychon/x11rb

https://github.com/sidorares/node-x11

https://github.com/BurntSushi/xgb

# Learning x11

https://cgit.freedesktop.org/xorg/proto/x11proto/plain/XF86keysym.h

https://tronche.com/gui/x/xlib/input/keyboard-encoding.html

https://jichu4n.com/posts/how-x-window-managers-work-and-how-to-write-one-part-i/

https://www.geeks3d.com/20120102/programming-tutorial-simple-x11-x-window-code-sample-for-linux-and-mac-os-x/

https://wiki.tcl-lang.org/page/Disable+autorepeat+under+X11

https://p.janouch.name/article-xgb.html

https://jamey.thesharps.us/2021/03/25/xcb-protocol-specifications-data/



# versions

## 0.12.0

Adding jbang examples
Upgrading dependencies
Removing rekon gradle plugin

## 0.11.0

Adding support for xkb except for GetKbdByName

## 0.10.0

[x] Made DeviceStateAbs_area and DeviceStateAbs_calib names DeviceStateAbsArea and DeviceStateAbsCalib
[x] Adding other KeySyms from x11lib.

## 0.9.0

* Added support for resolving enums directly. In xinput.xml there is an Event `Property` which has the same name as the 
enum `Property` from xproto.xml. The current solution is to use `resolveXTypeEnum(String)` when resolving enums.

* Added support for `<eventstruct>` tags.

[x] plugin name constant is needed for checking the `<allowed>` tag within `<eventstruct>`
[x] offset is required to check if an event is supported by a specific `<eventstruct>`

* Added support for readParams which are not part of the protocol for the object but passed in from another object's 
protocol. This is needed for xinput `DeviceTimeCoord`.

[x] Found bug with reading and writing events and errors for extensions. The offset must be subtracted from the number not 
added.

[x] Writing events is not possible without passing in the extension offset. The server will treat extension events like 
xproto events.

[x] Added support for `<case>` tags within a `<switch>`. This creates an interface and implementing classes for each
case.

## 0.8.0

Adding support for glx and dri3 extensions

## 0.7.0

* Adding support for generic events
* Adding support for the Present extension
* Added ProtocolPluginService to handle plugins for the XProtocolService

## 0.6.0

* Implementing `sync()` method based on XSync but without a discard parameter.
* Adding `discard()` method to clear the event queue. 
* Added `keyCodeToKeySym()` methods.
* Added `keySymToKeyCodes()` method.
* Added `getKeySym()` method.
* `getAtom(int)` now returns an `AtomValue` which contains the id and name.
* Added `getWMProtocols(int)` to returns the supported protocols for a window.
* Added `killClient(int)` and `inputFocus(int)`.
* Added Generator for KeySym enum.
* Added KeyboardService to handle the keyboard for the client.
* Added BasicWindowManager to example tests.

## 0.5.0

Adding `hasResponse()` method which checks for an available XEvent or XError on
the socket.

Since DisplayName can set the default screen methods have been added to return
default settings from the connection setup response.

## 0.4.0

Removing length properties from protocol objects where it is simply the list
size. The length still needs to be set for properties involving complex
expressions. This results in not having to set the length of lists on most 
objects. For example drawing a string no longer requires the size.

```
client.send(ImageText8.builder()
  .drawable(window.getWid())
  .gc(gc.getCid())
  .string(stringToByteList("Hello World!"))
  .x((short) 10)
  .y((short) 50)
  .build());
```

Adding exclude to x11protocol plugin.

Fixing issues with objects missing padding for the first field. This enables
the sync extension to work.

Added support for file descriptors. They are simply treated as an `int`.

Added extensions shm, sync xrandr, xv, and xvmc

Added defaultGC cache for root windows and method to automatically create them.

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

