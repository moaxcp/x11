///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.github.moaxcp.x11:x11-client:+

import com.github.moaxcp.x11client.*;
import com.github.moaxcp.x11client.protocol.KeySym;
import com.github.moaxcp.x11client.protocol.Utilities;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.xproto.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static com.github.moaxcp.x11client.protocol.Utilities.toList;

try (X11Client x11Client = X11Client.connect()) {
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
    while (true) {
        XEvent event = x11Client.getNextEvent();
        if (event instanceof ExposeEvent) {
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
                .string(Utilities.toByteList("Hello World!"))
                .x((short) 10)
                .y((short) 50)
                .build());
        } else if (event instanceof KeyPressEvent) {
            break;
        }
    }
}