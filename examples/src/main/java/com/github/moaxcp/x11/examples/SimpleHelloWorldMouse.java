package com.github.moaxcp.x11.examples;

import com.github.moaxcp.x11.keysym.KeySym;
import com.github.moaxcp.x11.protocol.Utilities;
import com.github.moaxcp.x11.protocol.XEvent;
import com.github.moaxcp.x11.protocol.xproto.*;
import com.github.moaxcp.x11.x11client.X11Client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class SimpleHelloWorldMouse {

    public static void main(String... args) throws IOException {
        try(X11Client client = X11Client.connect()) {
            CreateWindow window = CreateWindow.builder()
                    .depth(client.getDepth(0))
                    .wid(client.nextResourceId())
                    .parent(client.getRoot(0))
                    .x((short) 10)
                    .y((short) 10)
                    .width((short) 600)
                    .height((short) 480)
                    .borderWidth((short) 5)
                    .clazz(WindowClass.COPY_FROM_PARENT)
                    .visual(client.getVisualId(0))
                    .backgroundPixel(client.getWhitePixel(0))
                    .borderPixel(client.getBlackPixel(0))
                    .eventMaskEnable(EventMask.EXPOSURE, EventMask.KEY_PRESS)
                    .build();
            client.send(window);

            //XStoreName sets window title
            client.send(ChangeProperty.builder()
                    .window(window.getWid())
                    .property(Atom.WM_NAME.getValue())
                    .type(Atom.STRING.getValue())
                    .format((byte) 8)
                    .dataLen("Hello World!".length())
                    .data(Utilities.toByteList("Hello World!"))
                    .build());

            //XSetWMProtocols for adding delete atom
            InternAtomReply wmProtocols = client.send(InternAtom.builder().name(Utilities.toByteList("WM_PROTOCOLS")).build());
            InternAtomReply deleteAtom = client.send(InternAtom.builder().name(Utilities.toByteList("WM_DELETE_WINDOW")).build());
            client.send(ChangeProperty.builder()
                    .window(window.getWid())
                    .property(wmProtocols.getAtom())
                    .type(Atom.ATOM.getValue())
                    .format((byte) 32)
                    .mode(PropMode.REPLACE)
                    .data(Utilities.toList(ByteBuffer.allocate(4).putInt(deleteAtom.getAtom()).array()))
                    .dataLen(1)
                    .build());

            client.send(MapWindow.builder()
                    .window(window.getWid())
                    .build());
            CreateGC gc = CreateGC.builder()
                    .cid(client.nextResourceId())
                    .drawable(window.getWid())
                    .background(client.getWhitePixel(0))
                    .foreground(client.getBlackPixel(0))
                    .build();
            client.send(gc);
            while(true) {
                XEvent event = client.getNextEvent();
                if(event instanceof ExposeEvent) {
                    List<Rectangle> rectangles = new ArrayList<>();
                    rectangles.add(Rectangle.builder()
                            .x((short) 20)
                            .y((short) 20)
                            .width((short) 10)
                            .height((short) 10)
                            .build());
                    client.send(PolyFillRectangle.builder()
                            .drawable(window.getWid())
                            .gc(gc.getCid())
                            .rectangles(rectangles)
                            .build());
                    client.send(ImageText8.builder()
                            .drawable(window.getWid())
                            .gc(gc.getCid())
                            .string(Utilities.toByteList("Hello World!"))
                            .x((short) 10)
                            .y((short) 50)
                            .build());
                    GetGeometryReply geometry = client.send(GetGeometry.builder()
                            .drawable(window.getWid())
                            .build());
                    String attributeText = String.format("Size: %dx%d", geometry.getWidth(), geometry.getHeight());
                    client.send(ImageText8.builder()
                            .drawable(window.getWid())
                            .gc(gc.getCid())
                            .string(Utilities.toByteList(attributeText))
                            .x((short) 10)
                            .y((short) 80)
                            .build());
                } else if(event instanceof KeyPressEvent) {
                    KeyPressEvent keyPress = (KeyPressEvent) event;
                    KeySym keysym = client.keyCodeToKeySym(keyPress.getDetail(), keyPress.getState());
                    if(keysym == KeySym.XK_Escape) {
                        break;
                    }
                } else if(event instanceof ClientMessageEvent) {
                    ClientMessageEvent clientMessage = (ClientMessageEvent) event;
                    if(clientMessage.getFormat() == 32) {
                        ClientMessageData32 data = (ClientMessageData32) clientMessage.getData();
                        if(data.getData32().get(0) == deleteAtom.getAtom()) {
                            break;
                        }
                    }
                } else {
                    throw new IllegalStateException(event.toString());
                }
            }
        }
    }

}
