# x11

x11 enables java and other jvm languages to talk directly to a x11 
server without binding to a C library. The client is similar to X11lib for C
but uses objects to represent the protocol resulting in a simplified client. It 
supports the core protocol and all extensions. The client follows the same pattern 
as X11lib by queuing one-way requests before sending them to the server. The
x11-protocol project enables reading and writing the entire protocol and can
be used to help write a x11 server.

[![Java CI with Gradle](https://github.com/moaxcp/x11/actions/workflows/gradle.yml/badge.svg)](https://github.com/moaxcp/x11/actions/workflows/gradle.yml)
[![maven central](https://img.shields.io/maven-central/v/com.github.moaxcp.x11/x11-client)](https://search.maven.org/artifact/com.github.moaxcp.x11/x11-client)
[![javadoc](https://javadoc.io/badge2/com.github.moaxcp.x11/x11-client/javadoc.svg)](https://javadoc.io/doc/com.github.moaxcp.x11/x11-client)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=moaxcp_x11-client&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=moaxcp_x11-client)

# Users

The x11-client library can be added to your project using maven or gradle.

## Maven

```
<dependency>
 <groupId>com.github.moaxcp.x11</groupId>
 <artifactId>x11-client</artifactId>
 <version>0.20.0</version>
 <type>module</type>
</dependency>
```

## Gradle

```
implementation 'com.github.moaxcp.x11:x11-client:0.18.2'
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

This project converts the x11 protocol to classes which implement core
interfaces (XError, XEvent, XRequest, XReply, etc). The client can read and write these objects using the read and 
write methods defined in each object’s class.

X11ProtocolExceptions may be thrown when there are IOExceptions with the socket. X11ClientExceptions are thrown with 
client issues such as connection issues or api issues. X11ErrorExceptions represent 
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

Support is most needed for example code. Other x11 libraries have tons of examples that prove the library works.
So far this project only has a few basic examples.

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

https://www.x.org/releases/X11R7.6/doc/libXtst/recordlib.html



# versions

## 0.21.0

* Added support for no-sequence-number events which is only used in KeymapNotifyEvent
    * This fixed part of the RecordApiExample
* Fixed bug where generated code for reading generic events was missing from protocol plugins
* Fixed bug in generated code for replies where reading pad align was missing
* Adding XApi interfaces to cleanup client
* Added examples and api to run examples
* Fixed bug where new resource ids are not requested by the client when they run out

## 0.20.0

Fixing XAuthority parsing following the format described [here](https://gitlab.freedesktop.org/xorg/lib/libxau/-/tree/master?ref_type=heads).

## 0.19.0

* Added support for little-endian and made it default to match the new server default (see 
[blog post](https://who-t.blogspot.com/2023/01/x-servers-no-longer-allow-byte-swapped.html)).

## 0.18.2

* Fixing XAuthority when parsing number
* switched back to junixsocket as jdk does not support sockets only SocketChannel
* Fixing usage of io.freefair.lombok in protocol projects. The module-path was not being set on the delombok task
* fixing delombok by removing lombok from projects using junixsocket. Delombok does not support mutli-release jar files.
* Switched back to junixsocket

## 0.18.1

* fixing delombok task so javadoc works again and the project can be published to maven central

## 0.18.0

* Switched to a multimodule project
* Switched to jdk 21
* Added java module system support
* Removed delombok due to [issues](https://github.com/freefair/gradle-plugins/issues/824) with modules. This results in javadoc no longer being generated.
* Removed dependency on junixsocket and switched to the jdk UnixDomainSocket.
* Removed jbang
* Moved all integration tests to the examples project with a main method.

## 0.17.0

* Adding generated protocol sources to git

## 0.16.0

* Renaming project to x11
* Switching to a multiproject build.
* Change integration tests into examples in example project.

## 0.15.0

* Adding RecordApi which provides a higher level api for reading records with parsed XObjects so the user does not need to parse the data.
* Each XObject now has a static PLUGIN_NAME assigned to the plugin name and an instance getter method.
* Plugins now use the header as the plugin name.
* Added all plugin info from the xml file (extensionName, extensionXName, extensionMultiword)
* added methods to client for reading protocol from any X11Input. This is used for reading data from the record extension and for testing read/write for any object.
* Fixed issues with list lengths which need to be unsigned.

## 0.14.0

* Fixed issue where WILD XAuthority entries do not have an address
* Fixed major bug when writing requests. For xproto the header should start with the OPCODE then 1 pad. For extensions the header should start with the majorOpcode then OPCODE. BigRequest and all QueryVersion requests worked because their OPCODE is 0 but everything else in extensions failed. This should be fixed for all extensions and has been tested with the RECORD extension.

## 0.13.0

ProtocolPluginService now sets majorOpcode on XProtocolPlugins and uses it instead of majorVersion as the base opcode for requests. This fixes a bug in loading plugins and sending requests.

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

